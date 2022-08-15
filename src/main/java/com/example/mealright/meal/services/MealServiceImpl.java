/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.mealright.meal.services;

import com.example.mealright.MealRight.DatabaseController;
import com.example.mealright.meal.entities.MealEntity;
import com.example.mealright.meal.model.Meal;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.DocumentSnapshot;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bhartlove
 */

@Service
public class MealServiceImpl implements MealService {

    @Override
    public Meal createMeal(Meal meal) {
        MealEntity mealEntity = new MealEntity();
        BeanUtils.copyProperties(meal, mealEntity);
        
        Firestore db = DatabaseController.getInstance().db;
        
        Map<String, Object> data = new HashMap<>();
        data.put("name", mealEntity.getName());
        data.put("description", mealEntity.getDescription());
        data.put("poster", mealEntity.getPoster());
        data.put("likes", mealEntity.getLikes());
        data.put("recipe", mealEntity.getRecipe());
        data.put("tags", mealEntity.getTags());
        data.put("uid", mealEntity.getUid());

        ApiFuture<DocumentReference> result = db.collection("meals").add(data);
        
        return meal;
    }
    
    @Override
    public List<String> updateLikes(String id, String userId){
        Firestore db = DatabaseController.getInstance().db;
        DocumentReference docRef = db.collection("meals").document(id.trim());
        // asynchronously retrieve the document
        ApiFuture<DocumentSnapshot> future = docRef.get();
        // future.get() blocks on response
        DocumentSnapshot document;
        List<String> likedBy;
        try {
            document = future.get();
            if(document.exists()){
                Map docData = document.getData();
                likedBy = (List<String>)docData.get("likes");
                boolean inList = false;
                for (String user : likedBy) {
                    if(user.equals(userId)){
                        inList = true;
                        break;
                    }
                }
                if(inList){
                    likedBy.remove(userId);
                }
                
                else{
                    likedBy.add(userId);
                }
                docRef.update("likes", likedBy);
                return likedBy;
            }
            
            else{
                System.out.println("Document does not exist");
            }
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(MealServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         return null;
        
    }
    
    @Override
    public Meal getMeal(String id) {
        Firestore db = DatabaseController.getInstance().db;
        Meal retMeal = new Meal();
        DocumentReference docRef = db.collection("meals").document(id.trim());
        // asynchronously retrieve the document
        ApiFuture<DocumentSnapshot> future = docRef.get();
        // future.get() blocks on response
        DocumentSnapshot document;
        try {
            document = future.get();
            if(document.exists()){
                Map docData = document.getData();
                retMeal.setName(docData.get("name").toString());
                retMeal.setDescription(docData.get("description").toString());
                retMeal.setPoster(docData.get("poster").toString());
                retMeal.setLikes((List<String>)docData.get("likes"));
                retMeal.setRecipe(docData.get("recipe").toString());
                retMeal.setTags((List<String>)docData.get("tags"));
                retMeal.setUid(docData.get("uid").toString());
                retMeal.setId(document.getId());
                return retMeal;
            }
            
            else{
                System.out.println("Document does not exist");
            }
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(MealServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         return null;
    }
    
    @Override
    public List<Meal> getMeals() {
        Firestore db = DatabaseController.getInstance().db;
        
        List<Meal> mealList = new ArrayList<>();
        CollectionReference docRef = db.collection("meals");
        Map docData = new HashMap();
        Meal tmpMeal;
        
        // asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future = docRef.get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents;
        try {
            mealList.clear();
            documents = future.get().getDocuments();
            for(QueryDocumentSnapshot document : documents) {
                tmpMeal = new Meal();
                docData.putAll(document.getData());
                tmpMeal.setName(docData.get("name").toString());
                tmpMeal.setDescription(docData.get("description").toString());
                tmpMeal.setPoster(docData.get("poster").toString());
                tmpMeal.setLikes((List<String>)docData.get("likes"));
                tmpMeal.setRecipe(docData.get("recipe").toString());
                tmpMeal.setTags((List<String>)docData.get("tags"));
                tmpMeal.setId(document.getId());
                tmpMeal.setUid(docData.get("uid").toString());
                mealList.add(tmpMeal);
                docData.clear();
            }
            
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(MealServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return mealList;
    }
    
}

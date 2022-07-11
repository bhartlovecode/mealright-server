/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.mealright.meal.services;

import com.example.mealright.MealRight.DatabaseController;
import com.example.mealright.meal.entities.MealEntity;
import com.example.mealright.meal.model.Meal;
import static com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat.UUID;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.UUID;

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
        data.put("rating", mealEntity.getRating());
        data.put("recipe", mealEntity.getRecipe());
        data.put("tags", mealEntity.getTags());

        ApiFuture<DocumentReference> result = db.collection("meals").add(data);
        
        return meal;
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
                tmpMeal.setRating((double)docData.get("rating"));
                tmpMeal.setRecipe((Map<String, String>)docData.get("recipe"));
                tmpMeal.setTags((List<String>)docData.get("tags"));
                tmpMeal.setId(document.getId());
                mealList.add(tmpMeal);
                docData.clear();
            }
            
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(MealServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return mealList;
    }
    
}

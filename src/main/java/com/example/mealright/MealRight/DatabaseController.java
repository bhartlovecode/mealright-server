/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.mealright.MealRight;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

/**
 *
 * @author bhartlove
 */
public class DatabaseController {
    
    private static DatabaseController single_instance = null;
    public Firestore db;
    
    private DatabaseController(){
        try{
            db = FirestoreClient.getFirestore();
        }
        
        catch(Exception e){
            System.out.println("Error initializing database connection: " + e);
        }
    }
    
    public static DatabaseController getInstance()
    {
        if (single_instance == null)
            single_instance = new DatabaseController();
 
        return single_instance;
    }
    
}

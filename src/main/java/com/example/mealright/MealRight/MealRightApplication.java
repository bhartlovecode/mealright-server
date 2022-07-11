package com.example.mealright.MealRight;

import com.google.api.core.ApiFuture;
//import com.google.api.services.storage.Storage.Objects.List;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication; 
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages="com.example.mealright.meal.model") 
@ComponentScan(basePackages="com.example.mealright.meal.entities") 
@ComponentScan(basePackages="com.example.mealright.meal.services")
@ComponentScan(basePackages="com.example.mealright.meal.controller") 
@SpringBootApplication
public class MealRightApplication {

	public static void main(String[] args) {
		SpringApplication.run(MealRightApplication.class, args);
                
                try{
                   FileInputStream serviceAccount = new FileInputStream("src/main/java/com/example/mealright/MealRight/firebase.json");

                    FirebaseOptions options = new FirebaseOptions.Builder()
                      .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                      .build();

                    FirebaseApp app = FirebaseApp.initializeApp(options); 
                    DatabaseController db = DatabaseController.getInstance();

                } 
                
                catch(IOException e){
                    System.out.println("Error initializing spring application! " + e);
                    System.exit(1);
                }
                
	}

}

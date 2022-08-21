/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.mealright.meal.controller;

import com.example.mealright.meal.model.Meal;
import java.util.List;
import com.example.mealright.meal.services.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author bhartlove
 */
      
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class MealController {
    
    @Autowired
    private final MealService mealService;
    
    public MealController(MealService mealService){
        this.mealService = mealService;
    }
    
    @PostMapping("/meals")
    public Meal createMeal(@RequestBody Meal meal){
        return mealService.createMeal(meal);
    }
    
    @GetMapping("/meals")
    public List<Meal> getMeals(){
        return mealService.getMeals();
    }
    
    @GetMapping("/meals/{id}")
    public Meal getMeal(@PathVariable String id){
        return mealService.getMeal(id);
    }
    
    @PostMapping("/meals/{id}/updateLikes")
    public List<String> updateLikes(@PathVariable String id, @RequestBody String uid){
        uid = uid.substring(0, uid.length()-1);
        return mealService.updateLikes(id, uid);
    }
    
    @PostMapping("/meals/{id}/updateMeal")
    public Meal updateMeal(@RequestBody Meal meal, @PathVariable String id){
        return mealService.updateMeal(meal, id);
    }
}
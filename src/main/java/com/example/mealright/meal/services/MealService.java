/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.mealright.meal.services;

import com.example.mealright.meal.model.Meal;
import java.util.List;

/**
 *
 * @author bhartlove
 */
public interface MealService {
    
    public Meal createMeal(Meal meal);
    
    public List<Meal> getMeals();
    
    public Meal getMeal(String id);
    
    public List<String> updateLikes(String id, String userId);
    
    public Meal updateMeal(Meal meal, String id);
    
}

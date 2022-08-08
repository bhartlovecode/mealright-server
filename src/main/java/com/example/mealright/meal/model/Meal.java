/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.mealright.meal.model;

import lombok.Data;
import java.util.Map;
import java.util.List;

/**
 *
 * @author bhartlove
 */

@Data
public class Meal {
    private String id;
    private String name;
    private String description;
    private String recipe;
    private long likes;
    private String poster;
    private List<String> tags;
    
    public void reset(){
        id = "";
        name = "";
        description = "";
        recipe = "";
        likes = 0;
        poster = "";
        tags.clear();
    }

}
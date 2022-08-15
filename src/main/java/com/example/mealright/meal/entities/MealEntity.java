
package com.example.mealright.meal.entities;

import java.util.List;
import java.util.Map;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

/**
 *
 * @author bhartlove
 */
@Data
public class MealEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String name;
    private String description;
    private String recipe;
    private List<String> likes;
    private String poster;
    private List<String> tags;
    private String uid;
}

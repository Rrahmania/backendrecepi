package com.rasanusantara.ourrecipe.service;

import com.rasanusantara.ourrecipe.model.Recipe;
import java.util.List;

// ABSTRACTION: Interface hanya berisi deklarasi metode tanpa implementasi
public interface RecipeService {
    List<Recipe> getAllRecipes();
    Recipe getRecipeById(Long id);
    Recipe createRecipe(Recipe recipe, Long userId);
    void deleteRecipe(Long recipeId, Long requesterId);
}
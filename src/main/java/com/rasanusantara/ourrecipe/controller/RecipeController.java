package com.rasanusantara.ourrecipe.controller;

import com.rasanusantara.ourrecipe.model.Recipe;
import com.rasanusantara.ourrecipe.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@CrossOrigin(origins = "*") // Penting: Mengizinkan frontend dari domain/port lain (seperti Vercel) untuk mengakses API ini
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    // Endpoint: GET /api/recipes
    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    // Endpoint: POST /api/recipes?userId=1
    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe, @RequestParam Long userId) {
        Recipe newRecipe = recipeService.createRecipe(recipe, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRecipe);
    }

    // Endpoint: DELETE /api/recipes/1?requesterId=1
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable Long id, @RequestParam Long requesterId) {
        recipeService.deleteRecipe(id, requesterId);
        return ResponseEntity.ok("Resep berhasil dihapus");
    }
}
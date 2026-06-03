package com.rasanusantara.ourrecipe.service;

import com.rasanusantara.ourrecipe.model.Account;
import com.rasanusantara.ourrecipe.model.Admin;
import com.rasanusantara.ourrecipe.model.Recipe;
import com.rasanusantara.ourrecipe.model.RegularUser;
import com.rasanusantara.ourrecipe.repository.AccountRepository;
import com.rasanusantara.ourrecipe.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service // Menjadikan class ini sebagai Singleton bean di Spring
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id)
                // ERROR HANDLING: Melempar exception 404 jika resep tidak ditemukan
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resep tidak ditemukan"));
    }

    @Override
    public Recipe createRecipe(Recipe recipe, Long userId) {
        Account account = accountRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User tidak ditemukan"));

        // Validasi: Hanya RegularUser yang bisa membuat resep, Admin tidak perlu membuat resep
        if (!(account instanceof RegularUser)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Hanya User biasa yang dapat membuat resep");
        }

        recipe.setAuthor((RegularUser) account);
        return recipeRepository.save(recipe);
    }

    @Override
    public void deleteRecipe(Long recipeId, Long requesterId) {
        Recipe recipe = getRecipeById(recipeId);
        Account requester = accountRepository.findById(requesterId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User tidak ditemukan"));

        // POLYMORPHISM & LOGIKA ROLE:
        // Admin bisa menghapus apa saja. RegularUser hanya bisa menghapus miliknya sendiri.
        if (requester instanceof Admin) {
            recipeRepository.delete(recipe);
        } else if (requester instanceof RegularUser) {
            if (!recipe.getAuthor().getId().equals(requester.getId())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Anda tidak memiliki izin menghapus resep ini");
            }
            recipeRepository.delete(recipe);
        }
    }
}
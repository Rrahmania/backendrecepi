package com.rasanusantara.ourrecipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rasanusantara.ourrecipe.model.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    // Kita bisa tambahkan pencarian berdasarkan kategori dll nanti di sini
}
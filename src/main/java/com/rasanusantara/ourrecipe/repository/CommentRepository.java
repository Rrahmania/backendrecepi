package com.rasanusantara.ourrecipe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rasanusantara.ourrecipe.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.recipe.id = :recipeId")
    List<Comment> findByRecipeId(@Param("recipeId") Long recipeId);

    @Query("SELECT c FROM Comment c WHERE c.recipe.id = :recipeId AND c.account.id = :accountId")
    Optional<Comment> findByRecipeIdAndAccountId(@Param("recipeId") Long recipeId, @Param("accountId") Long accountId);
}

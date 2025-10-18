package com.lorachemicals.Backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lorachemicals.Backend.model.RecipeItem;

public interface RecipeItemRepository extends JpaRepository<RecipeItem, Long> {

    List<RecipeItem> findByRecipeRecipeid(Long recipeid);

    List<RecipeItem> findByRecipe_Recipeid(Long recipeid);

    @Modifying
    @Query("DELETE FROM RecipeItem r WHERE r.recipe.recipeid = :recipeId")
    void deleteByRecipeRecipeid(@Param("recipeId") Long recipeId);
}

package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.RecipeItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeItemRepository extends JpaRepository<RecipeItem, Long> {

    List<RecipeItem> findByRecipeRecipeid(Long recipeid);


    List<RecipeItem> findByRecipe_Recipeid(Long recipeid);
}

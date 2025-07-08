package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.RecipeItemRawChemical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecipeItemRawChemicalRepository extends JpaRepository<RecipeItemRawChemical, Long> {

    @Modifying
    @Query("DELETE FROM RecipeItemRawChemical r WHERE r.recipeItem.recipeitemid = :recipeItemId")
    void deleteByRecipeItemRecipeitemid(@Param("recipeItemId") Long recipeItemId);
}

package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}

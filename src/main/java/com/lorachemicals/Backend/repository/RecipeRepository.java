package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Optional<Recipe> findByMixer_Mixerid(Long mixerid);
}

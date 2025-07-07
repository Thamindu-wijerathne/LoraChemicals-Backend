package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.RecipeRequestDTO;
import com.lorachemicals.Backend.model.Mixer;
import com.lorachemicals.Backend.model.Recipe;
import com.lorachemicals.Backend.repository.MixerRepository;
import com.lorachemicals.Backend.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private MixerRepository mixerRepository;

    // Get all recipes
    public List<Recipe> getAllRecipes() {
        try {
            return recipeRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve recipes: " + e.getMessage(), e);
        }
    }

    // Get recipe by ID
    public Recipe getRecipeById(Long recipeid) {
        try {
            return recipeRepository.findById(recipeid)
                    .orElseThrow(() -> new RuntimeException("Recipe not found with ID: " + recipeid));
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve recipe: " + e.getMessage(), e);
        }
    }

    //get by mixerid
    public Recipe getRecipeByMixerId(Long mixerid) {
        try {
            return recipeRepository.findByMixer_Mixerid(mixerid)
                    .orElseThrow(() -> new RuntimeException("Recipe not found with mixer ID: " + mixerid));
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve recipe with mixer ID: " + mixerid, e);
        }
    }

    // Create recipe
    public Recipe addRecipe(RecipeRequestDTO dto) {
        try {
            Mixer mixer = mixerRepository.findById(dto.getMixerid())
                    .orElseThrow(() -> new RuntimeException("Mixer not found with ID: " + dto.getMixerid()));

            Recipe recipe = new Recipe();
            recipe.setRecipeName(dto.getRecipeName());
            recipe.setMixer(mixer);

            return recipeRepository.save(recipe);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add recipe: " + e.getMessage(), e);
        }
    }

    // Update recipe
    public Recipe updateRecipe(Long recipeid, RecipeRequestDTO dto) {
        try {
            Recipe recipe = recipeRepository.findById(recipeid)
                    .orElseThrow(() -> new RuntimeException("Recipe not found with ID: " + recipeid));

            Mixer mixer = mixerRepository.findById(dto.getMixerid())
                    .orElseThrow(() -> new RuntimeException("Mixer not found with ID: " + dto.getMixerid()));

            recipe.setRecipeName(dto.getRecipeName());
            recipe.setMixer(mixer);

            return recipeRepository.save(recipe);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update recipe: " + e.getMessage(), e);
        }
    }

    // Delete recipe
    public void deleteRecipe(Long recipeid) {
        try {
            Recipe recipe = recipeRepository.findById(recipeid)
                    .orElseThrow(() -> new RuntimeException("Recipe not found with ID: " + recipeid));

            recipeRepository.delete(recipe);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete recipe: " + e.getMessage(), e);
        }
    }
}

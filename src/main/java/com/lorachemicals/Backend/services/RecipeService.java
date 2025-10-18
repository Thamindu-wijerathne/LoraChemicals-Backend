package com.lorachemicals.Backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lorachemicals.Backend.dto.RecipeRequestDTO;
import com.lorachemicals.Backend.model.ProductType;
import com.lorachemicals.Backend.model.Recipe;
import com.lorachemicals.Backend.repository.ProductTypeRepository;
import com.lorachemicals.Backend.repository.RecipeItemRawChemicalRepository;
import com.lorachemicals.Backend.repository.RecipeItemRepository;
import com.lorachemicals.Backend.repository.RecipeRepository;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private RecipeItemRepository recipeItemRepository;

    @Autowired
    private RecipeItemRawChemicalRepository recipeItemRawChemicalRepository;

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

    //get by productType
    public Recipe getRecipeByproductTypeid(Long productTypeid) {
        try {
            return recipeRepository.findByProductType_ProductTypeId(productTypeid)
                    .orElseThrow(() -> new RuntimeException("Recipe not found with productType: " + productTypeid));
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve recipe with productType: " + productTypeid, e);
        }
    }

    // Create recipe
    public Recipe addRecipe(RecipeRequestDTO dto) {
        try {
            ProductType productType = productTypeRepository.findById(dto.getProductTypeId())
                    .orElseThrow(() -> new RuntimeException("ProductType not found with ID: " + dto.getProductTypeId()));

            Recipe recipe = new Recipe();
            recipe.setRecipeName(dto.getRecipeName());
            recipe.setProductType(productType);

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

            ProductType productType = productTypeRepository.findById(dto.getProductTypeId())
                    .orElseThrow(() -> new RuntimeException("ProductType not found with ID: " + dto.getProductTypeId()));


            recipe.setRecipeName(dto.getRecipeName());
            recipe.setProductType(productType);

            return recipeRepository.save(recipe);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update recipe: " + e.getMessage(), e);
        }
    }

    // Delete recipe
    @Transactional
    public void deleteRecipe(Long recipeid) {
        try {
            Recipe recipe = recipeRepository.findById(recipeid)
                    .orElseThrow(() -> new RuntimeException("Recipe not found with ID: " + recipeid));

            // Step 1: Delete all junction table records for this recipe
            recipeItemRawChemicalRepository.deleteByRecipeId(recipeid);
            
            // Step 2: Delete all recipe items for this recipe
            recipeItemRepository.deleteByRecipeRecipeid(recipeid);
            
            // Step 3: Delete the recipe itself
            recipeRepository.delete(recipe);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete recipe: " + e.getMessage(), e);
        }
    }
}

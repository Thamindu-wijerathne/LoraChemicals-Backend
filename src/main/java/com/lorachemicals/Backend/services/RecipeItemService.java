package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.RecipeItemRawChemicalRequestDTO;
import com.lorachemicals.Backend.dto.RecipeItemRequestDTO;
import com.lorachemicals.Backend.model.RawChemicalType;
import com.lorachemicals.Backend.model.Recipe;
import com.lorachemicals.Backend.model.RecipeItem;
import com.lorachemicals.Backend.model.RecipeItemRawChemical;
import com.lorachemicals.Backend.repository.RawChemicalTypeRepository;
import com.lorachemicals.Backend.repository.RecipeItemRawChemicalRepository;
import com.lorachemicals.Backend.repository.RecipeItemRepository;
import com.lorachemicals.Backend.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeItemService {

    @Autowired
    private RecipeItemRepository recipeItemRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RawChemicalTypeRepository rawChemicalTypeRepository;

    @Autowired
    private RecipeItemRawChemicalRepository recipeItemRawChemicalRepository;

    // Get all recipe items
    public List<RecipeItem> getAllRecipeItems() {
        try {
            return recipeItemRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get all recipe items", e);
        }
    }

    // Get all recipe items by recipe id
    public List<RecipeItem> getRecipeItemsByRecipeId(Long recipeid) {
        try {
            return recipeItemRepository.findByRecipeRecipeid(recipeid); // ✅ updated
        } catch (Exception e) {
            throw new RuntimeException("Failed to get recipe items for recipe id: " + recipeid, e);
        }
    }

    // Get by recipe item id
    public RecipeItem getRecipeItemById(Long recipeItemid) {
        try {
            return recipeItemRepository.findById(recipeItemid)
                    .orElseThrow(() -> new RuntimeException("RecipeItem not found with id: " + recipeItemid));
        } catch (Exception e) {
            throw new RuntimeException("Failed to get recipe item by id: " + recipeItemid, e);
        }
    }

    //get by recipe id
    public List<RecipeItem> getRecipeitemsByRecipeId(Long recipeid) {
        try{
            return recipeItemRepository.findByRecipeRecipeid(recipeid);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get recipe items for recipe id: " + recipeid, e);
        }
    }

    // Create recipe item and recipeitemrawchemical
    public RecipeItem createRecipeItem(RecipeItemRequestDTO recipeItemRequestDTO, RecipeItemRawChemicalRequestDTO recipeItemRawChemicalRequestDTO) {
        try {
            // Fetch the chemical
            RawChemicalType chemicalType = rawChemicalTypeRepository.findById(recipeItemRequestDTO.getRawchemicalid())
                    .orElseThrow(() -> new RuntimeException("Chemical not found with ID: " + recipeItemRequestDTO.getRawchemicalid()));

            // Fetch the recipe
            RecipeItem recipeItem = new RecipeItem();
            recipeItem.setRawChemical(chemicalType);
            recipeItem.setRecipe(recipeRepository.findById(recipeItemRequestDTO.getRecipeid())
                    .orElseThrow(() -> new RuntimeException("Recipe not found with ID: " + recipeItemRequestDTO.getRecipeid())));
            recipeItem.setUnit(recipeItemRequestDTO.getUnit());
            recipeItem.setQuantity(recipeItemRequestDTO.getQuantity());

            // Save recipe item first
            RecipeItem savedRecipeItem = recipeItemRepository.save(recipeItem);

            // Then create and save the link to raw chemical
            RecipeItemRawChemical recipeItemRawChemical = new RecipeItemRawChemical();
            recipeItemRawChemical.setRawChemical(chemicalType);
            recipeItemRawChemical.setRecipeItem(savedRecipeItem);
            recipeItemRawChemicalRepository.save(recipeItemRawChemical);

            return savedRecipeItem;

        } catch (Exception e) {
            throw new RuntimeException("Failed to create recipe item and its raw chemical links", e);
        }
    }

    // Update recipe item by recipe item id
    // Update recipe item by recipe item id
    public RecipeItem updateRecipeItemById(Long recipeItemid, RecipeItemRequestDTO updatedRecipeItemDTO) {
        try {
            RecipeItem recipeItem = recipeItemRepository.findById(recipeItemid)
                    .orElseThrow(() -> new RuntimeException("RecipeItem not found with id: " + recipeItemid));

            RawChemicalType chemical = rawChemicalTypeRepository.findById(updatedRecipeItemDTO.getRawchemicalid())
                    .orElseThrow(() -> new RuntimeException("Chemical not found with id: " + updatedRecipeItemDTO.getRawchemicalid()));

            Recipe recipe = recipeRepository.findById(updatedRecipeItemDTO.getRecipeid())
                    .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + updatedRecipeItemDTO.getRecipeid()));

            // Update all fields
            recipeItem.setRawChemical(chemical);
            recipeItem.setRecipe(recipe);
            recipeItem.setUnit(updatedRecipeItemDTO.getUnit());
            recipeItem.setQuantity(updatedRecipeItemDTO.getQuantity());

            return recipeItemRepository.save(recipeItem);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update recipe item with id: " + recipeItemid, e);
        }
    }

    // Delete by id
    public void deleteRecipeItemById(Long recipeItemid) {
        try {
            recipeItemRepository.deleteById(recipeItemid);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete recipe item with id: " + recipeItemid, e);
        }
    }

    public RecipeItem createRecipeItem(RecipeItem recipeItem) {
        try {
            RecipeItem savedRecipeItem = recipeItemRepository.save(recipeItem);

            RecipeItemRawChemical ric = new RecipeItemRawChemical();
            ric.setRecipeItem(savedRecipeItem);
            ric.setRawChemical(recipeItem.getRawChemical());

            recipeItemRawChemicalRepository.save(ric);

            return savedRecipeItem;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create recipe item", e);
        }
    }

}

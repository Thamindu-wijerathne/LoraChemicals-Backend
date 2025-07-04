package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.RecipeItemCreateRequest;
import com.lorachemicals.Backend.dto.RecipeItemRequestDTO;
import com.lorachemicals.Backend.model.RecipeItem;
import com.lorachemicals.Backend.services.RecipeItemService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter
@RestController
@RequestMapping("/recipe-item")
public class RecipeItemController {
    @Autowired
    private RecipeItemService recipeItemService;

    @GetMapping
    public ResponseEntity<?> getAllRecipeItems(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            List<RecipeItem> recipeItems = recipeItemService.getAllRecipeItems();
            return new ResponseEntity<>(recipeItems, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get recipe items: " + e.getMessage(), e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRecipeItemById(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            RecipeItem recipeItem = recipeItemService.getRecipeItemById(id);
            return new ResponseEntity<>(recipeItem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("RecipeItem not found: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createRecipeItem(@RequestBody RecipeItemCreateRequest requestBody, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            RecipeItem created = recipeItemService.createRecipeItem(
                    requestBody.getRecipeItemDTO(),
                    requestBody.getRawChemicalDTO()
            );
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create recipe item: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecipeItem(@PathVariable Long id, @RequestBody RecipeItemRequestDTO recipeItemRequestDTO, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            RecipeItem updated = recipeItemService.updateRecipeItemById(id, recipeItemRequestDTO);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update recipe item: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecipeItem(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            recipeItemService.deleteRecipeItemById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete recipe item: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

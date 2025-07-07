package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.RecipeRequestDTO;
import com.lorachemicals.Backend.model.Recipe;
import com.lorachemicals.Backend.services.RecipeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    // GET all
    @GetMapping("/all")
    public ResponseEntity<?> getAllRecipes(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            List<Recipe> recipes = recipeService.getAllRecipes();
            return new ResponseEntity<>(recipes, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to fetch recipes");
        }
    }

    // GET by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getRecipeById(@PathVariable("id") Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            Recipe recipe = recipeService.getRecipeById(id);
            return new ResponseEntity<>(recipe, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recipe not found with ID: " + id);
        }
    }

    //Get by mixer id
    @GetMapping("/mixer/{id}")
    public ResponseEntity<?> getMixerById(@PathVariable("id") Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin", "warehouse");

        try{
            Recipe recipe = recipeService.getRecipeByMixerId(id);
            return new ResponseEntity<>(recipe, HttpStatus.OK);

        } catch(Exception e){
            return ResponseEntity.internalServerError().body("Failed to fetch recipe with ID: " + id);
        }
    }

    // CREATE
    @PostMapping("/add")
    public ResponseEntity<?> createRecipe(@RequestBody RecipeRequestDTO dto, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            Recipe newRecipe = recipeService.addRecipe(dto);
            return new ResponseEntity<>(newRecipe, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to create recipe: " + e.getMessage());
        }
    }

    // UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRecipe(@PathVariable("id") Long id,
                                          @RequestBody RecipeRequestDTO dto,
                                          HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            Recipe updatedRecipe = recipeService.updateRecipe(id, dto);
            return new ResponseEntity<>(updatedRecipe, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update recipe: " + e.getMessage());
        }
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable("id") Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            recipeService.deleteRecipe(id);
            return ResponseEntity.ok("Recipe deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to delete recipe: " + e.getMessage());
        }
    }
}

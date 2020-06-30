package com.example.recepti.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.recepti.model.Recipe;
import com.example.recepti.repo.Repository;

import java.util.List;

public class RecipeViewModel extends ViewModel {

    private Repository recipeRepository;
    private boolean isViewingRecipes = false;

    public RecipeViewModel() {
        recipeRepository = Repository.getInstance();
    }

    public LiveData<List<Recipe>> getRecipes(){
        return recipeRepository.getRecipes();
    }

    public void searchRecipesApi(String query, int pageNumber){
        isViewingRecipes = true;
        recipeRepository.searchRecipesApi(query, pageNumber);
    }

    public boolean isViewingRecipes() {
        return isViewingRecipes;
    }

    public void setViewingRecipes(boolean viewingRecipes) {
        isViewingRecipes = viewingRecipes;
    }

    public boolean onBackPressed(){
        if (isViewingRecipes){
            isViewingRecipes = false;
            return false;
        }
        return true;
    }
}

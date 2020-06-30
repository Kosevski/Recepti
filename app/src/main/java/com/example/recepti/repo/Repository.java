package com.example.recepti.repo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.recepti.model.Recipe;
import com.example.recepti.requests.RecipeApiClient;

import java.util.List;

public class Repository {

    private static Repository instance;
    private RecipeApiClient recipeApiClient;

    public static Repository getInstance() {
        if(instance==null){
            instance = new Repository();
        }
        return instance;
    }

    private Repository(){
        recipeApiClient = RecipeApiClient.getInstance();

    }

    public MutableLiveData<List<Recipe>> getRecipes() {
        return recipeApiClient.getRecipes();
    }

    public void searchRecipesApi(String query, int pageNumber){
        if (pageNumber == 0){
            pageNumber = 1;
        }
        recipeApiClient.searchRecipesApi(query, pageNumber);
    }
}

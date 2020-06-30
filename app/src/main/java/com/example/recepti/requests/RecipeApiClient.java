package com.example.recepti.requests;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.recepti.AppExecutors;
import com.example.recepti.model.Recipe;
import com.example.recepti.requests.responces.RecipeSearchResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

import static com.example.recepti.util.Constants.BASE_URL;
import static com.example.recepti.util.Constants.NETWORK_TIMEOUT;

public class RecipeApiClient {

    private static final String TAG = "RecipeApiClient";

    private MutableLiveData<List<Recipe>> recipes;
    private RetrieveRecipesRunnable retrieveRecipesRunnable;

    private static RecipeApiClient instance;

    public static RecipeApiClient getInstance() {
        if (instance == null) {
            instance = new RecipeApiClient();
        }
        return instance;
    }

    public RecipeApiClient() {
        recipes = new MutableLiveData<>();
    }

    public MutableLiveData<List<Recipe>> getRecipes() {
        return recipes;
    }

    public void searchRecipesApi(String query, int pageNumber) {
        if (retrieveRecipesRunnable != null) {
            retrieveRecipesRunnable = null;
        }
        retrieveRecipesRunnable = new RetrieveRecipesRunnable(query, pageNumber);

        final Future handler = AppExecutors.getInstance().networkIO().submit(retrieveRecipesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    private class RetrieveRecipesRunnable implements Runnable {
        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveRecipesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getRecipes(query, pageNumber).execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    List<Recipe> list = new ArrayList<>(((RecipeSearchResponse) response.body()).getRecipes());
                    if (pageNumber == 1) {
                        recipes.postValue(list);
                    } else {
                        List<Recipe> currentRecipes = recipes.getValue();
                        currentRecipes.addAll(list);
                        recipes.postValue(currentRecipes);
                    }
                } else {
                    String error = response.errorBody().string();
                    Log.d(TAG, "run: " + error);
                    recipes.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                recipes.postValue(null);
            }

        }

        private Call<RecipeSearchResponse> getRecipes(String query, int pageNumber) {
            return ServiceGenerator.getRecipeApi().searchRecipe(
                    BASE_URL,
                    query,
                    String.valueOf(pageNumber)
            );
        }

        private void cancelRequest() {
            Log.d(TAG, "cancelRequest: canceling search request");
        }
    }
}

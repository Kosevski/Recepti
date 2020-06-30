package com.example.recepti;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recepti.adapteri.OnRecipeListener;
import com.example.recepti.adapteri.RecipeRecyclerAdapter;
import com.example.recepti.model.Recipe;
import com.example.recepti.viewmodels.RecipeViewModel;

import java.util.List;

public class RecipeListActivity extends BaseActivity implements OnRecipeListener {
    private RecipeViewModel recipeViewModel;

    private static final String TAG = "RecipeListActivity";
    private RecyclerView recyclerView;
    private RecipeRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        recyclerView = findViewById(R.id.recipe_list);

        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        subscribeObservers();
        initRecyclerview();
        initSearchView();

        if (!recipeViewModel.isViewingRecipes()) {
            displaySearchCat();
        }

    }

    private void subscribeObservers() {
        recipeViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                if (recipes != null) {
                    if (recipeViewModel.isViewingRecipes()){
                        adapter.setRecipes(recipes);
                    }
                }
            }
        });
    }

    private void initRecyclerview() {
        adapter = new RecipeRecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void initSearchView() {
        SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.displayLoading();
                recipeViewModel.searchRecipesApi(query, 1);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    @Override
    public void onRecipeClick(int position) {

    }

    @Override
    public void onCategoryClick(String category) {
        adapter.displayLoading();
        recipeViewModel.searchRecipesApi(category, 1);
    }

    private void displaySearchCat() {
        recipeViewModel.setViewingRecipes(false);
        adapter.displaySearchCategories();
    }

    @Override
    public void onBackPressed() {
        if (recipeViewModel.onBackPressed()){
            super.onBackPressed();
        } else displaySearchCat();
    }
}
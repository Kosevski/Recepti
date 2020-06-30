package com.example.recepti.adapteri;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.recepti.R;
import com.example.recepti.model.Recipe;
import com.example.recepti.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int RECIPE_TYPE = 1;
    private static final int LOADING_TYPE = 2;
    private static final int CATEGORY_TYPE = 3;

    private List<Recipe> recipes;
    private OnRecipeListener onRecipeListener;

    public RecipeRecyclerAdapter(OnRecipeListener onRecipeListener) {
        this.onRecipeListener = onRecipeListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case RECIPE_TYPE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_recipe, parent, false);
                return new RecipeViewHolder(view, onRecipeListener);
            }
            case LOADING_TYPE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_list_item, parent, false);
                return new LoadingViewHolder(view);
            }
            case CATEGORY_TYPE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category_list_item, parent, false);
                return new CategoryViewHolder(view, onRecipeListener);
            }

            default: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_recipe, parent, false);
                return new RecipeViewHolder(view, onRecipeListener);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int itemViewType = getItemViewType(position);
        if (itemViewType == RECIPE_TYPE) {
            ((RecipeViewHolder) holder).title.setText(recipes.get(position).getTitle());
            ((RecipeViewHolder) holder).publisher.setText(recipes.get(position).getPublisher());
            ((RecipeViewHolder) holder).rating.setText(String.valueOf(Math.round(recipes.get(position).getSocial_rank())));

            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);

            Glide.with(holder.itemView.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(recipes.get(position).getImage_url())
                    .into(((RecipeViewHolder) holder).image);
        } else if (itemViewType == CATEGORY_TYPE) {

            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);

            Uri path = Uri.parse("android.resource://com.example.recepti/drawable/" + recipes.get(position).getImage_url());
            Glide.with(holder.itemView.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(path)
                    .into(((CategoryViewHolder) holder).catImage);

            ((CategoryViewHolder) holder).catTitle.setText(recipes.get(position).getTitle());

        }

    }

    @Override
    public int getItemViewType(int position) {
        if (recipes.get(position).getTitle().equals("LOADING..")) {
            return LOADING_TYPE;
        } else if (recipes.get(position).getSocial_rank() == -1) {
            return CATEGORY_TYPE;
        } else {
            return RECIPE_TYPE;
        }
    }

    public void displayLoading() {
        if (!isLoading()) {
            Recipe recipe = new Recipe();
            recipe.setTitle("LOADING..");
            List<Recipe> loadingList = new ArrayList<>();
            loadingList.add(recipe);
            recipes = loadingList;
            notifyDataSetChanged();
        }
    }

    private boolean isLoading() {
        if (recipes != null) {
            if (recipes.size() > 0) {
                return recipes.get(recipes.size() - 1).getTitle().equals("LOADING..");
            }
        }
        return false;
    }

    public void displaySearchCategories() {
        List<Recipe> categories = new ArrayList<>();
        for (int i = 0; i < Constants.DEFAULT_SEARCH_CATEGORIES.length; i++) {
            Recipe recipe = new Recipe();
            recipe.setTitle(Constants.DEFAULT_SEARCH_CATEGORIES[i]);
            recipe.setImage_url(Constants.DEFAULT_SEARCH_CATEGORY_IMAGES[i]);
            recipe.setSocial_rank(-1);
            categories.add(recipe);
        }
        recipes = categories;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (recipes != null) {
            return recipes.size();
        }
        return 0;
    }

    public void setRecipes(List<Recipe> mRecipes) {
        recipes = mRecipes;
        notifyDataSetChanged();
    }
}

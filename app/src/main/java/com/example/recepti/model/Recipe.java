package com.example.recepti.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class Recipe implements Parcelable {

    private String recipe_id;
    private String title;
    private String publisher;
    private String image_url;
    private float social_rank;
    private String[] ingredients;

    public Recipe(String recipe_id, String title, String publisher, String image_url, float social_rank, String[] ingredients) {
        this.recipe_id = recipe_id;
        this.title = title;
        this.publisher = publisher;
        this.image_url = image_url;
        this.social_rank = social_rank;
        this.ingredients = ingredients;
    }

    public Recipe() {
    }

    protected Recipe(Parcel in) {
        recipe_id = in.readString();
        title = in.readString();
        publisher = in.readString();
        image_url = in.readString();
        social_rank = in.readFloat();
        ingredients = in.createStringArray();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(String recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public float getSocial_rank() {
        return social_rank;
    }

    public void setSocial_rank(float social_rank) {
        this.social_rank = social_rank;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "recipe_id='" + recipe_id + '\'' +
                ", title='" + title + '\'' +
                ", publisher='" + publisher + '\'' +
                ", image_url='" + image_url + '\'' +
                ", social_rank=" + social_rank +
                ", ingredients=" + Arrays.toString(ingredients) +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(recipe_id);
        dest.writeString(title);
        dest.writeString(publisher);
        dest.writeString(image_url);
        dest.writeFloat(social_rank);
        dest.writeStringArray(ingredients);
    }
}

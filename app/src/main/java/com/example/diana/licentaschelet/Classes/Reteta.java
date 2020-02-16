package com.example.diana.licentaschelet.Classes;


import java.util.ArrayList;

public class Reteta {
    private String title;
    private String recipeLink;
    private ArrayList<String> ingredients;
    private String imageLink;

    public Reteta(String title, String recipeLink, ArrayList<String> ingredients, String imageLink) {
        this.title = title;
        this.recipeLink = recipeLink;
        this.ingredients = ingredients;
        this.imageLink = imageLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRecipeLink() {
        return recipeLink;
    }

    public void setRecipeLink(String recipeLink) {
        this.recipeLink = recipeLink;
    }

    public String getIngredients() {
        String result = "";
        for(String s : ingredients){
            result = result + s + ", ";

        }
        return result;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    @Override
    public String toString() {
        return "Reteta{" +
                "title='" + title + '\'' +
                "\n recipeLink='" + recipeLink + '\'' +
                "\n ingredients=" + ingredients +
                "\n imageLink='" + imageLink + '\'' +
                '}';
    }
}

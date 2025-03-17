package com.example.recipeapp2.models;

import java.util.List;

public class RecipeResponse {
    public List<Recipe> results;

    public static class Recipe {
        public int id;
        public String title;
    }
}

package com.example.recipeapp2.api;

import com.example.recipeapp2.models.RecipeDetails;
import com.example.recipeapp2.models.RecipeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SpoonacularApi {
    @GET("recipes/complexSearch")
    Call<RecipeResponse> searchRecipes(
            @Query("query") String ingredient,
            @Query("number") int number,
            @Query("apiKey") String apiKey
    );

    @GET("recipes/{id}/information")
    Call<RecipeDetails> getRecipeDetails(
            @Path("id") int recipeId,
            @Query("apiKey") String apiKey
    );

    @GET("recipes/complexSearch")
    Call<RecipeResponse> searchRecipesAdvanced(
            @Query("query") String ingredient,
            @Query("cuisine") String cuisine,
            @Query("diet") String diet,
            @Query("number") int number,
            @Query("apiKey") String apiKey
    );
}

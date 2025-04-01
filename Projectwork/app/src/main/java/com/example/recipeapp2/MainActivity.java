package com.example.recipeapp2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipeapp2.api.RetrofitClient;
import com.example.recipeapp2.api.SpoonacularApi;
import com.example.recipeapp2.models.RecipeDetails;
import com.example.recipeapp2.models.RecipeResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText ingredientInput, recipeIdInput, cuisineInput, dietInput;
    private Button searchButton, detailsButton, advancedSearchButton;
    private TextView resultTextView;

    private final String API_KEY = "d1d0d987cf324e1a9c53cea4a4f2e58d"; // Replace with your Spoonacular API Key

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ingredientInput = findViewById(R.id.ingredientInput);
        recipeIdInput = findViewById(R.id.recipeIdInput);
        cuisineInput = findViewById(R.id.cuisineInput);
        dietInput = findViewById(R.id.dietInput);
        searchButton = findViewById(R.id.searchButton);
        detailsButton = findViewById(R.id.detailsButton);
        advancedSearchButton = findViewById(R.id.advancedSearchButton);
        resultTextView = findViewById(R.id.resultTextView);

        SpoonacularApi apiService = RetrofitClient.getInstance().create(SpoonacularApi.class);

        // Basic Recipe Search
        searchButton.setOnClickListener(v -> {
            String ingredient = ingredientInput.getText().toString();
            if (!ingredient.isEmpty()) {
                Call<RecipeResponse> call = apiService.searchRecipes(ingredient, 5, API_KEY);
                call.enqueue(new Callback<RecipeResponse>() {
                    @Override
                    public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            resultTextView.setText("Recipes Found:\n");
                            for (RecipeResponse.Recipe recipe : response.body().results) {
                                resultTextView.append(recipe.title + "\n");
                            }
                        } else {
                            resultTextView.setText("No recipes found.");
                        }
                    }

                    @Override
                    public void onFailure(Call<RecipeResponse> call, Throwable t) {
                        Log.e("API Error", t.getMessage());
                        resultTextView.setText("Failed to fetch recipes. Please try again.");
                    }
                });
            } else {
                resultTextView.setText("Please enter an ingredient to search for recipes.");
            }
        });

        // Get Detailed Recipe Information
        detailsButton.setOnClickListener(v -> {
            String recipeIdText = recipeIdInput.getText().toString();
            if (!recipeIdText.isEmpty() && recipeIdText.matches("\\d+")) {
                int recipeId = Integer.parseInt(recipeIdText);
                Call<RecipeDetails> call = apiService.getRecipeDetails(recipeId, API_KEY);
                call.enqueue(new Callback<RecipeDetails>() {
                    @Override
                    public void onResponse(Call<RecipeDetails> call, Response<RecipeDetails> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            RecipeDetails details = response.body();
                            resultTextView.setText("Title: " + details.title + "\nSummary: " + details.summary);
                        } else {
                            resultTextView.setText("No details found.");
                        }
                    }

                    @Override
                    public void onFailure(Call<RecipeDetails> call, Throwable t) {
                        Log.e("API Error", t.getMessage());
                        resultTextView.setText("Failed to fetch recipe details. Please try again.");
                    }
                });
            } else {
                resultTextView.setText("Please enter a valid numeric Recipe ID.");
            }
        });

        // Advanced Recipe Search (Cuisine and Diet)
        advancedSearchButton.setOnClickListener(v -> {
            String ingredient = ingredientInput.getText().toString();
            String cuisine = cuisineInput.getText().toString();
            String diet = dietInput.getText().toString();

            if (!ingredient.isEmpty()) {
                Call<RecipeResponse> call = apiService.searchRecipesAdvanced(ingredient, cuisine, diet, 5, API_KEY);
                call.enqueue(new Callback<RecipeResponse>() {
                    @Override
                    public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            resultTextView.setText("Advanced Recipes Found:\n");
                            for (RecipeResponse.Recipe recipe : response.body().results) {
                                resultTextView.append(recipe.title + "\n");
                            }
                        } else {
                            resultTextView.setText("No recipes found with the specified criteria.");
                        }
                    }

                    @Override
                    public void onFailure(Call<RecipeResponse> call, Throwable t) {
                        Log.e("API Error", t.getMessage());
                        resultTextView.setText("Failed to fetch advanced recipes. Please try again.");
                    }
                });
            } else {
                resultTextView.setText("Please enter an ingredient to perform an advanced search.");
            }
        });
    }
}
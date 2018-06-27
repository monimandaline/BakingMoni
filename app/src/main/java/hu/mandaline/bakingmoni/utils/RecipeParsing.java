package hu.mandaline.bakingmoni.utils;

import java.util.ArrayList;

import hu.mandaline.bakingmoni.model.Recipe_model;
import retrofit2.Call;
import retrofit2.http.GET;
import hu.mandaline.bakingmoni.utils.RecipesApi.RecipesApiConstants;

import static hu.mandaline.bakingmoni.utils.RecipesApi.RecipesApiConstants.JSON_FILE;

public interface RecipeParsing {
    @GET(JSON_FILE)
    Call<ArrayList<Recipe_model>> getRecipes();
}

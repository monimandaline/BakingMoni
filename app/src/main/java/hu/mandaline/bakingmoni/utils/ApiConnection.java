package hu.mandaline.bakingmoni.utils;

import static hu.mandaline.bakingmoni.utils.RecipesApi.RecipesApiConstants.BASE_URL;

public class ApiConnection {

    public static RecipeParsing getRecipe() {
        return RetrofitClient.getClient(BASE_URL).create(RecipeParsing.class);
    }
}

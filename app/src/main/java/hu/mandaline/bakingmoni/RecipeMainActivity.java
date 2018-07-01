package hu.mandaline.bakingmoni;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import hu.mandaline.bakingmoni.adapter.RecipeAdapter;
import hu.mandaline.bakingmoni.helper.RecipeData;
import hu.mandaline.bakingmoni.model.Ingredient_model;
import hu.mandaline.bakingmoni.model.Recipe_model;

import hu.mandaline.bakingmoni.utils.ApiConnection;
import hu.mandaline.bakingmoni.utils.RecipeParsing;
import hu.mandaline.bakingmoni.utils.ScreenColumnCalculator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeMainActivity extends AppCompatActivity implements RecipeAdapter.OnItemClickListener {

    private static final String TAG = "RecipesActivity";

    private ArrayList<Recipe_model> recipeList = new ArrayList<>();
    private RecipeAdapter recipeAdapter;
    private RecyclerView recipeRecyclerView;
    private ProgressBar progressBar;
    private TextView emptyStateTextView;
    private GridLayoutManager layoutManager;

    static final String DETAILS = "details";
    public Recipe_model RecipeDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_main);

        //Assign the views
        recipeRecyclerView = findViewById(R.id.rv_recipes);
        progressBar = findViewById(R.id.progress_bar);
        emptyStateTextView = findViewById(R.id.tv_empty_state);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        // Screen mode span settings
        int spanCount = 2;
        spanCount = ScreenColumnCalculator.calculateNoOfColumns(getApplicationContext());

        Toolbar toolbar = findViewById(R.id.recipe_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
        }

        // If there is no internet, it updates empty state with no connection error message
        if (!isConnectedToInternet(this)) {
            View progressBar = findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.GONE);
            emptyStateTextView.setVisibility(View.VISIBLE);
            emptyStateTextView.setText(R.string.no_internet_connection);

        // If there is internet connection, it loads data from json
        } else {
            emptyStateTextView.setVisibility(View.GONE);
            recipeRecyclerView.setVisibility(View.VISIBLE);
            layoutManager = new GridLayoutManager(this, spanCount);
            recipeRecyclerView.setLayoutManager(layoutManager);

            //Load recepies
            loadRecipes(this);
        }

    }

    public void setProgressBarVisibility(int gone) {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(int position, Recipe_model recipe, ImageView imageView) {

        Intent intent = new Intent(RecipeMainActivity.this, RecipeDetailsActivity.class);

        intent.putParcelableArrayListExtra("ingredients", (ArrayList<? extends Parcelable>) recipe.getIngredients());
        intent.putParcelableArrayListExtra("steps", (ArrayList <? extends Parcelable>) recipe.getSteps());

        intent.putExtra("recipe", recipe);
        intent.putExtra("name", recipe.getName());
        startActivity(intent);
    }

    // Sets recipelist to adapter after loading from json is done
    public void setRecipeList(ArrayList<Recipe_model> recipeList) {
        this.recipeList = recipeList;
        recipeAdapter = new RecipeAdapter(recipeList);
        recipeAdapter.setRecipeList(recipeList);
        recipeAdapter.setOnItemClickListener(this);
        recipeRecyclerView.setAdapter(recipeAdapter);
    }


    //Checks if device is connected to the internet or not
    public static boolean isConnectedToInternet(Context context) {
        boolean isConnectedToInternet = false;

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        assert connMgr != null; //line suggested by Lint
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        isConnectedToInternet = networkInfo != null && networkInfo.isConnected();
        return isConnectedToInternet;
    }


    // Downloads recipes from the url with Retrofit
    public static void loadRecipes(final Activity activity) {
        RecipeParsing recipeParsing = ApiConnection.getRecipe();
        recipeParsing.getRecipes().enqueue(new Callback<ArrayList<Recipe_model>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe_model>> call, Response<ArrayList<Recipe_model>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    ArrayList<Recipe_model> recipes = new ArrayList<>();
                    recipes.addAll(response.body());
                    Log.d("Utils", "Recipes loaded from API: " + recipes);

                    RecipeMainActivity recipeMainActivity = (RecipeMainActivity) activity;
                    recipeMainActivity.setRecipeList(recipes);
                    recipeMainActivity.setProgressBarVisibility(View.GONE);

                } else {
                    Log.d(TAG, "Status code : " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Recipe_model>> call, @NonNull Throwable t) {
                Toast.makeText(activity, "Error loading data from API: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Error loading from API" + t.getMessage());
                RecipeData.success = false;
            }
        });
    }



}

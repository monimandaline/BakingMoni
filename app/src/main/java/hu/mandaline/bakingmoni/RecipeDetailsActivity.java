package hu.mandaline.bakingmoni;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import hu.mandaline.bakingmoni.R;
import hu.mandaline.bakingmoni.fragments.IngredientsAndStepsFragment;
import hu.mandaline.bakingmoni.fragments.SingleStepFragment;
import hu.mandaline.bakingmoni.helper.RecipeData;
import hu.mandaline.bakingmoni.model.Ingredient_model;
import hu.mandaline.bakingmoni.model.Recipe_model;
//import hu.mandaline.bakingmoni.widget.BakingAppWidgetProvider;
//import hu.mandaline.bakingmoni.helper.RecipeData;
import hu.mandaline.bakingmoni.model.Recipe_model;
import hu.mandaline.bakingmoni.model.Step_model;

public class RecipeDetailsActivity extends AppCompatActivity implements IngredientsAndStepsFragment.OnStepListItemSelected
 {

    private boolean mTwoPane;
    private Recipe_model chosenRecipe;
    public Bundle bundle = new Bundle();
    public Recipe_model RecipeDetails;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Intent intent = getIntent();

        // Unwrapping the Parcel, get detail datas
        ArrayList<Ingredient_model> Ingredients = new ArrayList<Ingredient_model>();
        Ingredients = getIntent().getParcelableArrayListExtra("ingredients");

        ArrayList<Step_model> Steps = new ArrayList<Step_model>();
        Steps = getIntent().getParcelableArrayListExtra("steps");


        String name = getIntent().getExtras().getString("name");

        // full datamodel
        chosenRecipe = intent.getParcelableExtra("recipe");

        // detail data for fragment
        bundle.putParcelableArrayList("IngredientsForFragment", Ingredients);
        bundle.putParcelableArrayList("StepsForFragment", Steps);
        bundle.putString("CakeName", name);

/*
        chosenRecipe = RecipeData.recipe;
        if (chosenRecipe != null) {
            bundle.putParcelableArrayList("INGREDIENTS", chosenRecipe.getIngredients());
            bundle.putParcelableArrayList("STEPS", chosenRecipe.getSteps());
            bundle.putString("RECIPE_NAME", chosenRecipe.getName());
        }
        */


        // tablet
        if (findViewById((R.id.two_pane_layout)) != null) {
            mTwoPane = true;

            if (savedInstanceState == null) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                SingleStepFragment singleStepFragment = new SingleStepFragment();
                singleStepFragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.single_step_fragment, singleStepFragment)
                        .commit();

                IngredientsAndStepsFragment ingredientsAndStepsFragment = new IngredientsAndStepsFragment();
                ingredientsAndStepsFragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.ingredients_and_steps_layout, ingredientsAndStepsFragment)
                        .addToBackStack(null)
                        .commit();
            }
            // mobil
        } else {
            mTwoPane = false;

            FragmentManager fragmentManager = getSupportFragmentManager();
            IngredientsAndStepsFragment ingredientsAndStepsFragment = new IngredientsAndStepsFragment();
            ingredientsAndStepsFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.ingredients_and_steps_layout, ingredientsAndStepsFragment)
                    .commit();
        }


        Toolbar toolbar = findViewById(R.id.recipe_toolbar);

      /*  FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentUpdate = new Intent(RecipeDetailsActivity.this, BakingAppWidgetProvider.class);
                intentUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                int[] ids = AppWidgetManager.getInstance(getApplication())
                        .getAppWidgetIds(new ComponentName(getApplication(), BakingAppWidgetProvider.class));
                intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
                sendBroadcast(intentUpdate);
                Snackbar.make(view, "Ingredients added to widget!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/

 /*       if (savedInstanceState != null) {
            mTwoPane = savedInstanceState.getBoolean("PANE");
            chosenRecipe = savedInstanceState.getParcelable("RECIPE");
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle(chosenRecipe.getName());
            }
            return;
        }


*/
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(name);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTwoPane == true) {
                    onBackPressed();
                } else {
                    FragmentManager fm = getSupportFragmentManager();
                    if (mTwoPane == false && findViewById(R.id.frame_single_step) != null) {
                        fm.popBackStack(); //goes back to ingredients and steps fragment
                    } else {
                        //goes back to recipe list screen
                        finish();
                        Intent intent = new Intent(RecipeDetailsActivity.this, RecipeMainActivity.class);
                        RecipeData.recipe = null;
                        startActivity(intent);
                    }
                }
            }
        });

    }


    @Override
    public void onBackPressed() {
        RecipeData.stepIndex = 0;
        if (mTwoPane) {
            finish();
            super.onBackPressed();
        } else if (findViewById(R.id.frame_single_step) != null) {
            getSupportFragmentManager().popBackStack();
        } else if (findViewById(R.id.frame_ingredients_and_steps) != null) {
            finish();
            Intent intent = new Intent(this, RecipeMainActivity.class);
            RecipeData.recipe = null;
            startActivity(intent);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putBoolean("PANE", mTwoPane);
        currentState.putParcelable("RECIPE", chosenRecipe);
    }


    // Define the behavior for onStepListItemSelected
    @Override
    public void onStepListItemSelected(int listIndex) {
        Toast.makeText(this, "Position clicked = " + listIndex, Toast.LENGTH_SHORT).show();

        //RecipeData.stepIndex = listIndex;
        //chosenRecipe
        //chosenRecipe = RecipeData.recipe;

        if (mTwoPane) {
            SingleStepFragment newSingleStepFragment = new SingleStepFragment();
            newSingleStepFragment.setListIndex(listIndex);
            bundle.putParcelableArrayList("StepsForFragment", chosenRecipe.getSteps());
            newSingleStepFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.single_step_fragment, newSingleStepFragment)
                    .commit();
        } else {
            SingleStepFragment newSingleStepFragment = new SingleStepFragment();
            newSingleStepFragment.setListIndex(listIndex);
            bundle.putParcelableArrayList("StepsForFragment", chosenRecipe.getSteps());
            newSingleStepFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_ingredients_and_steps, newSingleStepFragment)
                    .addToBackStack("STEP_STACK")
                    .commit();
        }
    }

}
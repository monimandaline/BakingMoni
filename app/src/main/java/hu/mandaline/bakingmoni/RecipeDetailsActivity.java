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

import hu.mandaline.bakingmoni.fragments.IngredientsAndStepsFragment;
import hu.mandaline.bakingmoni.fragments.SingleStepFragment;
import hu.mandaline.bakingmoni.model.Ingredient_model;
import hu.mandaline.bakingmoni.model.Step_model;
import hu.mandaline.bakingmoni.widget.WidgetProvider;
import hu.mandaline.bakingmoni.widget.WidgetData;

public class RecipeDetailsActivity extends AppCompatActivity implements IngredientsAndStepsFragment.OnStepListItemSelected
 {

    private boolean mTwoPane;
    public Bundle bundle = new Bundle();
    public ArrayList<Step_model> Steps = new ArrayList<Step_model>();
    public ArrayList<Ingredient_model> Ingredients = new ArrayList<Ingredient_model>();
    public String Name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        // Unwrapping the Parcel, get detail datas
        Ingredients = getIntent().getParcelableArrayListExtra("ingredients");
        Steps = getIntent().getParcelableArrayListExtra("steps");
        Name = getIntent().getExtras().getString("name");

        // Detail data for fragment
        bundle.putParcelableArrayList("IngredientsForFragment", Ingredients);
        bundle.putParcelableArrayList("StepsForFragment", Steps);
        bundle.putString("CakeName", Name);

        // tablet
        if (findViewById((R.id.double_pane_layout)) != null) {
            mTwoPane = true;

            if (savedInstanceState == null) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                SingleStepFragment singleStepFragment = new SingleStepFragment();
                singleStepFragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.single_step_layout, singleStepFragment)
                        .commit();

                IngredientsAndStepsFragment ingredientsAndStepsFragment = new IngredientsAndStepsFragment();
                ingredientsAndStepsFragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.ingredients_and_steps_layout, ingredientsAndStepsFragment)
                        .addToBackStack(null)
                        .commit();
            }
        // phone
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

        FloatingActionButton fab = findViewById(R.id.fab_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentUpdate = new Intent(RecipeDetailsActivity.this, WidgetProvider.class);
                intentUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

                int[] ids = AppWidgetManager.getInstance(getApplication())
                        .getAppWidgetIds(new ComponentName(getApplication(), WidgetProvider.class));
                intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);

                WidgetData.widget_ingredients = Ingredients;
                WidgetData.widget_cake_name = Name;

                sendBroadcast(intentUpdate);
                Snackbar.make(view, "Ingredients added to widget!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        if (savedInstanceState != null) {
            mTwoPane = savedInstanceState.getBoolean("pane");
            Ingredients = savedInstanceState.getParcelableArrayList("ingredients");
            Steps = savedInstanceState.getParcelableArrayList("steps");
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle(Name);
            }
            return;
        }



        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(Name);
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
                        startActivity(intent);
                    }
                }
            }
        });

    }


    @Override
    public void onBackPressed() {
        if (mTwoPane) {
            finish();
            super.onBackPressed();
        } else if (findViewById(R.id.frame_single_step) != null) {
            getSupportFragmentManager().popBackStack();
        } else if (findViewById(R.id.frame_ingredients_and_steps) != null) {
            finish();
            Intent intent = new Intent(this, RecipeMainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putBoolean("pane", mTwoPane);
        currentState.putParcelableArrayList("steps", Steps);
        currentState.putParcelableArrayList("ingredients", Ingredients);
    }


    // Define the behavior for onStepListItemSelected
    @Override
    public void onStepListItemSelected(int listIndex) {
        Toast.makeText(this, "Position clicked = " + listIndex, Toast.LENGTH_SHORT).show();


        if (mTwoPane) {
            SingleStepFragment newSingleStepFragment = new SingleStepFragment();
            bundle.putParcelableArrayList("StepsForFragment", Steps);
            bundle.putInt("listIndex", listIndex);
            newSingleStepFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.single_step_layout, newSingleStepFragment)
                    .commit();
        } else {
            SingleStepFragment newSingleStepFragment = new SingleStepFragment();
            bundle.putParcelableArrayList("StepsForFragment", Steps);
            bundle.putInt("listIndex", listIndex);
            newSingleStepFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_ingredients_and_steps, newSingleStepFragment)
                    .addToBackStack("step_back")
                    .commit();
        }
    }

}
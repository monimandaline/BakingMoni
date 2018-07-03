package hu.mandaline.bakingmoni.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import hu.mandaline.bakingmoni.R;
import hu.mandaline.bakingmoni.adapter.IngredientsListAdapter;
import hu.mandaline.bakingmoni.adapter.StepListAdapter;
import hu.mandaline.bakingmoni.model.Ingredient_model;
import hu.mandaline.bakingmoni.model.Step_model;

// A simple {@link Fragment} subclass.

public class IngredientsAndStepsFragment extends Fragment {


    private ArrayList<Ingredient_model> ingredientList;
    private ArrayList<Step_model> stepList;
    private OnStepListItemSelected lCallback;
    private TextView recipeNameView;
    private String recipeName;
    RecyclerView stepsView;
    RecyclerView ingredientsView;

    // Mandatory empty constructor
    public IngredientsAndStepsFragment() {
    }

   public interface OnStepListItemSelected {
          void onStepListItemSelected(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            lCallback = (OnStepListItemSelected) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    // Inflates ingredients and steps views
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_ingredients_and_steps, container, false);

        recipeNameView = (TextView) rootView.findViewById(R.id.tv_recipe_name);
        stepsView = (RecyclerView) rootView.findViewById(R.id.rv_steps);
        ingredientsView = (RecyclerView) rootView.findViewById(R.id.rv_ingredients);

        if (savedInstanceState != null) {
            ingredientList = savedInstanceState.getParcelableArrayList("SIngredient");
            stepList = savedInstanceState.getParcelableArrayList("SStep");
            recipeName = savedInstanceState.getString("SName");

        } else {
            if (getArguments() != null) {
                ingredientList = getArguments().getParcelableArrayList("IngredientsForFragment");
                stepList = getArguments().getParcelableArrayList("StepsForFragment");
                recipeName = getArguments().getString("CakeName");
            }
        }

        String ingredients_title = getResources().getString(R.string.ingredients_title);
        recipeNameView.setText(recipeName + "\u0020" + ingredients_title);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        stepsView.setLayoutManager(layoutManager);
        ingredientsView.setLayoutManager(layoutManager2);

        StepListAdapter stepAdapter = new StepListAdapter(stepList, new StepListAdapter.OnStepListItemClickListener() {
            @Override
            public void onStepListItemClick(Step_model item) {
                   lCallback.onStepListItemSelected(item.getId());
            }
        });

        IngredientsListAdapter ingredientsAdapter = new IngredientsListAdapter(ingredientList);

        // Set the adapter on the view
        stepsView.setAdapter(stepAdapter);
        ingredientsView.setAdapter(ingredientsAdapter);

        return rootView;
    }

    // Save the current state of this fragment
    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putParcelableArrayList("SIngredient", ingredientList);
        currentState.putParcelableArrayList("SStep", stepList);
        currentState.putString("SName", recipeName);
    }
}
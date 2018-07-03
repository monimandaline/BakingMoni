package hu.mandaline.bakingmoni.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import hu.mandaline.bakingmoni.R;
import hu.mandaline.bakingmoni.model.Ingredient_model;

// RecyclerView adapter to Ingredients
public class IngredientsListAdapter extends RecyclerView.Adapter<IngredientsListAdapter.IngredientsAdapterViewHolder> {


    public List<Ingredient_model> ingredientsList;

    public IngredientsListAdapter(List<Ingredient_model> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    @Override
    public IngredientsListAdapter.IngredientsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.ingredient_card, viewGroup, false);

        return new IngredientsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientsListAdapter.IngredientsAdapterViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Ingredient_model ingredients = ingredientsList.get(position);

        holder.tv_ingredient_measure.setText(ingredients.getMeasure());
        holder.tv_ingredient_name.setText(ingredients.getIngredient());
        holder.tv_ingredient_quantity.setText(String.valueOf(ingredients.getQuantity()));
    }

    @Override
    public int getItemCount() {
        if (null == ingredientsList) return 0;
        return ingredientsList.size();
    }

    // ViewHolder
    public class IngredientsAdapterViewHolder extends RecyclerView.ViewHolder  {

        CardView cardView;
        TextView tv_ingredient_quantity;
        TextView tv_ingredient_measure;
        TextView tv_ingredient_name;

        public IngredientsAdapterViewHolder(final View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cv_ingredient);
            tv_ingredient_quantity = itemView.findViewById(R.id.tv_ingredient_quantity);
            tv_ingredient_measure = itemView.findViewById(R.id.tv_ingredient_measure);
            tv_ingredient_name = itemView.findViewById(R.id.tv_ingredient);
        }
    }

 /*   // Helper method to set the actual ingredients list into the recyclerview on the activity
    public void setIngredientsList(ArrayList<Ingredient_model> ingredients) {
        ingredientsList = ingredients;
        notifyDataSetChanged();
    }*/

}

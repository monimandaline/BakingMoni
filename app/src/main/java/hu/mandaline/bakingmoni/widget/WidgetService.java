package hu.mandaline.bakingmoni.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

import hu.mandaline.bakingmoni.R;
import hu.mandaline.bakingmoni.model.Ingredient_model;


public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetRemoteViewsFactory(this.getApplicationContext());
    }

    public class WidgetRemoteViewsFactory implements RemoteViewsFactory {

        Context context;

        ArrayList<Ingredient_model> WidgetIngredients;
        Ingredient_model ingredient;


        WidgetRemoteViewsFactory(Context context) {
            this.context = context;
        }

        private void setIngredientsData(){
            if(WidgetIngredients != null){
                WidgetIngredients = WidgetData.widget_ingredients;
            } else{
                WidgetIngredients = new ArrayList<>();
            }
        }

        @Override
        public void onCreate() {

            // Unwrapping the Parcel, get detail datas
            WidgetIngredients = WidgetData.widget_ingredients;

        }

        //load data for the list
        @Override
        public void onDataSetChanged() {
            setIngredientsData();
        }

        @Override
        public void onDestroy() {
        }

        @Override
        public int getCount() {
            if(WidgetIngredients == null)
                return 0;
            else
                return WidgetIngredients.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            ingredient = WidgetIngredients.get(position);
            RemoteViews remoteView = new RemoteViews(getPackageName(), R.layout.widget_list_item);
            remoteView.setTextViewText(R.id.widget_quantity, String.valueOf(ingredient.getQuantity()));
            remoteView.setTextViewText(R.id.widget_measure, ingredient.getMeasure());
            remoteView.setTextViewText(R.id.widget_ingredient, ingredient.getIngredient());
            return remoteView;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }



    }
}

package hu.mandaline.bakingmoni.widget;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.ArrayList;

import hu.intellicode.bakingapp.R;
import hu.intellicode.bakingapp.helper.RecipeData;
import hu.intellicode.bakingapp.models.Recipe;
import hu.intellicode.bakingapp.ui.RecipesActivity;
import hu.mandaline.bakingmoni.RecipeMainActivity;
import hu.mandaline.bakingmoni.model.Ingredient_model;
import hu.mandaline.bakingmoni.model.Recipe_model;

/**
 * Implementation of App Widget functionality.
 * Tutorial used for immediate widget update: https://code.tutsplus.com/tutorials/code-a-widget-for-your-android-app-updating-your-widget--cms-30528
 * Also big thanks to my fellow classmate Benerice, who helped me in debugging to make the widget correctly working
 */



public class BakingAppWidgetProvider extends AppWidgetProvider {


    private static Bundle args;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

       /* Intent intent = getIntent();

        // Unwrapping the Parcel, get detail datas
        Ingredients = getIntent().getParcelableArrayListExtra("WidgetIgnredients");
        Name = getIntent().getExtras().getString("WidgetName");


        String recipeNameText = "";
        if (recipe != null) {
            recipeNameText = recipe.getName();
        }*/

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        views.setTextViewText(R.id.appwidget_text, recipeNameText);

        //opens app on widget click
        Intent intent = new Intent(context, RecipeMainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

        //populates the list in the widget
        Intent intentUpdate = new Intent(context, WidgetService.class);
        intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetId);
        views.setRemoteAdapter(R.id.listview_widget, intentUpdate);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.listview_widget);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget);
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }



}


package hu.mandaline.bakingmoni.utils;

import android.content.Context;
import android.util.DisplayMetrics;


public class ScreenColumnCalculator {
        public static int calculateNoOfColumns(Context context) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
            int noOfColumns = (int) (dpWidth / 299);
            return noOfColumns;
        }
    }

package cz.koto.misak.dbshowcase.android.mobile.util;

import android.content.Context;
import android.util.DisplayMetrics;


public class UnitConverter {

    public static float convertPixelsToDp(float px, Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return px / (metrics.densityDpi / 160f);
    }


}

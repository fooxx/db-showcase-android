package cz.koto.misak.dbshowcase.android.mobile.utility;

import android.support.v7.app.ActionBar;
import android.view.View;

/**
 * Created by Misak on 21/04/16.
 */
public class ViewUpdater {

    public static void setUpDetailActionBar(ActionBar actionBar) {
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);//back arrow button
            actionBar.setDisplayShowHomeEnabled(true);

            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    public static void setViewHeight(View view, int height, boolean layout) {
        view.getLayoutParams().height = height;
        if (layout) {
            view.requestLayout();
        }
    }

}

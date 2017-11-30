package cz.koto.misak.dbshowcase.android.mobile.utility;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;


public class ContextProvider {
	private static Context sContext;


	private ContextProvider() {
	}


	public static void initialize(Context context) {
		sContext = context;
	}


	public static Context getContext() {
		if(sContext == null)
			throw new IllegalStateException("Context was not properly initialized. You should call ContextProvider.initialize(this) in Application's constructor.");
		return sContext;
	}


	public static Resources getResources() {
		return getContext().getResources();
	}


	public static String getString(@StringRes int resourceId) {
		return getResources().getString(resourceId);
	}


	public static String getString(@StringRes int resourceId, Object... args) {
		return getResources().getString(resourceId, args);
	}


	public static int getColor(@ColorRes int resourceId) {
		return ContextCompat.getColor(getContext(), resourceId);
	}


	public static Drawable getDrawable(@DrawableRes int resourceId) {
		return ContextCompat.getDrawable(getContext(), resourceId);
	}
}

package cz.koto.misak.dbshowcase.android.mobile.ui.base;

import android.databinding.BindingAdapter;
import android.view.View;


public class BindingAdapters {


	@BindingAdapter("hide")
	public static void setHide(View view, boolean hide) {
		view.setVisibility(hide ? View.GONE : View.VISIBLE);
	}


	@BindingAdapter("show")
	public static void setShow(View view, boolean show) {
		view.setVisibility(show ? View.VISIBLE : View.GONE);
	}


	@BindingAdapter("invisible")
	public static void setInvisible(View view, boolean invisible) {
		view.setVisibility(invisible ? View.INVISIBLE : View.VISIBLE);
	}


	@BindingAdapter("visible")
	public static void setVisible(View view, boolean visible) {
		view.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
	}

}

package cz.koto.misak.dbshowcase.android.mobile.ui.base;

import android.databinding.BindingAdapter;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cz.koto.misak.dbshowcase.android.mobile.utility.FileUtils;


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


	@BindingAdapter("humanReadableByteCount")
	public static void setHumanReadableByteCount(TextView textView, long byteCount) {
		textView.setText(FileUtils.humanReadableByteCount(byteCount, true));
	}


	@BindingAdapter("drawable")
	public static void setImageDrawable(ImageView view, @DrawableRes int resource) {
		view.setImageResource(resource);
	}
}

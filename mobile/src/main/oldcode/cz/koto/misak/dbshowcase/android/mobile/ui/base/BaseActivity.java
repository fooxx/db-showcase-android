package cz.koto.misak.dbshowcase.android.mobile.ui.base;

import android.app.Activity;
import android.databinding.ViewDataBinding;
import android.support.design.widget.Snackbar;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import cz.kinst.jakub.viewmodelbinding.ViewModel;
import cz.kinst.jakub.viewmodelbinding.ViewModelActivity;


public abstract class BaseActivity<T extends ViewDataBinding, S extends ViewModel> extends ViewModelActivity<T, S> implements SnackbarProvider {

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == android.R.id.home)
			onBackPressed();
		return super.onOptionsItemSelected(item);
	}


	@Override
	public void showSnackBar(String text) {
		Snackbar.make(getBinding().getRoot(), text, Snackbar.LENGTH_SHORT).show();
	}


	public void hideSoftKeyboard() {
		if(getCurrentFocus() != null) {
			InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
		}
	}
}

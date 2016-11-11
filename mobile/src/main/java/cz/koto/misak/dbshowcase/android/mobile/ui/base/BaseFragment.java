package cz.koto.misak.dbshowcase.android.mobile.ui.base;

import android.databinding.ViewDataBinding;
import android.support.design.widget.Snackbar;

import cz.kinst.jakub.viewmodelbinding.ViewModel;
import cz.kinst.jakub.viewmodelbinding.ViewModelFragment;
import cz.koto.misak.dbshowcase.android.mobile.R;
import cz.koto.misak.dbshowcase.android.mobile.utility.ContextProvider;


public abstract class BaseFragment<T extends ViewDataBinding, S extends ViewModel<T>> extends ViewModelFragment<T, S> implements SnackbarProvider {

	@Override
	public void showSnackBar(String text) {
		if(getBinding() != null && getBinding().getRoot() != null)
			try {
				Snackbar.make(getBinding().getRoot(), text, Snackbar.LENGTH_SHORT).show();
			} catch(Exception e) {
				e.printStackTrace();
			}
	}


	public int getStatusBarColor() {
		return ContextProvider.getColor(R.color.theme_primary_dark);
	}


	public boolean handlesBackButton() {
		return false;
	}


	public void onBackButtonPressed() {

	}
}

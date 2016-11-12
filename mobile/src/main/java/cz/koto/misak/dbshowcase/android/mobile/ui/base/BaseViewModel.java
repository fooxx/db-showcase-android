package cz.koto.misak.dbshowcase.android.mobile.ui.base;

import android.databinding.ViewDataBinding;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;

import cz.kinst.jakub.viewmodelbinding.ViewModel;
import cz.koto.misak.dbshowcase.android.mobile.R;
import cz.koto.misak.dbshowcase.android.mobile.ui.navigation.NavigationManager;
import cz.koto.misak.dbshowcase.android.mobile.ui.navigation.NavigationProvider;


public abstract class BaseViewModel<T extends ViewDataBinding> extends ViewModel<T> implements SnackbarProvider, NavigationProvider {

	@Override
	public void onViewAttached(boolean firstAttachment) {
		super.onViewAttached(firstAttachment);
		//if(getNavigationManager() != null)
		//getNavigationManager().configureToolbar(getToolbar(), null, null, null, false);
	}


	@Override
	public void showSnackBar(String text) {
		runOnUiThread(() -> ((SnackbarProvider) getView()).showSnackBar(text));
	}


	@Override
	public NavigationManager getNavigationManager() {
		if(getActivity() instanceof NavigationProvider)
			return ((NavigationProvider) getActivity()).getNavigationManager();
		else
			return null;
	}


	public void showSnackBar(@StringRes int textResource) {
		runOnUiThread(() -> ((SnackbarProvider) getView()).showSnackBar(getString(textResource)));
	}


	public Toolbar getToolbar() {
		return ((Toolbar) getBinding().getRoot().findViewById(R.id.toolbar));
	}

}

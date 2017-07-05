package cz.koto.misak.dbshowcase.android.mobile.ui.base;

import android.databinding.ViewDataBinding;
import android.support.annotation.StringRes;

import cz.kinst.jakub.viewmodelbinding.ViewModel;
import cz.koto.misak.dbshowcase.android.mobile.ui.navigation.NavigationManager;
import cz.koto.misak.dbshowcase.android.mobile.ui.navigation.NavigationProvider;


public abstract class BaseViewModel<T extends ViewDataBinding> extends ViewModel implements SnackbarProvider, NavigationProvider {

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


//	public Toolbar getToolbar() {
//		return ((Toolbar) getBinding().getRoot().findViewById(R.id.toolbar));
//	}
//
//
//	public void updateToolbar() {
//		PersistenceType activePersistenceType = ModelProvider.get().getActivePersistenceType();
//		PersistenceSyncState activePersistenceSyncState = ModelProvider.get().getActivePersistenceSyncState();
//		getNavigationManager().configureToolbar(getToolbar(),
//				activePersistenceType == null ? null : ContextProvider.getString(activePersistenceType.getStringRes()),
//				activePersistenceType == null ? null : activePersistenceType.getIconRes(),
//				activePersistenceSyncState == null ? null : ContextProvider.getString(activePersistenceSyncState.getDescRes()),
//				activePersistenceSyncState == null ? null : activePersistenceSyncState.getIconRes(),
//				false);
//	}

}

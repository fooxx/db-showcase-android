package cz.koto.misak.dbshowcase.android.mobile.ui.navigation;


import cz.koto.misak.dbshowcase.android.mobile.ui.control.ControlRootFragment;


public class ControlNavigationManager {
	private final NavigationManager mParentNavigationManager;


	ControlNavigationManager(NavigationManager navigationManager) { mParentNavigationManager = navigationManager; }


	public void switchToRoot() {
		getNavigationManager().clearBackStack();
		getNavigationManager().switchToFragment(ControlRootFragment.newInstance(), false);
	}


	public void switchToRoot(Boolean encryptionRequested) {
		getNavigationManager().clearBackStack();
		getNavigationManager().switchToFragment(ControlRootFragment.newInstance(encryptionRequested), false);
		getNavigationManager().selectNavigationItem(1);
	}

	private NavigationManager getNavigationManager() {
		return mParentNavigationManager;
	}
}
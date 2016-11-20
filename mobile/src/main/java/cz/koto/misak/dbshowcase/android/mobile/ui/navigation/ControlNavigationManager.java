package cz.koto.misak.dbshowcase.android.mobile.ui.navigation;


import cz.koto.misak.dbshowcase.android.mobile.ui.settings.SettingsRootFragment;


public class ControlNavigationManager {
	private final NavigationManager mParentNavigationManager;


	ControlNavigationManager(NavigationManager navigationManager) { mParentNavigationManager = navigationManager; }


	public void switchToRoot() {
		getNavigationManager().clearBackStack();
		getNavigationManager().switchToFragment(SettingsRootFragment.newInstance(), false);
	}


	private NavigationManager getNavigationManager() {
		return mParentNavigationManager;
	}
}
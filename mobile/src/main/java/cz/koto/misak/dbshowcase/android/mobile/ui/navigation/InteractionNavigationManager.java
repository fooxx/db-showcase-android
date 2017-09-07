package cz.koto.misak.dbshowcase.android.mobile.ui.navigation;


import cz.koto.misak.dbshowcase.android.mobile.ui.interaction.InteractionRootFragment;


public class InteractionNavigationManager {
	private final NavigationManager mParentNavigationManager;


	InteractionNavigationManager(NavigationManager navigationManager) { mParentNavigationManager = navigationManager; }


	public void switchToRoot() {
		getNavigationManager().clearBackStack();
		getNavigationManager().switchToFragment(InteractionRootFragment.newInstance(), false);
		getNavigationManager().selectNavigationItem(0);
	}


	private NavigationManager getNavigationManager() {
		return mParentNavigationManager;
	}
}
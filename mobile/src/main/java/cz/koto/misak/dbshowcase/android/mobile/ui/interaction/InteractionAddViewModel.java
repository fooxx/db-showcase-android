package cz.koto.misak.dbshowcase.android.mobile.ui.interaction;


import cz.koto.misak.dbshowcase.android.mobile.R;
import cz.koto.misak.dbshowcase.android.mobile.api.OnLoadResultListener;
import cz.koto.misak.dbshowcase.android.mobile.model.ModelProvider;
import cz.koto.misak.dbshowcase.android.mobile.ui.navigation.NavigationProvider;


public class InteractionAddViewModel implements InteractionCard, OnLoadResultListener {

	private NavigationProvider mNavigationProvider;


	public InteractionAddViewModel(NavigationProvider navigationProvider) {
		mNavigationProvider = navigationProvider;
	}


	public static InteractionCard getInstance(NavigationProvider navigationProvider) {
		return new InteractionAddViewModel(navigationProvider);
	}


	public int getPagerLayoutResource() {
		return R.layout.item_interaction_add;
	}


	@Override
	public void loadSuccess() {
		mNavigationProvider.getNavigationManager().getInteractionNavigationManager().switchToRoot();
	}


	@Override
	public void loadFailed(Throwable throwable) {

	}


	public void addCompleteRandomSchoolClass() {
	}


	public void downloadSchoolClassFromApi() {
		ModelProvider.get().updateModelFromApi(this);
	}


	public Boolean isNetworkAvailable() {
		return true;
	}
}

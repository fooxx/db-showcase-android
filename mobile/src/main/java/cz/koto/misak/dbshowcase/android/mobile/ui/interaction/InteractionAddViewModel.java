package cz.koto.misak.dbshowcase.android.mobile.ui.interaction;


import cz.koto.misak.dbshowcase.android.mobile.R;


public class InteractionAddViewModel implements InteractionCard {
	public static InteractionCard getInstance() {
		return new InteractionAddViewModel();
	}


	public int getPagerLayoutResource() {
		return R.layout.item_interaction_add;
	}


	public void addCompleteRandomSchoolClass() {
	}


	public void downloadSchoolClassFromApi() {

	}


	public Boolean isNetworkAvailable() {
		return true;
	}


}

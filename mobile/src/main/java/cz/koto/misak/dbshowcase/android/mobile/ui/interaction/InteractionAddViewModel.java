package cz.koto.misak.dbshowcase.android.mobile.ui.interaction;


import cz.koto.misak.dbshowcase.android.mobile.R;
import cz.koto.misak.dbshowcase.android.mobile.model.DataHandlerListener;
import cz.koto.misak.dbshowcase.android.mobile.model.ModelProvider;
import cz.koto.misak.dbshowcase.android.mobile.model.SchoolModel;
import cz.koto.misak.dbshowcase.android.mobile.model.utility.SchoolModelGenerator;
import cz.koto.misak.dbshowcase.android.mobile.persistence.realm.model.SchoolClassRealmEntity;
import cz.koto.misak.dbshowcase.android.mobile.ui.StateListener;
import cz.koto.misak.dbshowcase.android.mobile.ui.navigation.NavigationProvider;
import timber.log.Timber;


public class InteractionAddViewModel implements InteractionCard, DataHandlerListener {

	private NavigationProvider mNavigationProvider;
	private StateListener mStateListener;


	public InteractionAddViewModel(NavigationProvider navigationProvider, StateListener stateListener) {
		mNavigationProvider = navigationProvider;
		mStateListener = stateListener;
	}


	public static InteractionCard getInstance(NavigationProvider navigationProvider, StateListener stateListener) {
		return new InteractionAddViewModel(navigationProvider, stateListener);
	}


	public int getPagerLayoutResource() {
		return R.layout.item_interaction_add;
	}


	@Override
	public void handleSuccess() {
		if(mStateListener != null)
			mStateListener.setContent();
		mNavigationProvider.getNavigationManager().getInteractionNavigationManager().switchToRoot();
	}


	@Override
	public void handleFailed(Throwable throwable) {
		if(mStateListener != null)
			mStateListener.setContent();
		Timber.e(throwable, "InteractionAddViewModel was unable to add new school class!");
	}


	public void addCompleteRandomSchoolClass() {
		if(mStateListener != null)
			mStateListener.setProgress();
		SchoolModel model = ModelProvider.get().getSchoolModel();
		SchoolClassRealmEntity s = SchoolModelGenerator.generateSchoolClassRealmEntity(model.getTeachersCount(),
				model.getStudentsCount(), model.getSchoolClassCount());
		ModelProvider.get().addSchoolClass(s, this);
	}


	public void downloadSchoolClassFromApi() {
		if(mStateListener != null)
			mStateListener.setProgress();
		ModelProvider.get().initModelFromApi(this);
	}


	public Boolean isNetworkAvailable() {
		return true;
	}
}

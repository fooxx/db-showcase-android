package cz.koto.misak.dbshowcase.android.mobile.ui.interaction;


import cz.koto.misak.dbshowcase.android.mobile.R;
import cz.koto.misak.dbshowcase.android.mobile.model.DataHandlerListener;
import cz.koto.misak.dbshowcase.android.mobile.model.ModelProvider;
import cz.koto.misak.dbshowcase.android.mobile.ui.StateListener;
import cz.koto.misak.dbshowcase.android.mobile.ui.navigation.NavigationProvider;
import timber.log.Timber;


public class InteractionAddCardViewModel implements InteractionCard, DataHandlerListener {

	private NavigationProvider mNavigationProvider;
	private StateListener mStateListener;
	private DataHandlerListener mDataHandlerListener;


	public InteractionAddCardViewModel(NavigationProvider navigationProvider, StateListener stateListener, DataHandlerListener dataHandlerListener) {
		mNavigationProvider = navigationProvider;
		mStateListener = stateListener;
		mDataHandlerListener = dataHandlerListener;
	}


	public static InteractionCard getInstance(NavigationProvider navigationProvider, StateListener stateListener, DataHandlerListener dataHandlerListener) {
		return new InteractionAddCardViewModel(navigationProvider, stateListener, dataHandlerListener);
	}


	public int getPagerLayoutResource() {
		return R.layout.item_interaction_add_card;
	}


	@Override
	public void handleSuccess() {
		if(mStateListener != null)
			mStateListener.setContent();
		mDataHandlerListener.handleSuccess();
	}


	@Override
	public void handleFailed(Throwable throwable) {
		if(mStateListener != null)
			mStateListener.setContent();
		Timber.e(throwable, "InteractionAddCardViewModel was unable to add new school class!");
		mDataHandlerListener.handleFailed(throwable);
	}


	public void addCompleteRandomSchoolClass() {
		if(mStateListener != null)
			mStateListener.setProgress();
		ModelProvider.get().addRandomSchoolClass(this);
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

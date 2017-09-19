package cz.koto.misak.dbshowcase.android.mobile.ui;

import android.databinding.Bindable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import cz.kinst.jakub.viewmodelbinding.ViewModel;
import cz.koto.keystorecompat.KeystoreCompat;
import cz.koto.keystorecompat.exception.ForceLockScreenKitKatException;
import cz.koto.keystorecompat.utility.IntentUtilityKt;
import cz.koto.misak.dbshowcase.android.mobile.DbApplication;
import cz.koto.misak.dbshowcase.android.mobile.DbKeystoreCompatConfig;
import cz.koto.misak.dbshowcase.android.mobile.R;
import cz.koto.misak.dbshowcase.android.mobile.model.ModelProvider;
import cz.koto.misak.dbshowcase.android.mobile.model.SchoolModel;
import kotlin.Unit;


public class MainViewModel extends ViewModel {


	public final ObservableBoolean progress = new ObservableBoolean(false);
	public final ObservableField<SchoolModel> schoolModel = new ObservableField<>();
	public final int FORCE_SIGNUP_REQUEST = 1111;
	public ObservableField<String> storageType = new ObservableField<>();
	public ObservableField<Integer> storageTypeIcon = new ObservableField<>(R.drawable.ic_database_black_24dp);
	public ObservableField<String> storageState = new ObservableField<>();
	public ObservableField<Integer> storageStateIcon = new ObservableField<>();


	@Override
	public void onViewModelCreated() {
		super.onViewModelCreated();
		KeystoreCompat.INSTANCE.setConfig(new DbKeystoreCompatConfig());
	}


	@Override
	public void onResume() {
		super.onResume();
	}


	@Override
	public void onViewAttached(boolean firstAttachment) {
		super.onViewAttached(firstAttachment);
		DbApplication.get().getDbComponent().provideShowcaseRealmLoadModule().initConfig(
				() -> {
					//((MainActivity) getActivity()).getNavigationManager().getInteractionNavigationManager().switchToRoot();
				}, exception -> {
					ModelProvider.get().dismissForceLockScreenFlag();
					if(exception instanceof ForceLockScreenKitKatException) {
						getActivity().startActivityForResult(((ForceLockScreenKitKatException) exception).getLockIntent(), FORCE_SIGNUP_REQUEST);
					} else {
						IntentUtilityKt.forceAndroidAuth(getString(R.string.kc_lock_screen_title), getString(R.string.kc_lock_screen_description),
								intent -> {
									getActivity().startActivityForResult(intent, FORCE_SIGNUP_REQUEST);
									return Unit.INSTANCE;
								},
								KeystoreCompat.context);
					}
				});

	}


	@Override
	public void onViewModelDestroyed() {
		super.onViewModelDestroyed();
	}


	@Bindable
	public boolean isStorageTypeAvailable() {
		return !storageType.get().isEmpty();
	}


	@Bindable
	public boolean isStorageStateAvailable() {
		return !storageState.get().isEmpty();
	}


}

package cz.koto.misak.dbshowcase.android.mobile.ui.control;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableLong;

import cz.koto.misak.dbshowcase.android.mobile.databinding.FragmentControlRootBinding;
import cz.koto.misak.dbshowcase.android.mobile.model.ModelProvider;
import cz.koto.misak.dbshowcase.android.mobile.ui.base.BaseViewModel;
import cz.koto.misak.keystorecompat.KeystoreCompat;
import cz.koto.misak.keystorecompat.KeystoreHashKt;
import cz.koto.misak.keystorecompat.utility.AndroidVersionUtilityKt;
import cz.koto.misak.keystorecompat.utility.IntentUtilityKt;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import kotlin.Unit;
import timber.log.Timber;


public class ControlRootViewModel extends BaseViewModel<FragmentControlRootBinding> {

	public final ObservableLong dbSize = new ObservableLong();

	public final ObservableBoolean androidSecurityAvailable = new ObservableBoolean(false);
	public final ObservableBoolean androidSecuritySelectable = new ObservableBoolean(false);


	@Override
	public void onViewAttached(boolean firstAttachment) {
		super.onViewAttached(firstAttachment);
		updateToolbar();
		dbSize.set(ModelProvider.get().getDbSizeInBytes());


		setVisibility();

		AndroidVersionUtilityKt.runSinceKitKat(() -> {
			getBinding().settingsAndroidSecuritySwitch.setChecked(KeystoreCompat.INSTANCE.hasSecretLoadable());
			getBinding().settingsAndroidSecuritySwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
				if(isChecked) {
					getBinding().settingsAndroidSecuritySwitch.setEnabled(false);

					Flowable.fromCallable(() -> {
						byte[] secretKey = KeystoreHashKt.createHashKey("ThisIsMyVeryScreetPassword", false);
						KeystoreCompat.INSTANCE.storeSecret(secretKey,
								() -> {
									Timber.e("Store credentials failed!");
									return Unit.INSTANCE;
								});
						return Unit.INSTANCE;
					}).subscribeOn(Schedulers.io())
							.observeOn(AndroidSchedulers.mainThread())
							.subscribe(unit -> {
							}, unit -> {
								getBinding().settingsAndroidSecuritySwitch.setEnabled(true);
								getBinding().settingsAndroidSecuritySwitch.setChecked(false);
							}, () -> {
								getBinding().settingsAndroidSecuritySwitch.setEnabled(true);
								getBinding().settingsAndroidSecuritySwitch.setChecked(true);
							});

				} else {
					KeystoreCompat.INSTANCE.clearCredentials();
				}
			});
			return Unit.INSTANCE;
		});
	}


	@Override
	public void onViewModelCreated() {
		super.onViewModelCreated();
	}


	@Override
	public void onResume() {
		super.onResume();
		updateToolbar();
		dbSize.set(ModelProvider.get().getDbSizeInBytes());
	}


	public void deleteModel() {
		if(ModelProvider.get().deleteModel())
			getNavigationManager().getControlNavigationManager().switchToRoot();
	}


	public void onClickSecuritySettings() {
		IntentUtilityKt.showLockScreenSettings(getContext());
	}


	private void setVisibility() {
		AndroidVersionUtilityKt.runSinceKitKat(() -> {
			androidSecurityAvailable.set(KeystoreCompat.INSTANCE.isKeystoreCompatAvailable());
			androidSecuritySelectable.set(KeystoreCompat.INSTANCE.isSecurityEnabled());
			return Unit.INSTANCE;
		});
	}

}

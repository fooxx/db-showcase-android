package cz.koto.misak.dbshowcase.android.mobile.ui.control;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableLong;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.Toast;

import cz.koto.misak.dbshowcase.android.mobile.R;
import cz.koto.misak.dbshowcase.android.mobile.databinding.DialogPasswordBinding;
import cz.koto.misak.dbshowcase.android.mobile.databinding.FragmentControlRootBinding;
import cz.koto.misak.dbshowcase.android.mobile.model.ModelProvider;
import cz.koto.misak.dbshowcase.android.mobile.ui.base.BaseViewModel;
import cz.koto.misak.dbshowcase.android.mobile.ui.dialog.PasswordDialogListener;
import cz.koto.misak.dbshowcase.android.mobile.ui.dialog.PasswordDialogViewModel;
import cz.koto.misak.dbshowcase.android.mobile.utility.ByteUtility;
import cz.koto.misak.keystorecompat.KeystoreCompat;
import cz.koto.misak.keystorecompat.KeystoreHashKt;
import cz.koto.misak.keystorecompat.utility.AndroidVersionUtilityKt;
import cz.koto.misak.keystorecompat.utility.IntentUtilityKt;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import kotlin.Unit;
import timber.log.Timber;


public class ControlRootViewModel extends BaseViewModel<FragmentControlRootBinding> implements PasswordDialogListener {

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
					AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
					DialogPasswordBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.dialog_password, null, false);
					dialogBuilder.setView(binding.getRoot());
					AlertDialog dialog = dialogBuilder.create();
					binding.setViewModel(new PasswordDialogViewModel(dialog, this));
					dialog.show();
				} else {
					if(ModelProvider.get().isPersistenceEncrypted())
						Toast.makeText(getContext(), R.string.settings_security_migration_to_open_not_implemented, Toast.LENGTH_SHORT).show();

					getBinding().settingsAndroidSecuritySwitch.setChecked(KeystoreCompat.INSTANCE.hasSecretLoadable());
					//ModelProvider.get().setPersistenceEncrypted(false);
					//KeystoreCompat.INSTANCE.clearCredentials();
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


	@Override
	public void onSubmitPassword(String password) {
		Flowable.fromCallable(() -> {
			byte[] secretKey32 = KeystoreHashKt.createHashKey(/*"ThisIsMyVeryScreetPasswordXXXX"*/password, false,
					KeystoreHashKt.getLENGTH32BYTES());

			Timber.d("Generated secretKeySmall Length:%s", secretKey32.length);
			KeystoreCompat.INSTANCE.storeSecret(secretKey32,
					() -> {
						Timber.e("Store credentials failed!");
						ModelProvider.get().setPersistenceEncrypted(false);
						return Unit.INSTANCE;
					}, () -> {
						byte[] secretKey64 = ByteUtility.doubleSizeBytes(secretKey32);
						ModelProvider.get().encryptDb(secretKey64);

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
	}


	@Override
	public void onCancelPassword() {
		getBinding().settingsAndroidSecuritySwitch.setEnabled(true);
		getBinding().settingsAndroidSecuritySwitch.setChecked(false);
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

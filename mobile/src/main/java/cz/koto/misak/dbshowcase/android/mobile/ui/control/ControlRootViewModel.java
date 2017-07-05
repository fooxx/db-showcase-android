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
import cz.koto.misak.dbshowcase.android.mobile.ui.MainActivity;
import cz.koto.misak.dbshowcase.android.mobile.ui.base.BaseViewModel;
import cz.koto.misak.dbshowcase.android.mobile.ui.dialog.PasswordDialogListener;
import cz.koto.misak.dbshowcase.android.mobile.ui.dialog.PasswordDialogViewModel;
import cz.koto.misak.dbshowcase.android.mobile.utility.ByteUtility;
import cz.koto.misak.dbshowcase.android.mobile.utility.ContextProvider;
import cz.koto.misak.keystorecompat.KeystoreCompat;
import cz.koto.misak.keystorecompat.KeystoreHashKt;
import cz.koto.misak.keystorecompat.exception.ForceLockScreenMarshmallowException;
import cz.koto.misak.keystorecompat.utility.AndroidVersionUtilityKt;
import cz.koto.misak.keystorecompat.utility.IntentUtilityKt;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import kotlin.Unit;
import timber.log.Timber;


public class ControlRootViewModel extends BaseViewModel<FragmentControlRootBinding> implements PasswordDialogListener {

	public static final String EXTRA_ENCRYPTION_REQUEST_SCHEDULED = "EXTRA_ENCRYPTION_REQUEST_SCHEDULED";
	public final ObservableLong dbSize = new ObservableLong();
	public final ObservableBoolean androidSecurityAvailable = new ObservableBoolean(false);
	public final ObservableBoolean androidSecuritySelectable = new ObservableBoolean(false);
	private boolean userEventSwitchSecurity = true;


	@Override
	public void onViewAttached(boolean firstAttachment) {
		super.onViewAttached(firstAttachment);
		//TODO updateToolbar();
		dbSize.set(ModelProvider.get().getDbSizeInBytes());


		setVisibility();
		AndroidVersionUtilityKt.runSinceKitKat(() -> {

			androidSecuritySelectable.set(KeystoreCompat.INSTANCE.hasSecretLoadable());


			return Unit.INSTANCE;
		});
	}


	@Override
	public void onViewModelCreated() {
		super.onViewModelCreated();
		if(getView().getBundle().getBoolean(EXTRA_ENCRYPTION_REQUEST_SCHEDULED)) {
			requestEncryption();
		}
	}


	@Override
	public void onResume() {
		super.onResume();
		//TODO updateToolbar();
		dbSize.set(ModelProvider.get().getDbSizeInBytes());
		String password = ModelProvider.get().getTemporaryPassword();
		if(password != null) {
			onSubmitPassword(password);

		}
	}


	@Override
	public void onSubmitPassword(String password) {
		Flowable.fromCallable(() -> {
			byte[] secretKey32 = KeystoreHashKt.createHashKey(/*"ThisIsMyVeryScreetPasswordXXXX"*/password, false,
					KeystoreHashKt.getLENGTH32BYTES());
			Timber.d("Generated secretKeySmall Length:%s", secretKey32.length);
			KeystoreCompat.INSTANCE.storeSecret(secretKey32,
					(exception) -> {
						ModelProvider.get().setTemporaryPassword(password);
						Timber.e("Store credentials failed!", exception);
						if(exception instanceof ForceLockScreenMarshmallowException) {
							IntentUtilityKt.forceAndroidAuth(getString(R.string.kc_lock_screen_title), getString(R.string.kc_lock_screen_description),
									intent -> {
										getActivity().startActivityForResult(intent, MainActivity.FORCE_ENCRYPTION_REQUEST_M);
										return Unit.INSTANCE;
									},
									KeystoreCompat.context);
						}

						return Unit.INSTANCE;
					}, () -> {
						byte[] secretKey64 = ByteUtility.doubleSizeBytes(secretKey32);
						ModelProvider.get().encryptDb(secretKey64);
						ModelProvider.get().setTemporaryPassword(null);
						return Unit.INSTANCE;
					});
			return Unit.INSTANCE;
		}).subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(unit -> {
				}, unit -> {
					ModelProvider.get().setTemporaryPassword(null);
					showSnackBar(ContextProvider.getString(R.string.settings_security_store_failed));
					//TODO getBinding().settingsAndroidSecuritySwitch.setEnabled(true);
					unCheckSwitchSecurityProgrammatically();
				}, () -> {
					if(ModelProvider.get().getTemporaryPassword() == null) {
						//TODO getBinding().settingsAndroidSecuritySwitch.setEnabled(true);
						checkSwitchSecurityProgrammatically();
					} else {
						//TODO getBinding().settingsAndroidSecuritySwitch.setEnabled(true);
						unCheckSwitchSecurityProgrammatically();
					}
				});
	}


	@Override
	public void onCancelPassword() {
		//TODO getBinding().settingsAndroidSecuritySwitch.setEnabled(true);
		//getBinding().settingsAndroidSecuritySwitch.setChecked(false);
		androidSecuritySelectable.set(false);
	}


	public void onSecuritySelected() {
		if(androidSecuritySelectable.get()) {
			if(userEventSwitchSecurity) {
				requestEncryption();
			}
		} else {
			if(ModelProvider.get().isPersistenceEncrypted())
				Toast.makeText(getContext(), R.string.settings_security_migration_to_open_not_implemented, Toast.LENGTH_SHORT).show();

			//getBinding().settingsAndroidSecuritySwitch.setChecked(KeystoreCompat.INSTANCE.hasSecretLoadable());
		}
		userEventSwitchSecurity = true;
	}


	public void deleteModel() {
		if(ModelProvider.get().deleteModel())
			getNavigationManager().getControlNavigationManager().switchToRoot();
	}


	public void onClickSecuritySettings() {
		IntentUtilityKt.showLockScreenSettings(getContext());
	}


	private void checkSwitchSecurityProgrammatically() {
		userEventSwitchSecurity = false;
//		getBinding().settingsAndroidSecuritySwitch.setChecked(true);
		androidSecuritySelectable.set(true);
	}


	private void unCheckSwitchSecurityProgrammatically() {
		userEventSwitchSecurity = false;
		//getBinding().settingsAndroidSecuritySwitch.setChecked(false);
		androidSecuritySelectable.set(false);
	}


	private void requestEncryption() {
		//TODO getBinding().settingsAndroidSecuritySwitch.setEnabled(false);

		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
		DialogPasswordBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.dialog_password, null, false);
		dialogBuilder.setView(binding.getRoot());
		AlertDialog dialog = dialogBuilder.create();
		binding.setViewModel(new PasswordDialogViewModel(dialog, this));
		dialog.show();
	}


	private void setVisibility() {
		AndroidVersionUtilityKt.runSinceKitKat(() -> {
			androidSecurityAvailable.set(KeystoreCompat.INSTANCE.isKeystoreCompatAvailable());
			androidSecuritySelectable.set(KeystoreCompat.INSTANCE.isSecurityEnabled());
			return Unit.INSTANCE;
		});
	}
}

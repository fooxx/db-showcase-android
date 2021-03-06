package cz.koto.misak.dbshowcase.android.mobile.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import cz.koto.keystorecompat.KeystoreCompat;
import cz.koto.misak.dbshowcase.android.mobile.R;
import cz.koto.misak.dbshowcase.android.mobile.databinding.ActivityMainBinding;
import cz.koto.misak.dbshowcase.android.mobile.model.ModelProvider;
import cz.koto.misak.dbshowcase.android.mobile.persistence.PersistenceSyncState;
import cz.koto.misak.dbshowcase.android.mobile.persistence.PersistenceType;
import cz.koto.misak.dbshowcase.android.mobile.ui.base.BaseActivity;
import cz.koto.misak.dbshowcase.android.mobile.ui.navigation.NavigationManager;
import cz.koto.misak.dbshowcase.android.mobile.ui.navigation.NavigationProvider;
import cz.koto.misak.dbshowcase.android.mobile.utility.ApplicationEvent;
import cz.koto.misak.dbshowcase.android.mobile.utility.ContextProvider;
import cz.koto.misak.dbshowcase.android.mobile.utility.EventsUtilityKt;


public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements NavigationProvider {

	public static final int FORCE_ENCRYPTION_REQUEST_M = 1112;
	private NavigationManager mNavigationManager = new NavigationManager(this, R.id.content) {
		@Override
		public void selectNavigationItem(int itemId) {
			getBinding().navigation.getMenu().getItem(itemId).setChecked(true);

		}
	};



	@Override
	public NavigationManager getNavigationManager() {
		return mNavigationManager;
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setupViewModel(R.layout.activity_main, MainViewModel.class);
		super.onCreate(savedInstanceState);

		mNavigationManager.restore(savedInstanceState);

		EventsUtilityKt.getApplicationEvents().subscribe(applicationEvent -> {
			if(ApplicationEvent.StateUpdateRequest.INSTANCE.equals(applicationEvent)) {
				updateToolbar();
			}
		});

		updateToolbar();

		if(savedInstanceState == null)
			getNavigationManager().getInteractionNavigationManager().switchToRoot();

		getBinding().navigation.setOnNavigationItemSelectedListener(item -> {
			switch(item.getItemId()) {
				case R.id.menu_interaction:
					getNavigationManager().getInteractionNavigationManager().switchToRoot();
					return true;
				case R.id.menu_control:
					getNavigationManager().getControlNavigationManager().switchToRoot();
					return true;
				case R.id.menu_gihub:
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/kotomisak/db-showcase-android"));
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					ContextProvider.getContext().startActivity(intent);
					return false;
//				case R.id.menu_settings:
//					getNavigationManager().getSettingsNavigationManager().switchToRoot();
//					break;
				case R.id.menu_logout:
					finishAffinity();
					System.exit(0);
					return false;
				default:
					return false;
			}
		});

	}


	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		if(isFinishing()) {
			mNavigationManager.cleanup();
		}
	}


	@Override
	public void onBackPressed() {
		if(getNavigationManager().getCurrentFragment() != null && getNavigationManager().getCurrentFragment().handlesBackButton())
			getNavigationManager().getCurrentFragment().onBackButtonPressed();
		else
			super.onBackPressed();
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == getViewModel().FORCE_SIGNUP_REQUEST) {
			if(resultCode == Activity.RESULT_CANCELED) {
				KeystoreCompat.INSTANCE.increaseLockScreenCancel();
				this.finish();
			} else {
				getViewModel().onViewAttached(false);
			}
		} else if(requestCode == FORCE_ENCRYPTION_REQUEST_M) {
			if(resultCode == Activity.RESULT_CANCELED) {
				ModelProvider.get().setTemporaryPassword(null);
			}
		} else
			super.onActivityResult(requestCode, resultCode, data);


	}


	public void updateToolbar() {
		setSupportActionBar(getBinding().toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setTitle("");

		PersistenceType activePersistenceType = ModelProvider.get().getActivePersistenceType();
		PersistenceSyncState activePersistenceSyncState = ModelProvider.get().getActivePersistenceSyncState();
		getViewModel().storageType.set(activePersistenceType == null ? "-" : ContextProvider.getString(activePersistenceType.getStringRes()));
		getViewModel().storageState.set(activePersistenceSyncState == null ? null : ContextProvider.getString(activePersistenceSyncState.getDescRes()));
		getViewModel().storageTypeIcon.set(activePersistenceType == null ? null : activePersistenceType.getIconRes());
		getViewModel().storageStateIcon.set(activePersistenceSyncState == null ? null : activePersistenceSyncState.getIconRes());
	}

}
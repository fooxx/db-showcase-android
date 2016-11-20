package cz.koto.misak.dbshowcase.android.mobile.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import cz.kinst.jakub.viewmodelbinding.ViewModelBindingConfig;
import cz.koto.misak.dbshowcase.android.mobile.R;
import cz.koto.misak.dbshowcase.android.mobile.databinding.ActivityMainBinding;
import cz.koto.misak.dbshowcase.android.mobile.ui.base.BaseActivity;
import cz.koto.misak.dbshowcase.android.mobile.ui.navigation.NavigationManager;
import cz.koto.misak.dbshowcase.android.mobile.ui.navigation.NavigationProvider;
import cz.koto.misak.dbshowcase.android.mobile.utility.ContextProvider;


public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements NavigationProvider {

	private NavigationManager mNavigationManager = new NavigationManager(this, R.id.content);


	@Override
	public ViewModelBindingConfig<MainViewModel> getViewModelBindingConfig() {
		return new ViewModelBindingConfig<>(R.layout.activity_main, MainViewModel.class);
	}


	@Override
	public NavigationManager getNavigationManager() {
		return mNavigationManager;
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mNavigationManager.restore(savedInstanceState);

		if(savedInstanceState == null)
			getNavigationManager().getInteractionNavigationManager().switchToRoot();

		getBinding().navigation.setOnNavigationItemSelectedListener(item -> {
			switch(item.getItemId()) {
				default:
				case R.id.menu_interaction:
					getNavigationManager().getInteractionNavigationManager().switchToRoot();
					break;
				case R.id.menu_gauge:
					break;
				case R.id.menu_gihub:
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/kotomisak/db-showcase-android"));
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					ContextProvider.getContext().startActivity(intent);
					break;
				case R.id.menu_settings:
					getNavigationManager().getSettingsNavigationManager().switchToRoot();
					break;
			}
			return false;
		});
	}


	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mNavigationManager.onSaveInstanceState(outState);
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

}
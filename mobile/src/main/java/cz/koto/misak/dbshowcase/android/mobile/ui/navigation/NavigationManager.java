package cz.koto.misak.dbshowcase.android.mobile.ui.navigation;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import cz.koto.misak.dbshowcase.android.mobile.R;
import cz.koto.misak.dbshowcase.android.mobile.ui.MainActivity2;
import cz.koto.misak.dbshowcase.android.mobile.ui.base.BaseFragment;
import cz.koto.misak.dbshowcase.android.mobile.ui.base.RootFragment;
import timber.log.Timber;


public class NavigationManager implements FragmentManager.OnBackStackChangedListener {

	private static final String INSTANCE_KEY_TITLE = "title";
	private static final String INSTANCE_BACK_ARROW_VISIBLE = "back_arrow_visible";
	private static final String INSTANCE_TOOLBAR_VISIBLE = "toolbar_visible";

	private static final String FRAGMENT_TAG = "main";
	private final int mFragmentContainerId;
	private final SettingsNavigationManager mSettingsNavigationManager = new SettingsNavigationManager(this);
	private final InteractionNavigationManager mInteractionNavigationManager = new InteractionNavigationManager(this);
	private MainActivity2 mActivity;
	private BaseFragment mCurrentFragment;
	private String mTitle;
	private boolean mBackButtonVisible;
	private boolean mToolbarVisible;
	private boolean mSkipOneBackStackChange;
	private Toolbar mToolbar;


	public NavigationManager(MainActivity2 activity, @IdRes int fragmentConatinerId) {
		mActivity = activity;
		mActivity.getSupportFragmentManager().addOnBackStackChangedListener(this);
		mFragmentContainerId = fragmentConatinerId;
	}


	@Override
	public void onBackStackChanged() {
		if(!mSkipOneBackStackChange) {
			mCurrentFragment = (BaseFragment) mActivity.getSupportFragmentManager().findFragmentById(mFragmentContainerId);
			onCurrentFragmentChanged();
		} else
			mSkipOneBackStackChange = false;
	}


	public void restore(Bundle savedInstanceState) {
		mCurrentFragment = (BaseFragment) mActivity.getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
		if(savedInstanceState != null) {
			mTitle = savedInstanceState.getString(INSTANCE_KEY_TITLE);
			mToolbarVisible = savedInstanceState.getBoolean(INSTANCE_TOOLBAR_VISIBLE);
			mBackButtonVisible = savedInstanceState.getBoolean(INSTANCE_BACK_ARROW_VISIBLE);
			updateToolbar();
		}
		onCurrentFragmentChanged();
	}


	public void onSaveInstanceState(Bundle outState) {
		outState.putString(INSTANCE_KEY_TITLE, mTitle);
		outState.putBoolean(INSTANCE_BACK_ARROW_VISIBLE, mBackButtonVisible);
		outState.putBoolean(INSTANCE_TOOLBAR_VISIBLE, mToolbarVisible);
	}


	public void updateToolbar() {
		if(mActivity != null) {
			mActivity.setSupportActionBar(mToolbar);
			if(mActivity.getSupportActionBar() != null) {
				((TextView) mToolbar.findViewById(R.id.title)).setText(mTitle);
				mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(mBackButtonVisible);
				mActivity.getSupportActionBar().setTitle("");
			}
		}
	}


	public SettingsNavigationManager getSettingsNavigationManager() {
		return mSettingsNavigationManager;
	}


	public InteractionNavigationManager getInteractionNavigationManager() {
		return mInteractionNavigationManager;
	}


	public void setProgress(boolean progress) {
		if(mActivity != null && mActivity.getViewModel() != null) {
			mActivity.getViewModel().progress.set(progress);
		}
	}


	public MainActivity2 getActivity() {
		return mActivity;
	}


	public BaseFragment getCurrentFragment() {
		return mCurrentFragment;
	}


	public void clearBackStack() {
		try {
			mActivity.getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		} catch(IllegalStateException ignored) {
			ignored.printStackTrace();
		}
	}


	public void configureToolbar(Toolbar toolbar, String title, boolean backButtonEnabled) {
		mToolbar = toolbar;
		mTitle = title;
		mBackButtonVisible = backButtonEnabled;
		updateToolbar();
	}


	public void cleanup() {
		mActivity = null;
	}


	void switchToRootFragment(BaseFragment fragment) {
		resetToolbar();
		String fragmentNameToSwitch = fragment.getClass().getName();
		Timber.i("Switching to %s", fragmentNameToSwitch);
		FragmentTransaction transaction = mActivity.getSupportFragmentManager().beginTransaction();

		clearBackStack();

		BaseFragment existingFragment = (BaseFragment) mActivity.getSupportFragmentManager().findFragmentByTag(fragmentNameToSwitch);

		if(mCurrentFragment != null) {
			if(!(mCurrentFragment instanceof RootFragment))
				transaction.remove(mCurrentFragment);
			else {
				transaction.detach(mCurrentFragment);
			}
		}

		if(existingFragment != null) {
			transaction.attach(existingFragment);
			mCurrentFragment = existingFragment;
		} else {
			transaction.add(mFragmentContainerId, fragment, fragmentNameToSwitch);
			mCurrentFragment = fragment;
		}


		transaction.commitAllowingStateLoss();
		onCurrentFragmentChanged();
		mSkipOneBackStackChange = true;
	}


	void switchToFragment(BaseFragment fragment, boolean addToBackStack) {
		if(fragment instanceof RootFragment) {
			switchToRootFragment(fragment);
			return;
		}

		if(mCurrentFragment != null && fragment.getClass().getName().equals(FRAGMENT_TAG))
			return;

		String fragmentNameToSwitch = fragment.getClass().getName();
		Timber.i("Switching to %s", fragmentNameToSwitch);
		FragmentTransaction transaction = mActivity.getSupportFragmentManager().beginTransaction();

		if(mCurrentFragment != null) {
			if(!(mCurrentFragment instanceof RootFragment))
				transaction.remove(mCurrentFragment);
			else
				transaction.detach(mCurrentFragment);
		}
		transaction.add(mFragmentContainerId, fragment, FRAGMENT_TAG);
		if(addToBackStack)
			transaction.addToBackStack(fragmentNameToSwitch);

		transaction.commitAllowingStateLoss();
		mCurrentFragment = fragment;
		onCurrentFragmentChanged();

		mSkipOneBackStackChange = true;
	}


	private void onCurrentFragmentChanged() {}


	private void resetToolbar() {
		mTitle = null;
		mBackButtonVisible = false;
		mToolbarVisible = true;
		updateToolbar();
	}

}

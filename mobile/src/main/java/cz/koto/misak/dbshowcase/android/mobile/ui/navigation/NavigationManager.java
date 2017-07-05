package cz.koto.misak.dbshowcase.android.mobile.ui.navigation;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import cz.koto.misak.dbshowcase.android.mobile.ui.MainActivity;
import cz.koto.misak.dbshowcase.android.mobile.ui.base.BaseFragment;
import cz.koto.misak.dbshowcase.android.mobile.ui.base.RootFragment;
import timber.log.Timber;


public class NavigationManager implements FragmentManager.OnBackStackChangedListener {

	private static final String FRAGMENT_TAG = "main";
	private final int mFragmentContainerId;
	private final SettingsNavigationManager mSettingsNavigationManager = new SettingsNavigationManager(this);
	private final InteractionNavigationManager mInteractionNavigationManager = new InteractionNavigationManager(this);
	private final ControlNavigationManager mControlNavigationManager = new ControlNavigationManager(this);
	private MainActivity mActivity;
	private BaseFragment mCurrentFragment;
	private boolean mSkipOneBackStackChange;


	public NavigationManager(MainActivity activity, @IdRes int fragmentConatinerId) {
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
		onCurrentFragmentChanged();
	}

	public SettingsNavigationManager getSettingsNavigationManager() {
		return mSettingsNavigationManager;
	}


	public InteractionNavigationManager getInteractionNavigationManager() {
		return mInteractionNavigationManager;
	}


	public ControlNavigationManager getControlNavigationManager() {
		return mControlNavigationManager;
	}


	public void setProgress(boolean progress) {
		if(mActivity != null && mActivity.getViewModel() != null) {
			mActivity.getViewModel().progress.set(progress);
		}
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




	public void cleanup() {
		mActivity = null;
	}


	void switchToRootFragment(BaseFragment fragment) {
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


}

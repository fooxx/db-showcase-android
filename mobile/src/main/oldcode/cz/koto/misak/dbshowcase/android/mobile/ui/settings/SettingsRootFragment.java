package cz.koto.misak.dbshowcase.android.mobile.ui.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;

import cz.koto.misak.dbshowcase.android.mobile.R;
import cz.koto.misak.dbshowcase.android.mobile.databinding.FragmentSettingsRootBinding;
import cz.koto.misak.dbshowcase.android.mobile.ui.base.BaseFragment;
import cz.koto.misak.dbshowcase.android.mobile.ui.base.RootFragment;


public class SettingsRootFragment extends BaseFragment<FragmentSettingsRootBinding, SettingsRootViewModel> implements RootFragment {

	public static SettingsRootFragment newInstance() {
		SettingsRootFragment f = new SettingsRootFragment();
		Bundle args = new Bundle();
		f.setArguments(args);
		return f;
	}


	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		setupViewModel(R.layout.fragment_settings_root, SettingsRootViewModel.class);
		super.onCreate(savedInstanceState);
	}


//	@Override
//	public ViewModelBindingConfig<SettingsRootViewModel> getViewModelBindingConfig() {
//		return new ViewModelBindingConfig<>(R.layout.fragment_settings_root, SettingsRootViewModel.class);
//	}
}

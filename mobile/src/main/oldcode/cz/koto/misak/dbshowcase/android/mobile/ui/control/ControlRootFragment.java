package cz.koto.misak.dbshowcase.android.mobile.ui.control;

import android.os.Bundle;
import android.support.annotation.Nullable;

import cz.koto.misak.dbshowcase.android.mobile.R;
import cz.koto.misak.dbshowcase.android.mobile.databinding.FragmentControlRootBinding;
import cz.koto.misak.dbshowcase.android.mobile.ui.base.BaseFragment;
import cz.koto.misak.dbshowcase.android.mobile.ui.base.RootFragment;


public class ControlRootFragment extends BaseFragment<FragmentControlRootBinding, ControlRootViewModel> implements RootFragment {

	public static ControlRootFragment newInstance() {
		return ControlRootFragment.newInstance(false);
	}


	public static ControlRootFragment newInstance(Boolean encryptionRequested) {
		ControlRootFragment f = new ControlRootFragment();
		Bundle args = new Bundle();
		args.putBoolean(ControlRootViewModel.EXTRA_ENCRYPTION_REQUEST_SCHEDULED, encryptionRequested);
		f.setArguments(args);
		return f;
	}


	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		setupViewModel(R.layout.fragment_control_root, ControlRootViewModel.class);
		super.onCreate(savedInstanceState);
	}


//	@Override
//	public ViewModelBindingConfig<ControlRootViewModel> getViewModelBindingConfig() {
//		return new ViewModelBindingConfig<>(R.layout.fragment_control_root, ControlRootViewModel.class);
//	}
}

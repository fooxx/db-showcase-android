package cz.koto.misak.dbshowcase.android.mobile.ui.control;

import android.os.Bundle;

import cz.kinst.jakub.viewmodelbinding.ViewModelBindingConfig;
import cz.koto.misak.dbshowcase.android.mobile.R;
import cz.koto.misak.dbshowcase.android.mobile.databinding.FragmentControlRootBinding;
import cz.koto.misak.dbshowcase.android.mobile.ui.base.BaseFragment;
import cz.koto.misak.dbshowcase.android.mobile.ui.base.RootFragment;


public class ControlRootFragment extends BaseFragment<FragmentControlRootBinding, ControlRootViewModel> implements RootFragment {

	public static ControlRootFragment newInstance() {
		ControlRootFragment f = new ControlRootFragment();
		Bundle args = new Bundle();
		f.setArguments(args);
		return f;
	}


	@Override
	public ViewModelBindingConfig<ControlRootViewModel> getViewModelBindingConfig() {
		return new ViewModelBindingConfig<>(R.layout.fragment_control_root, ControlRootViewModel.class);
	}
}

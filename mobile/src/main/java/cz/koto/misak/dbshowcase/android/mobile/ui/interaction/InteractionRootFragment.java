package cz.koto.misak.dbshowcase.android.mobile.ui.interaction;

import android.os.Bundle;
import android.support.annotation.Nullable;

import cz.koto.misak.dbshowcase.android.mobile.R;
import cz.koto.misak.dbshowcase.android.mobile.databinding.FragmentInteractionRootBinding;
import cz.koto.misak.dbshowcase.android.mobile.ui.base.BaseFragment;
import cz.koto.misak.dbshowcase.android.mobile.ui.base.RootFragment;


public class InteractionRootFragment extends BaseFragment<FragmentInteractionRootBinding, InteractionRootViewModel> implements RootFragment {

	public static InteractionRootFragment newInstance() {
		InteractionRootFragment f = new InteractionRootFragment();
		Bundle args = new Bundle();
		f.setArguments(args);
		return f;
	}


	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		setupViewModel(R.layout.fragment_interaction_root, InteractionRootViewModel.class);
		super.onCreate(savedInstanceState);
	}

}

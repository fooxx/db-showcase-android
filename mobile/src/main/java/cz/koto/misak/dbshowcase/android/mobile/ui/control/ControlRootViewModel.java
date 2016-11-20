package cz.koto.misak.dbshowcase.android.mobile.ui.control;

import android.databinding.ObservableLong;

import cz.koto.misak.dbshowcase.android.mobile.databinding.FragmentControlRootBinding;
import cz.koto.misak.dbshowcase.android.mobile.model.ModelProvider;
import cz.koto.misak.dbshowcase.android.mobile.ui.base.BaseViewModel;


public class ControlRootViewModel extends BaseViewModel<FragmentControlRootBinding> {

	public final ObservableLong dbSize = new ObservableLong();


	@Override
	public void onViewAttached(boolean firstAttachment) {
		super.onViewAttached(firstAttachment);
		updateToolbar();
		dbSize.set(ModelProvider.get().getDbSizeInBytes());
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
}

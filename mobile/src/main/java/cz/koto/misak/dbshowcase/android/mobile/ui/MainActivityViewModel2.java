package cz.koto.misak.dbshowcase.android.mobile.ui;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import cz.kinst.jakub.viewmodelbinding.ViewModel;
import cz.koto.misak.dbshowcase.android.mobile.databinding.ActivityMain2Binding;
import cz.koto.misak.dbshowcase.android.mobile.model.SchoolModel;


public class MainActivityViewModel2 extends ViewModel<ActivityMain2Binding> {


	public final ObservableBoolean progress = new ObservableBoolean(false);
	public final ObservableField<SchoolModel> schoolModel = new ObservableField<>();


	@Override
	public void onViewModelCreated() {
		super.onViewModelCreated();
	}


	@Override
	public void onResume() {
		super.onResume();
	}


	@Override
	public void onViewAttached(boolean firstAttachment) {
		super.onViewAttached(firstAttachment);

	}


	@Override
	public void onViewModelDestroyed() {
		super.onViewModelDestroyed();
	}



}

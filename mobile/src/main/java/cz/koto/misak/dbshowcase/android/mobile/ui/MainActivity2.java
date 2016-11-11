package cz.koto.misak.dbshowcase.android.mobile.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import cz.kinst.jakub.viewmodelbinding.ViewModelBindingConfig;
import cz.koto.misak.dbshowcase.android.mobile.R;
import cz.koto.misak.dbshowcase.android.mobile.databinding.ActivityMain2Binding;
import cz.koto.misak.dbshowcase.android.mobile.ui.base.BaseActivity;


public class MainActivity2 extends BaseActivity<ActivityMain2Binding, MainActivityViewModel2> {

	@Override
	public ViewModelBindingConfig<MainActivityViewModel2> getViewModelBindingConfig() {
		return new ViewModelBindingConfig<>(R.layout.activity_main2, MainActivityViewModel2.class);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getBinding().recycler.setLayoutManager(new LinearLayoutManager(this));
	}
}
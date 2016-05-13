package cz.koto.misak.dbshowcase.android.mobile.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import cz.kinst.jakub.viewmodelbinding.ViewModelActivity;
import cz.kinst.jakub.viewmodelbinding.ViewModelBindingConfig;
import cz.koto.misak.dbshowcase.android.mobile.R;
import cz.koto.misak.dbshowcase.android.mobile.databinding.ActivityMainBinding;
import cz.koto.misak.dbshowcase.android.mobile.viewModel.MainActivityViewModel;


public class  MainActivity extends ViewModelActivity<ActivityMainBinding, MainActivityViewModel>
{

    @Override
    public ViewModelBindingConfig<MainActivityViewModel> getViewModelBindingConfig()
    {
        return new ViewModelBindingConfig<>(R.layout.activity_main, MainActivityViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBinding().recycler.setLayoutManager(new LinearLayoutManager(this));
    }
}
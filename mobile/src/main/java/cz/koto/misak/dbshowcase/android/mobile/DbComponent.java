package cz.koto.misak.dbshowcase.android.mobile;

import javax.inject.Singleton;

import cz.koto.misak.dbshowcase.android.mobile.db.realm.ShowcaseRealmConfigModule;
import cz.koto.misak.dbshowcase.android.mobile.db.realm.ShowcaseRealmConfigurationMarker;
import cz.koto.misak.dbshowcase.android.mobile.db.realm.ShowcaseRealmCrudModule;
import cz.koto.misak.dbshowcase.android.mobile.viewModel.MainActivityViewModel;
import dagger.Component;
import io.realm.RealmConfiguration;


@Singleton
@Component(modules = {
        ShowcaseRealmConfigModule.class,
        ShowcaseRealmCrudModule.class})
public interface DbComponent {

    void inject(MainActivityViewModel mainActivityViewModel);

    // downstream components need these exposed
    @ShowcaseRealmConfigurationMarker
    RealmConfiguration provideRealmConfiguration();

    ShowcaseRealmCrudModule provideShowcaseRealmLoadModule();
}

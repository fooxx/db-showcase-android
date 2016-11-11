package cz.koto.misak.dbshowcase.android.mobile;

import javax.inject.Singleton;

import cz.koto.misak.dbshowcase.android.mobile.persistence.realm.ShowcaseRealmConfigModule;
import cz.koto.misak.dbshowcase.android.mobile.persistence.realm.ShowcaseRealmConfigurationMarker;
import cz.koto.misak.dbshowcase.android.mobile.persistence.realm.ShowcaseRealmCrudModule;
import cz.koto.misak.dbshowcase.android.mobile.ui.MainActivityViewModel2;
import dagger.Component;
import io.realm.RealmConfiguration;


@Singleton
@Component(modules = {
        ShowcaseRealmConfigModule.class,
        ShowcaseRealmCrudModule.class})
public interface DbComponent {

    void inject(MainActivityViewModel2 mainActivityViewModel);

    // downstream components need these exposed
    @ShowcaseRealmConfigurationMarker
    RealmConfiguration provideRealmConfiguration();

    ShowcaseRealmCrudModule provideShowcaseRealmLoadModule();
}

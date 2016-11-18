package cz.koto.misak.dbshowcase.android.mobile;

import javax.inject.Singleton;

import cz.koto.misak.dbshowcase.android.mobile.persistence.realm.ShowcaseRealmConfigModule;
import cz.koto.misak.dbshowcase.android.mobile.persistence.realm.ShowcaseRealmConfigurationMarker;
import cz.koto.misak.dbshowcase.android.mobile.persistence.realm.ShowcaseRealmModule;
import cz.koto.misak.dbshowcase.android.mobile.ui.MainViewModel;
import dagger.Component;
import io.realm.RealmConfiguration;


@Singleton
@Component(modules = {
        ShowcaseRealmConfigModule.class,
        ShowcaseRealmModule.class})
public interface DbComponent {

    void inject(MainViewModel mainActivityViewModel);

    // downstream components need these exposed
    @ShowcaseRealmConfigurationMarker
    RealmConfiguration provideRealmConfiguration();

    ShowcaseRealmModule provideShowcaseRealmLoadModule();
}

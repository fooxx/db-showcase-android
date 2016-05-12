package cz.koto.misak.dbshowcase.android.mobile;

import javax.inject.Singleton;

import cz.koto.misak.dbshowcase.android.mobile.db.realm.ShowcaseRealmConfigModule;
import cz.koto.misak.dbshowcase.android.mobile.db.realm.ShowcaseRealmLoadModule;
import cz.koto.misak.dbshowcase.android.mobile.viewModel.MainActivityViewModel;
import dagger.Component;
import io.realm.RealmConfiguration;


@Singleton
@Component(modules = {
        ShowcaseRealmConfigModule.class,
        ShowcaseRealmLoadModule.class})
public interface DbComponent {

    void inject(MainActivityViewModel mainActivityViewModel);

    // downstream components need these exposed
    RealmConfiguration provideRealmConfiguration();

    ShowcaseRealmLoadModule provideShowcaseRealmLoadModule();
}

package cz.koto.misak.dbshowcase.android.mobile.db.realm;


import javax.inject.Singleton;

import cz.koto.misak.dbshowcase.android.mobile.DbApplication;
import dagger.Module;
import dagger.Provides;
import io.realm.RealmConfiguration;

@Module
public class ShowcaseRealmConfigModule {

    public static final int VERSION = 1;
    public static final String NAME = DbApplication.class.getName().toLowerCase() + ".realm";

    @Provides
    @Singleton
    public RealmConfiguration provideRealmConfiguration() {
        return new RealmConfiguration.Builder(DbApplication.get().getApplicationContext())
                .name(ShowcaseRealmConfigModule.NAME)
                .schemaVersion(ShowcaseRealmConfigModule.VERSION)
                //.modules(new ShowcaseRealmModule())
                .migration(new ShowcaseRealmMigration())
                .build();
    }
}

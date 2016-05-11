package cz.koto.misak.dbshowcase.android.mobile.db.realm;


import cz.koto.misak.dbshowcase.android.mobile.DbApplication;
import io.realm.RealmConfiguration;

public class ShowcaseRealm {

    public static final int VERSION = 1;
    public static final String NAME = DbApplication.class.getName().toLowerCase() + ".realm";

    private final RealmConfiguration mRealmConfiguration;

    private static ShowcaseRealm sInstance = null;

    private ShowcaseRealm() {
        mRealmConfiguration = new RealmConfiguration.Builder(DbApplication.get().getApplicationContext())
                .name(ShowcaseRealm.NAME)
                .schemaVersion(ShowcaseRealm.VERSION)
                .modules(new ShowcaseRealmModule())
                .migration(new ShowcaseRealmMigration())
                .build();
    }

    public static ShowcaseRealm getInstance() {
        if (sInstance == null) {
            sInstance = new ShowcaseRealm();
        }
        return sInstance;
    }

    public RealmConfiguration getmRealmConfiguration() {
        return mRealmConfiguration;
    }
}

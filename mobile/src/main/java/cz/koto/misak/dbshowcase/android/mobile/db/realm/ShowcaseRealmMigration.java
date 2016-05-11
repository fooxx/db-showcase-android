package cz.koto.misak.dbshowcase.android.mobile.db.realm;

import io.realm.DynamicRealm;
import io.realm.RealmSchema;
import timber.log.Timber;


public class ShowcaseRealmMigration implements io.realm.RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

        // DynamicRealm exposes an editable schema
        RealmSchema schema = realm.getSchema();

        Timber.e("Realm needs migration from %s to %s, but has NO implementation yet!", oldVersion, newVersion);
    }
}

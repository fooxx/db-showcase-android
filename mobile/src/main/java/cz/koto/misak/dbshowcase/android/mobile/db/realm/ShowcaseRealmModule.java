package cz.koto.misak.dbshowcase.android.mobile.db.realm;

import io.realm.annotations.RealmModule;

/**
 * Restriction to specific classes for realm.
 */
@RealmModule(classes = {RealmString.class})
public class ShowcaseRealmModule {
}

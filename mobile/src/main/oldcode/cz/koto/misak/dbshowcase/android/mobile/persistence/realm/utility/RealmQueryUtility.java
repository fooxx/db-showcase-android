package cz.koto.misak.dbshowcase.android.mobile.persistence.realm.utility;


import io.realm.Realm;
import io.realm.RealmModel;


public class RealmQueryUtility {

	public static <E extends RealmModel> long getNextFreePrimaryKey(Realm realm, Class<E> clazz) {
		return getNextFreePrimaryKey(realm, clazz, "id");
	}


	public static <E extends RealmModel> long getNextFreePrimaryKey(Realm realm, Class<E> clazz, String keyName) {
		return realm.where(clazz).max(keyName).longValue() + 1;
	}
}

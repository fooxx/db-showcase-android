package cz.koto.misak.dbshowcase.android.mobile.persistence;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import cz.koto.misak.dbshowcase.android.mobile.R;


public enum PersistenceType {
	REALM(R.string.persistence_title_realm, R.drawable.ic_database_black_24dp),
	DB_FLOW(R.string.persistence_title_db_flow, R.drawable.ic_database_black_24dp),
	NONE(R.string.persistence_title_none, R.drawable.ic_warning_black_24dp);


	@StringRes int stringRes;
	@DrawableRes int iconRes;


	PersistenceType(@StringRes int stringRes, @DrawableRes int iconRes) {
		this.stringRes = stringRes;
		this.iconRes = iconRes;
	}


	public static PersistenceType getPersistenceTypeForString(String name) {
		for(PersistenceType persistenceType : PersistenceType.values()) {
			if(persistenceType.name().equals(name)) {
				return persistenceType;
			}
		}
		return null;
	}


	public int getStringRes() {
		return stringRes;
	}


	public int getIconRes() {
		return iconRes;
	}
}

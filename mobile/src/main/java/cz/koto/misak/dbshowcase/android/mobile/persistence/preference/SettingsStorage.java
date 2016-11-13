package cz.koto.misak.dbshowcase.android.mobile.persistence.preference;


import android.content.Context;
import android.content.SharedPreferences;

import cz.koto.misak.dbshowcase.android.mobile.persistence.PersistenceSyncState;
import cz.koto.misak.dbshowcase.android.mobile.persistence.PersistenceType;
import cz.koto.misak.dbshowcase.android.mobile.utility.ContextProvider;


public abstract class SettingsStorage {

	private static final String PREF_NAME = "modelProvider";
	private static final String KEY_PERSISTENCE_TYPE = "persistence_type";
	private static final String KEY_PERSISTENCE_STATE = "persistence_state";
	private final SharedPreferences mPrefs;


	protected SettingsStorage() {
		mPrefs = ContextProvider.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
	}


	protected PersistenceType getActivePersistenceType() {
		return PersistenceType.getPersistenceTypeForString(mPrefs.getString(KEY_PERSISTENCE_TYPE, PersistenceType.REALM.name()));
	}


	protected void setActivePersistenceType(PersistenceType persistenceType) {
		SharedPreferences.Editor editor = mPrefs.edit();
		editor.putString(KEY_PERSISTENCE_TYPE, persistenceType == null ? null : persistenceType.name());
		editor.apply();
	}


	protected PersistenceSyncState getActivePersistenceSyncState() {
		return PersistenceSyncState.getPersistenceSyncStateForString(mPrefs.getString(KEY_PERSISTENCE_STATE, PersistenceSyncState.ENABLED.name()));
	}


	protected void setActivePersistenceSyncState(PersistenceSyncState persistenceSyncState) {
		SharedPreferences.Editor editor = mPrefs.edit();
		editor.putString(KEY_PERSISTENCE_STATE, persistenceSyncState == null ? null : persistenceSyncState.name());
		editor.apply();
	}
}

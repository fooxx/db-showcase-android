package cz.koto.misak.dbshowcase.android.mobile.persistence;


import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import cz.koto.misak.dbshowcase.android.mobile.R;


public enum PersistenceSyncState {
	DISABLED(R.string.persistence_state_desc_off, R.drawable.ic_sync_disabled_black_24dp),
	ENABLED(R.string.persistence_state_desc_on, R.drawable.ic_sync_black_24dp),
	ERROR(R.string.persistence_state_desc_error, R.drawable.ic_sync_problem_black_24dp);


	@DrawableRes
	private int iconRes;

	@StringRes
	private int descRes;


	PersistenceSyncState(@StringRes int descRes, @DrawableRes int iconRes) {
		this.descRes = descRes;
		this.iconRes = iconRes;
	}


	public static PersistenceSyncState getPersistenceSyncStateForString(String name) {
		for(PersistenceSyncState persistenceState : PersistenceSyncState.values()) {
			if(persistenceState.name().equals(name)) {
				return persistenceState;
			}
		}
		return null;
	}


	public int getIconRes() {
		return iconRes;
	}


	public int getDescRes() {
		return descRes;
	}
}

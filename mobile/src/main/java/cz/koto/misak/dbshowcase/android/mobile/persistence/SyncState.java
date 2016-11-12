package cz.koto.misak.dbshowcase.android.mobile.persistence;


import android.support.annotation.DrawableRes;

import cz.koto.misak.dbshowcase.android.mobile.R;


public enum SyncState {
	DISABLED(R.drawable.ic_sync_disabled_black_24dp),
	ENABLED(R.drawable.ic_sync_black_24dp),
	ERROR(R.drawable.ic_sync_problem_black_24dp);


	private
	@DrawableRes int iconRes;


	SyncState(@DrawableRes int iconRes) {
		this.iconRes = iconRes;
	}


	public int getIconRes() {
		return iconRes;
	}
}

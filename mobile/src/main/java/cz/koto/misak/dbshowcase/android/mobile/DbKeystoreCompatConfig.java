package cz.koto.misak.dbshowcase.android.mobile;

import cz.koto.misak.keystorecompat.KeystoreCompatConfig;


public class DbKeystoreCompatConfig extends KeystoreCompatConfig {

	@Override
	public int getDialogDismissThreshold() {
		return Integer.MAX_VALUE;
	}


	@Override
	public boolean isRootDetectionEnabled() {
		if(BuildConfig.DEBUG) {
			return false;
		} else
			return super.isRootDetectionEnabled();
	}
}

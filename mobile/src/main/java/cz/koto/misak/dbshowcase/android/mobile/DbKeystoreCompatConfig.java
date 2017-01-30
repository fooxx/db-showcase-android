package cz.koto.misak.dbshowcase.android.mobile;

import cz.koto.misak.keystorecompat.KeystoreCompatConfig;


public class DbKeystoreCompatConfig extends KeystoreCompatConfig {

	@Override
	public int getDialogDismissThreshold() {
		return Integer.MAX_VALUE;
	}
}

package cz.koto.misak.dbshowcase.android.mobile;


import cz.koto.misak.dbshowcase.android.mobile.db.DbShowcase;

/**
 * DbConfig is base config for application.
 */
public class DbConfig {
	public static final String API_DB_PROTOCOL = "http";

	public static final String API_DB_ENDPOINT;

	static {
		API_DB_ENDPOINT = API_DB_PROTOCOL + "://kotopeky.cz/api/db-showcase/";
	}

	/*
	 * Enable/disable non-standard (debug&lower) logging.
	 */
	public static final boolean LOGS = BuildConfig.LOGS;


	public static DbShowcase DB_SHOWCASE = DbShowcase.parse(BuildConfig.DB_SHOWCASE);

}
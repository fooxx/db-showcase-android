package cz.koto.misak.dbshowcase.android.mobile;


/**
 * KoTiPointConfigBase is base config for application.
 */
public class DbConfig
{
	public static final String API_DB_PROTOCOL = "http";

	public static final String API_DB_ENDPOINT;

	static {
		API_DB_ENDPOINT = API_DB_PROTOCOL + "://kotopeky.cz/api/db-showcase/";
	}

	/*
	 * Enable/disable non-standard (debug&lower) logging.
	 */
	public static final boolean LOGS = BuildConfig.LOGS;

	/**
	 * Enable/disable development support.
	 *
	 * Enable development logging.
	 * Disable crashlytics reporting.
	 */
	public static final boolean DEV_API = BuildConfig.DEV_API;


	public static final String VERSION_NAME = BuildConfig.VERSION_NAME;
	public static final String BASE_URL = "";
}

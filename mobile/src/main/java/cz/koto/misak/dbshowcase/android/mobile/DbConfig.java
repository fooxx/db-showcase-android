package cz.koto.misak.dbshowcase.android.mobile;



/**
 * DbConfig is base config for application.
 */
public class DbConfig {
	public static final String API_DB_PROTOCOL = "http";

	public static final String API_DB_ENDPOINT;

	static {
		API_DB_ENDPOINT = API_DB_PROTOCOL + "://kotopeky.cz/api/db-showcase/";
	}

}
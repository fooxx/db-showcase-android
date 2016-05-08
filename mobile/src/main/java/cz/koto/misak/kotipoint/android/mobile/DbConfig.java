package cz.koto.misak.kotipoint.android.mobile;


/**
 * KoTiPointConfigBase is base config for application.
 */
public class DbConfig
{

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

	/**
	 * Delay (in millis) for testing purpose.
	 */
	public static final int API_KOTINODE_TEST_DELAY = 0;

    public static final String MEDIA_PROTOCOL_PREFIX = "http://";


	public static final String VERSION_NAME = BuildConfig.VERSION_NAME;
}

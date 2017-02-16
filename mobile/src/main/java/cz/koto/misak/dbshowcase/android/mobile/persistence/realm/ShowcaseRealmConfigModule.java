package cz.koto.misak.dbshowcase.android.mobile.persistence.realm;


import javax.inject.Singleton;

import cz.koto.misak.dbshowcase.android.mobile.DbApplication;
import cz.koto.misak.dbshowcase.android.mobile.model.ModelProvider;
import dagger.Module;
import dagger.Provides;
import io.realm.RealmConfiguration;
import timber.log.Timber;


@Module
public class ShowcaseRealmConfigModule {

	public static final int SCHEMA_VERSION = 1;
	public static final String REALM_NAME_OPEN = "open.realm";//REALM_DEFAULT;//DbApplication.class.getSimpleName().toLowerCase() + ".default.realm";
	/**
	 * Use temporarily default.realm to see realm db in Stetho.
	 * TODO use this until this PR will be solved: https://github.com/uPhyca/stetho-realm/pull/38
	 */
	private static final String REALM_DEFAULT = "default.realm";
	public static final String REALM_NAME_ENCRYPTED = REALM_DEFAULT;//DbApplication.class.getSimpleName().toLowerCase() + ".encrypted.realm";
	public static final String REALM_NAME_ASSET = REALM_DEFAULT;//DbApplication.class.getSimpleName().toLowerCase() + ".asset.realm";
	private RealmConfiguration mRealmDefaultConfiguration;
	private RealmConfiguration mRealmEnhancedConfiguration;
	private ShowcaseRealmMigration mRealmMigration;


	@Provides
	@Singleton
	@ShowcaseRealmConfigurationDefault
	public RealmConfiguration provideRealmDefaultConfiguration() {

		if(DbApplication.get().getDbComponent().provideShowcaseRealmLoadModule().isPersistenceEncrypted()) {
			mRealmDefaultConfiguration = new RealmConfiguration.Builder()
					.name(REALM_NAME_ENCRYPTED)
					.encryptionKey(ModelProvider.get().getSecretKey())//Ensure secret key is already loaded!
					.schemaVersion(2)
					.migration(mRealmMigration == null ? new ShowcaseRealmMigration() : mRealmMigration)
					.build();
			DbApplication.get().getDbComponent().provideShowcaseRealmLoadModule().setReady(true);
			Timber.d("Realm configuration path [%s]", mRealmDefaultConfiguration.getPath());
		} else {
			mRealmDefaultConfiguration = new RealmConfiguration.Builder()
					.name(REALM_NAME_OPEN)
					.deleteRealmIfMigrationNeeded()
					.build();
			DbApplication.get().getDbComponent().provideShowcaseRealmLoadModule().setReady(true);
			Timber.d("Realm configuration path [%s]", mRealmDefaultConfiguration.getPath());
		}

		return mRealmDefaultConfiguration;
	}


	/**
	 * When opening the Realm for the first time, instead of creating an empty file,
	 * the Realm file will be copied from the provided asset file and used instead.
	 * <p>
	 * This cannot be configured to clear and recreate schema by calling deleteRealmIfMigrationNeeded()
	 * at the same time as doing so will delete the copied asset schema.
	 * <p>
	 * There is no restriction for the size of file in raw/assets directory since Android 2.3
	 * But there is limit 50MB of the apk. When you exceed this limit, use expansion.apk
	 *
	 * @return
	 */
	@Provides
	@Singleton
	@ShowcaseRealmConfigurationAsset
	public RealmConfiguration provideRealmAssetConfiguration(byte[] secret) {
		if(mRealmEnhancedConfiguration == null) {


//			RealmMigrationUtility.copyBundledRealmFile(ContextProvider.getResources().openRawResource(R.raw.init),
//					REALM_NAME_ASSET);

			mRealmEnhancedConfiguration = new RealmConfiguration.Builder()
					.name(REALM_NAME_ASSET)
					.schemaVersion(SCHEMA_VERSION)
					.migration(mRealmMigration == null ? new ShowcaseRealmMigration() : mRealmMigration)
					.assetFile("realm/init.realm")
					.build();
			DbApplication.get().getDbComponent().provideShowcaseRealmLoadModule().setReady(true);
		}
		return mRealmEnhancedConfiguration;
	}
}

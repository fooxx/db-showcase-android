package cz.koto.misak.dbshowcase.android.mobile;

import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;

import com.facebook.stetho.Stetho;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.squareup.leakcanary.LeakCanary;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import cz.koto.misak.dbshowcase.android.mobile.api.RestModule;
import cz.koto.misak.dbshowcase.android.mobile.persistence.realm.ShowcaseRealmConfigModule;
import cz.koto.misak.dbshowcase.android.mobile.persistence.realm.ShowcaseRealmModule;
import cz.koto.misak.dbshowcase.android.mobile.utility.ContextProvider;
import io.realm.Realm;
import timber.log.Timber;


public class DbApplication extends MultiDexApplication {

	private static DbApplication sInstance;

	private NetComponent mNetComponent;

	private DbComponent mDbComponent;


	public static DbApplication get() {
		return sInstance;
	}


	@Override
	public void onCreate() {
		super.onCreate();

		if(BuildConfig.DEBUG) {

			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//					.detectDiskReads()
//					.detectDiskWrites()
//					.detectNetwork()
					.detectAll()// for all detectable problems
					.penaltyLog()
					.build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//					.detectLeakedSqlLiteObjects()
//					.detectLeakedClosableObjects()
					.detectAll()
					.penaltyLog()
					//.penaltyDeath()
					.build());
		}

		mNetComponent = DaggerNetComponent.builder()
				.restModule(new RestModule())
				.build();

		mDbComponent = DaggerDbComponent.builder()
				.showcaseRealmConfigModule(new ShowcaseRealmConfigModule())
				.showcaseRealmModule(new ShowcaseRealmModule())
				.build();

		sInstance = this;

		ContextProvider.initialize(this);

		if(BuildConfig.DEBUG) {
			Timber.plant(new Timber.DebugTree());
		}

		LeakCanary.install(this);

		/*
		 * INIT DB REALM
		 */
		Realm.init(this);

		/*
		 * INIT STETHO VIEW to DBFLOW/REALM
		 */
		if(BuildConfig.DEBUG) {
			Stetho.initialize(
					Stetho.newInitializerBuilder(this)
							.enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
							.enableWebKitInspector(RealmInspectorModulesProvider.builder(this)
									//.withFolder(getCacheDir())
									//.withEncryptionKey("encrypted.realm", key)
									.withMetaTables()
									//.withDescendingOrder()
									//.withLimit(1000)
									//.databaseNamePattern(Pattern.compile(".+\\\\.realm"))//https://github.com/uPhyca/stetho-realm/pull/38
									.build())
							.build());
		}

		/*
		 * INIT DB FLOW
		 */
		FlowManager.init(new FlowConfig.Builder(this).openDatabasesOnInit(true).build());
	}


	@Override
	public void onTerminate() {
		FlowManager.destroy();
		super.onTerminate();
	}


	public NetComponent getNetComponent() {
		return mNetComponent;
	}


	public DbComponent getDbComponent() {
		return mDbComponent;
	}

}
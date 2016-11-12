package cz.koto.misak.dbshowcase.android.mobile;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.squareup.leakcanary.LeakCanary;

import cz.koto.misak.dbshowcase.android.mobile.api.RestModule;
import cz.koto.misak.dbshowcase.android.mobile.persistence.realm.ShowcaseRealmConfigModule;
import cz.koto.misak.dbshowcase.android.mobile.persistence.realm.ShowcaseRealmCrudModule;
import cz.koto.misak.dbshowcase.android.mobile.utility.ContextProvider;
import io.realm.Realm;
import timber.log.Timber;


public class DbApplication extends Application {

	private static DbApplication sInstance;

	private NetComponent mNetComponent;

	private DbComponent mDbComponent;


	public static DbApplication get() {
		return sInstance;
	}


	@Override
	public void onCreate() {
		super.onCreate();

		mNetComponent = DaggerNetComponent.builder()
				.restModule(new RestModule())
				.build();

		mDbComponent = DaggerDbComponent.builder()
				.showcaseRealmConfigModule(new ShowcaseRealmConfigModule())
				.showcaseRealmCrudModule(new ShowcaseRealmCrudModule())
				.build();

		sInstance = this;

		ContextProvider.initialize(this);

		if(DbConfig.LOGS) {
			Timber.plant(new Timber.DebugTree());
		}

		LeakCanary.install(this);

		Stetho.initialize(
				Stetho.newInitializerBuilder(this)
						.enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
						.enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
						.build());

		FlowManager.init(new FlowConfig.Builder(this).openDatabasesOnInit(true).build());

		Realm.init(this);
		Realm.setDefaultConfiguration(mDbComponent.provideRealmConfiguration());
	}


	public NetComponent getNetComponent() {
		return mNetComponent;
	}


	public DbComponent getDbComponent() {
		return mDbComponent;
	}
}
package cz.koto.misak.dbshowcase.android.mobile;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.facebook.stetho.Stetho;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.squareup.leakcanary.LeakCanary;

import cz.koto.misak.dbshowcase.android.mobile.db.realm.ShowcaseRealm;
import cz.koto.misak.dbshowcase.android.mobile.rest.RestModule;
import cz.koto.misak.dbshowcase.android.mobile.util.CrashlyticsTree;
import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import timber.log.Timber;


public class DbApplication extends Application {

    private static DbApplication sInstance;

    private NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mNetComponent = DaggerNetComponent.builder()
                .restModule(new RestModule())
                .build();

        sInstance = this;

        //Use fabric for non-debug only.
        CrashlyticsCore core = new CrashlyticsCore.Builder()
                .disabled(BuildConfig.DEBUG)
                .build();
        /**
         * Attention!
         * Never let the Fabric to generate plain new Crashlytics() Kit!
         * Always use customized Kit (like the core above) to omit DEV reporting to Crashlytics server.
         */
        Fabric.with(this, new Crashlytics.Builder().core(core).build());

        if (DbConfig.LOGS) {
            Timber.plant(new Timber.DebugTree());
        }

        Timber.plant(new CrashlyticsTree());

        LeakCanary.install(this);

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());

        FlowManager.init(new FlowConfig.Builder(this).openDatabasesOnInit(true).build());

        Realm.setDefaultConfiguration(ShowcaseRealm.getInstance().getmRealmConfiguration());

//        APILoadModule.getInstance().loadApiData();
    }

    public static DbApplication get() {
        return sInstance;
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }


}
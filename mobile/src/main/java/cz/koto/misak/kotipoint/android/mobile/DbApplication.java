package cz.koto.misak.kotipoint.android.mobile;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.squareup.leakcanary.LeakCanary;

import cz.koto.misak.kotipoint.android.mobile.util.CrashlyticsTree;
import io.fabric.sdk.android.Fabric;
import timber.log.Timber;


public class DbApplication extends Application {

    private static DbApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        //Use fabric for non-dev api only.
        CrashlyticsCore core = new CrashlyticsCore.Builder()
                .disabled(DbConfig.DEV_API)
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

//        Realm.setDefaultConfiguration(DBManager.getInstance().getmRealmConfiguration());

        /**
         * This approach save always latest 50 records to DB.
         * TODO: use system of data checkpoints to check whether dataLoading&save is necessary.
         */
//        DBManager.getInstance().saveKoTiEventItemList(new OffsetAndLimit(0,50));

        //DBManager.getInstance().getReaderInstance().readKoTiEventFromRealm(Realm.getDefaultInstance());
        //DBManager.getInstance().getReaderInstance().eventListRealm(0,1000);
        //DBManager.getInstance().getReaderInstance().readEventListRealm_();
        //DBManager.getInstance().getReaderInstance().readEventListRealmSandbox();
    }

    public static DbApplication get() {
        return sInstance;
    }
}

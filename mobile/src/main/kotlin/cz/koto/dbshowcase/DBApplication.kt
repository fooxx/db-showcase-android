package cz.koto.dbshowcase

import android.app.Application
import android.os.StrictMode
import cz.koto.dbshowcase.android.mobile.BuildConfig
import timber.log.Timber

class DBApplication : Application() {


	override fun onCreate() {
		super.onCreate()

		if (BuildConfig.DEBUG) {

			StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
					//					.detectDiskReads()
					//					.detectDiskWrites()
					//					.detectNetwork()
					.detectAll()// for all detectable problems
					.penaltyLog()
					.build())
			StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
					//					.detectLeakedSqlLiteObjects()
					//					.detectLeakedClosableObjects()
					.detectAll()
					.penaltyLog()
					//.penaltyDeath()
					.build())
		}

		if (BuildConfig.DEBUG) {
			Timber.plant(Timber.DebugTree())
		}
	}
}
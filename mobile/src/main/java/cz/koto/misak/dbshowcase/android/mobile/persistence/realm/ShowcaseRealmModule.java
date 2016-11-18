package cz.koto.misak.dbshowcase.android.mobile.persistence.realm;


import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import cz.koto.misak.dbshowcase.android.mobile.model.DataHandlerListener;
import cz.koto.misak.dbshowcase.android.mobile.model.SchoolClassInterface;
import cz.koto.misak.dbshowcase.android.mobile.persistence.realm.model.SchoolClassRealmEntity;
import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import timber.log.Timber;


/**
 * Realm module related to CREATE/READ/UPDATE/DELETE operationss.
 */

@Module
public class ShowcaseRealmModule {

	@Provides
	@Singleton
	public ShowcaseRealmModule provideShowcaseRealmLoadModule() {
		return new ShowcaseRealmModule();
	}


	@Provides
	public List<? extends SchoolClassInterface> provideSchoolClassRealmEntityList(RealmConfiguration realmConfiguration) {
		Realm realm = Realm.getInstance(realmConfiguration);
		return realm.copyFromRealm(realm.where(SchoolClassRealmEntity.class).findAll());
	}


	public void saveOrUpdateSchoolClass(List<? extends SchoolClassInterface> schoolModel,
										DataHandlerListener dataHandlerListener) {
		if(schoolModel == null) return;
		final Realm realm = Realm.getDefaultInstance();
		try {
			List<SchoolClassRealmEntity> persistenceModel = new ArrayList<>();
			for(SchoolClassInterface schoolClass : schoolModel) {
				if(schoolClass instanceof SchoolClassRealmEntity) {
					persistenceModel.add((SchoolClassRealmEntity) schoolClass);
				} else {
					//TODO translate from DBFlow entity to Realm entity
					throw new RuntimeException("Translation from DBFlow entity to Realm entity is NOT implemented yet!");
				}
			}

			if(!persistenceModel.isEmpty()) {
				realm.setAutoRefresh(true);
				realm.executeTransactionAsync(
						// Asynchronously update objects on a background thread
						bgRealm -> bgRealm.copyToRealmOrUpdate(persistenceModel),
						() -> {
							if(realm != null) realm.close();
							dataHandlerListener.handleSuccess();
						},
						error -> {
							if(realm != null) realm.close();
							dataHandlerListener.handleFailed(error);
						});
			}
		} catch(Exception e) {
			dataHandlerListener.handleFailed(e);
			if(realm != null) {
				realm.close();
			}
		}
	}


	public void saveOrUpdateRealmSchoolClass(List<SchoolClassRealmEntity> persistenceModel,
											 DataHandlerListener dataHandlerListener) {
		if(persistenceModel == null) return;
		final Realm realm = Realm.getDefaultInstance();
		try {
			if(!persistenceModel.isEmpty()) {
				// Asynchronously update objects on a background thread
				realm.executeTransactionAsync(
						bgRealm -> bgRealm.copyToRealmOrUpdate(persistenceModel),
						() -> {
							if(realm != null) realm.close();
							dataHandlerListener.handleSuccess();
						},
						error -> {
							if(realm != null) realm.close();
							dataHandlerListener.handleFailed(error);
						});

			}
		} catch(Exception e) {
			dataHandlerListener.handleFailed(e);
			if(realm != null) {
				realm.close();
			}
		}
	}


	/**
	 * Get the only/latest broadcast program from realm database.
	 *
	 * @return
	 */
	@Nullable
	public List<SchoolClassRealmEntity> getSchoolClass() {
		Realm realm = null;
		List<SchoolClassRealmEntity> ret = new ArrayList<>();
		try {
			realm = Realm.getDefaultInstance();

			RealmQuery<SchoolClassRealmEntity> query = realm.where(SchoolClassRealmEntity.class);
			RealmResults<SchoolClassRealmEntity> results = query.findAll();
			if(results.isEmpty()) {
				if(results.isEmpty()) return ret;
			}

			return realm.copyFromRealm(results);
		} catch(Exception e) {
			Timber.e(e, "getSchoolClass from realm failed!");
			return null;
		} finally {
			if(realm != null) {
				realm.close();
			}
		}
	}
}

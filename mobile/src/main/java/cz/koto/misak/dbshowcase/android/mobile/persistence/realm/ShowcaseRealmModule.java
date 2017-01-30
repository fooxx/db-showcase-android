package cz.koto.misak.dbshowcase.android.mobile.persistence.realm;


import android.support.annotation.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import cz.koto.misak.dbshowcase.android.mobile.DbApplication;
import cz.koto.misak.dbshowcase.android.mobile.model.DataHandlerListener;
import cz.koto.misak.dbshowcase.android.mobile.model.ModelProvider;
import cz.koto.misak.dbshowcase.android.mobile.model.SchoolClassInterface;
import cz.koto.misak.dbshowcase.android.mobile.persistence.realm.model.SchoolClassRealmEntity;
import cz.koto.misak.dbshowcase.android.mobile.persistence.realm.model.StudentRealmEntity;
import cz.koto.misak.dbshowcase.android.mobile.persistence.realm.utility.RealmQueryUtility;
import cz.koto.misak.dbshowcase.android.mobile.utility.ContextProvider;
import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import timber.log.Timber;


/**
 * Instead of manually keeping track of realm.beginTransaction(), realm.commitTransaction(),
 * and realm.cancelTransaction() you can use the realm.executeTransaction() method,
 * which will automatically handle begin/commit, and cancel if an error happens.
 */

@Module
public class ShowcaseRealmModule {

	private boolean ready = false;


	public interface LoadSchoolClassAsyncListener {
		void onSuccess(List<SchoolClassRealmEntity> schoolClassRealmEntityList);
		void onError(Throwable throwable);
	}


	public interface InitConfigSuccessListener {
		void onSuccess();
	}


	public interface InitConfigErrorListener {
		void onError(Exception e);
	}


	@Provides
	@Singleton
	public ShowcaseRealmModule provideShowcaseRealmLoadModule() {
		return new ShowcaseRealmModule();
	}


	public void initConfig(InitConfigSuccessListener successListener, InitConfigErrorListener errorListener) {
		if(ModelProvider.get().isPersistenceEncrypted())
			ModelProvider.get().loadSecretKey(
					secret -> {
						Realm.setDefaultConfiguration(DbApplication.get().getDbComponent().provideRealmConfiguration());
						successListener.onSuccess();
					},
					exception -> {
						errorListener.onError(exception);
					});
		else {
			Realm.setDefaultConfiguration(DbApplication.get().getDbComponent().provideRealmConfiguration());
			successListener.onSuccess();
		}
	}


	/**
	 * Persist complete model to realm.
	 * Realm will run that transaction on a background thread and report back when the transaction is done.
	 *
	 * @param schoolModel
	 * @param dataHandlerListener
	 */
	public void saveOrUpdateSchoolClass(List<? extends SchoolClassInterface> schoolModel,
										DataHandlerListener dataHandlerListener) {

		if(isReady()) {
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
								broadcastSuccess(dataHandlerListener);
								dataHandlerListener.handleSuccess();
							},
							error -> {
								if(realm != null) realm.close();
								broadcastFail(dataHandlerListener, error);
							});
				}
			} catch(Exception e) {
				dataHandlerListener.handleFailed(e);
				if(realm != null) {
					realm.close();
				}
			}
		}
	}


	/**
	 * Persist complete model to realm.
	 * Realm will run that transaction on a background thread and report back when the transaction is done.
	 *
	 * @param persistenceModel
	 * @param dataHandlerListener
	 */
	public void saveOrUpdateRealmSchoolClass(List<SchoolClassRealmEntity> persistenceModel,
											 DataHandlerListener dataHandlerListener) {
		if(isReady()) {
			if(persistenceModel == null) return;
			final Realm realm = Realm.getDefaultInstance();
			try {
				if(!persistenceModel.isEmpty()) {
					// Asynchronously update objects on a background thread
					realm.executeTransactionAsync(
							bgRealm -> bgRealm.copyToRealmOrUpdate(persistenceModel),
							() -> {
								if(realm != null) realm.close();
								broadcastSuccess(dataHandlerListener);
							},
							error -> {
								if(realm != null) realm.close();
								broadcastFail(dataHandlerListener, error);
							});

				}
			} catch(Exception e) {
				dataHandlerListener.handleFailed(e);
				if(realm != null) {
					realm.close();
				}
			}
		}
	}


	/**
	 * Persist complete model to realm.
	 * Realm will run that transaction on a background thread and report back when the transaction is done.
	 *
	 * @param schoolClass
	 * @param student
	 * @param dataHandlerListener
	 */
	public void addStudentToClass(SchoolClassRealmEntity schoolClass,
								  StudentRealmEntity student,
								  DataHandlerListener dataHandlerListener) {
		if(isReady()) {
			if((schoolClass == null) || (student == null)) {
				broadcastFail(dataHandlerListener, new RuntimeException("Missing schoolClass or student entity for add operation!"));
				return;
			}
			final Realm realm = Realm.getDefaultInstance();
			try {

				if(realm.where(StudentRealmEntity.class).equalTo("name", student.getName()).findAll().size() > 0) {
					broadcastFail(dataHandlerListener, new RuntimeException("Add failed! Student already exists. Name: " + student.getName()));
					return;
				}
				student.setId(RealmQueryUtility.getNextFreePrimaryKey(realm, StudentRealmEntity.class));
				schoolClass.getStudentList().add(student);
				// Asynchronously update objects on a background thread
				realm.executeTransactionAsync(
						bgRealm -> bgRealm.copyToRealmOrUpdate(schoolClass),
						() -> {
							if(realm != null) realm.close();
							broadcastSuccess(dataHandlerListener);
						},
						error -> {
							if(realm != null) realm.close();
							dataHandlerListener.handleFailed(error);
						});

			} catch(Exception e) {
				dataHandlerListener.handleFailed(e);
				if(realm != null) {
					realm.close();
				}
			}
		}
	}


	/**
	 * Most queries in Realm are fast enough to be run synchronously - even on the UI thread.
	 *
	 * @return
	 */
	@Nullable
	public List<SchoolClassRealmEntity> getSchoolClass() {
		if(isReady()) {
			final Realm realm = Realm.getDefaultInstance();
			try {
				return realm.copyFromRealm(realm.where(SchoolClassRealmEntity.class).findAll());
			} catch(Exception e) {
				Timber.e(e, "getSchoolClass from realm failed!");
				return null;
			} finally {
				if(realm != null) {
					realm.close();
				}
			}
		} else {
			return null;
		}
	}


	/**
	 * For either very complex queries or queries on large data sets
	 * it can be an advantage to run the query on a background thread.
	 *
	 * @return
	 */
	public void loadSchoolClassAsync(LoadSchoolClassAsyncListener loadSchoolClassAsyncListener) {
		if(isReady()) {
			final Realm realm = Realm.getDefaultInstance();
			RealmChangeListener callback = new RealmChangeListener<RealmResults<SchoolClassRealmEntity>>() {
				@Override
				public void onChange(RealmResults<SchoolClassRealmEntity> results) {
					// called once the query complete and on every update
					loadSchoolClassAsyncListener.onSuccess(realm.copyFromRealm(results));
					results.removeChangeListeners();
					if(realm != null) {
						realm.close();
					}
				}
			};

			RealmChangeListener callb = new RealmChangeListener() {
				@Override
				public void onChange(Object element) {
					Timber.d(String.valueOf(element));
				}
			};

			try {
				realm.copyFromRealm(realm.where(SchoolClassRealmEntity.class).findAllAsync());
				realm.addChangeListener(callb);
			} catch(Exception e) {
				Timber.e(e, "getSchoolClass from realm failed!");
				if(realm != null) {
					realm.close();
				}
				loadSchoolClassAsyncListener.onError(e);
			}
		} else {
			loadSchoolClassAsyncListener.onError(new RuntimeException("Realm is NOT initialized yet!"));
		}
	}


	public long getDbSizeInBytes() {
		if(isReady()) {
			final Realm realm = Realm.getDefaultInstance();
			File realmFile = new File(ContextProvider.getContext().getFilesDir(), realm.getConfiguration().getRealmFileName());
//		FileUtils.folderSize(ContextProvider.getContext().getFilesDir());
			Timber.d("Getting size of realm file %s:", realmFile.getName());
			realm.close();
			return realmFile.length();
		} else {
			return 0;
		}
	}


	public File encryptRealm(byte[] secretKey) {
		File openRealmFile = new File(ContextProvider.getContext().getFilesDir(), ShowcaseRealmConfigModule.REALM_NAME_OPEN);
		File encryptedRealmFile = new File(ContextProvider.getContext().getFilesDir(), ShowcaseRealmConfigModule.REALM_NAME_ENCRYPTED);
		encryptedRealmFile.delete();
		if(openRealmFile.exists() && !encryptedRealmFile.exists()) {
			Realm realm = Realm.getDefaultInstance();
			realm.writeEncryptedCopyTo(encryptedRealmFile, secretKey);
			realm.close();
			Realm.deleteRealm(realm.getConfiguration());
			Realm.removeDefaultConfiguration();
			return openRealmFile;
		} else {
			throw new RuntimeException("Migration failed");
		}
	}


	public boolean deleteModel() {
		if(isReady()) {
			return Realm.deleteRealm(DbApplication.get().getDbComponent().provideRealmConfiguration());
		} else {
			return false;
		}
	}


	public boolean isReady() {
		return ready;
	}


	public void setReady(boolean ready) {
		this.ready = ready;
	}


	private void broadcastFail(DataHandlerListener listener, Throwable th) {
		if(listener != null) {
			listener.handleFailed(th);
		}
	}


	private void broadcastSuccess(DataHandlerListener listener) {
		if(listener != null) {
			listener.handleSuccess();
		}
	}
}

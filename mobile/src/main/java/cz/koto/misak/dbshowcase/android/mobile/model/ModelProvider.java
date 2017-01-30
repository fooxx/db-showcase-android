package cz.koto.misak.dbshowcase.android.mobile.model;

import java.util.ArrayList;
import java.util.List;

import cz.koto.misak.dbshowcase.android.mobile.DbApplication;
import cz.koto.misak.dbshowcase.android.mobile.api.DbShowcaseAPIClient;
import cz.koto.misak.dbshowcase.android.mobile.model.utility.SchoolModelComposer;
import cz.koto.misak.dbshowcase.android.mobile.model.utility.SchoolModelGenerator;
import cz.koto.misak.dbshowcase.android.mobile.persistence.PersistenceSyncState;
import cz.koto.misak.dbshowcase.android.mobile.persistence.PersistenceType;
import cz.koto.misak.dbshowcase.android.mobile.persistence.preference.SettingsStorage;
import cz.koto.misak.dbshowcase.android.mobile.persistence.realm.ShowcaseRealmModule;
import cz.koto.misak.dbshowcase.android.mobile.persistence.realm.model.SchoolClassRealmEntity;
import cz.koto.misak.dbshowcase.android.mobile.persistence.realm.model.StudentRealmEntity;
import cz.koto.misak.dbshowcase.android.mobile.utility.ByteUtility;
import cz.koto.misak.keystorecompat.KeystoreCompat;
import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import kotlin.Unit;
import timber.log.Timber;


public class ModelProvider extends SettingsStorage {

	private static ModelProvider sInstance;

	private final SchoolModel mSchoolModel = new SchoolModel();
	ShowcaseRealmModule mRealmModule = DbApplication.get().getDbComponent().provideShowcaseRealmLoadModule();
	private PersistenceType mPersistenceType;
	private PersistenceSyncState mPersistenceSyncState;
	private byte[] mSecretKey = null;


	public interface SecretLoadedCallback {
		void onSecretLoaded(byte[] secret);
	}


	public interface SecretNotFoundCallback {
		void onSecretNotFound(Exception e);
	}


	public ModelProvider() {
		super();
		mPersistenceSyncState = super.getActivePersistenceSyncState();
		mPersistenceType = super.getActivePersistenceType();
	}


	public static ModelProvider get() {
		if(sInstance == null)
			sInstance = new ModelProvider();
		return sInstance;
	}


	@Override
	public PersistenceSyncState getActivePersistenceSyncState() {
		return mPersistenceSyncState;
	}


	@Override
	protected void setActivePersistenceSyncState(PersistenceSyncState persistenceSyncState) {
		super.setActivePersistenceSyncState(persistenceSyncState);
		mPersistenceSyncState = persistenceSyncState;
	}


	@Override
	public PersistenceType getActivePersistenceType() {
		return mPersistenceType;
	}


	@Override
	protected void setActivePersistenceType(PersistenceType persistenceType) {
		super.setActivePersistenceType(persistenceType);
		mPersistenceType = persistenceType;
	}


	public long getDbSizeInBytes() {
		switch(mPersistenceType) {
			case REALM:
				return DbApplication.get().getDbComponent().provideShowcaseRealmLoadModule().getDbSizeInBytes();
			case DB_FLOW:
			default:
				return 0;
		}
	}


	public void loadModel(DataHandlerListener successListener) {
		switch(mPersistenceType) {
			case REALM:

				//SYNCHRONOUS READ
				mSchoolModel.addSchoolItems(mRealmModule.getSchoolClass());
				successListener.handleSuccess();

				//ASYNCHRONOUS READ
//				mRealmModule.loadSchoolClassAsync(
//						new ShowcaseRealmModule.LoadSchoolClassAsyncListener() {
//							@Override
//							public void onSuccess(List<SchoolClassRealmEntity> schoolClassRealmEntityList) {
//								mSchoolModel.addSchoolItems(schoolClassRealmEntityList);
//								successListener.handleSuccess();
//							}
//
//
//							@Override
//							public void onError(Throwable throwable) {
//								successListener.handleFailed(throwable);
//							}
//						}
//				);

				break;
			case DB_FLOW:
			case NONE:
			default:
				successListener.handleFailed(new RuntimeException("Unsupported persistence type:" + mPersistenceType));
		}
	}


	public boolean deleteModel() {
		switch(mPersistenceType) {
			case REALM:
				if(mRealmModule.deleteModel()) {
					mSchoolModel.getSchoolItems().clear();
					return true;
				}
				break;
			case DB_FLOW:
			case NONE:
			default:
				return false;
		}
		return false;
	}


	public SchoolModel getSchoolModel() {
		return mSchoolModel;
	}


	public final void initModelFromApi(DataHandlerListener resultListener) {
		Maybe.zip(DbShowcaseAPIClient.getAPIService().realmClassList(),
				DbShowcaseAPIClient.getAPIService().realmTeacherList(),
				DbShowcaseAPIClient.getAPIService().realmStudentList(),
				SchoolModelComposer::composeSchoolModel)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(list -> {
							setSchoolModel(list, resultListener);
						},
						throwable -> {
							setActivePersistenceSyncState(PersistenceSyncState.ERROR);
							resultListener.handleFailed(throwable);
						});
	}


	public final void addRandomSchoolClass(DataHandlerListener resultListener) {
		switch(mPersistenceType) {
			case REALM:
				SchoolClassRealmEntity s = SchoolModelGenerator.generateSchoolClassRealmEntity(mSchoolModel.getTeachersCount(),
						mSchoolModel.getStudentsCount(), mSchoolModel.getSchoolClassCount());
				addSchoolClass(s, resultListener);
				break;
			case DB_FLOW:
			default:
				Timber.e("Adding school class NOT implemented for %s yet!", mPersistenceType);
		}
	}


	public final <T extends SchoolClassInterface> void addRandomStudent(T schoolClass, DataHandlerListener resultListener) {
		switch(mPersistenceType) {
			case REALM:
				if(schoolClass instanceof SchoolClassRealmEntity) {
					StudentRealmEntity s = SchoolModelGenerator.getRandomStudent(null);
					mRealmModule.addStudentToClass((SchoolClassRealmEntity) schoolClass, s, resultListener);
				} else {
					if(resultListener != null) {
						resultListener.handleFailed(new RuntimeException("Attempt to mix database types in addRandomStudent!"));
					}
				}
				break;
			case DB_FLOW:
			default:
				Timber.e("Adding school class NOT implemented for %s yet!", mPersistenceType);
		}
	}


	public void loadSecretKey(SecretLoadedCallback loadedCallback, SecretNotFoundCallback notFoundCallback) {
		if(mSecretKey == null) {
			if(KeystoreCompat.INSTANCE.hasSecretLoadable() && KeystoreCompat.INSTANCE.isKeystoreCompatAvailable()) {
				KeystoreCompat.INSTANCE.loadSecret(secretKey32 -> {
							mSecretKey = ByteUtility.doubleSizeBytes(secretKey32);
							loadedCallback.onSecretLoaded(mSecretKey);
							return Unit.INSTANCE;
						}
						, e -> {
							notFoundCallback.onSecretNotFound(e);
							return Unit.INSTANCE;
						}, ModelProvider.get().isForceLockScreenFlag());
			} else {
				notFoundCallback.onSecretNotFound(new RuntimeException("Secret is not loadable or keystoreCompat is not available."));
			}
		}
	}


	public byte[] getSecretKey() {
		return mSecretKey;
	}


	public void setSecretKey(byte[] secretKey) {
		mSecretKey = secretKey;
	}


	private final <X extends SchoolClassInterface> void addSchoolClass(SchoolClassRealmEntity re, DataHandlerListener resultListener) {
		List<SchoolClassInterface> ret = new ArrayList<>();
		ret.addAll(mSchoolModel.getSchoolItems());
		ret.add(re);
		setSchoolModel(ret, resultListener);
	}


	private void setSchoolModel(List<? extends SchoolClassInterface> schoolModelItems, DataHandlerListener outerDataHandlerListener) {
		switch(mPersistenceSyncState) {
			case ERROR:
			case ENABLED:
				switch(mPersistenceType) {
					case REALM:
						mRealmModule.saveOrUpdateSchoolClass(schoolModelItems, new DataHandlerListener() {
							@Override
							public void handleSuccess() {
								mSchoolModel.setSchoolItems(schoolModelItems);
								outerDataHandlerListener.handleSuccess();
							}


							@Override
							public void handleFailed(Throwable throwable) {
								setActivePersistenceSyncState(PersistenceSyncState.ERROR);
								mSchoolModel.setSchoolItems(schoolModelItems);
								outerDataHandlerListener.handleFailed(throwable);
							}
						});
						setActivePersistenceSyncState(PersistenceSyncState.ENABLED);
						break;
					case DB_FLOW:
					default:
						mSchoolModel.setSchoolItems(schoolModelItems);
				}
				break;
			case DISABLED:
				mSchoolModel.setSchoolItems(schoolModelItems);
		}

	}


	private void updateModelFromRealm() {
		//loadni model z realmu (+ umozni na plusko load z API + pri zapnute synchronizaci ukladej load z API do realmu).
	}
}

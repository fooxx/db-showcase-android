package cz.koto.misak.dbshowcase.android.mobile.model;

import cz.koto.misak.dbshowcase.android.mobile.api.DbShowcaseAPIClient;
import cz.koto.misak.dbshowcase.android.mobile.api.OnLoadResultListener;
import cz.koto.misak.dbshowcase.android.mobile.model.utility.SchoolModelComposer;
import cz.koto.misak.dbshowcase.android.mobile.persistence.PersistenceSyncState;
import cz.koto.misak.dbshowcase.android.mobile.persistence.PersistenceType;
import cz.koto.misak.dbshowcase.android.mobile.persistence.preference.SettingsStorage;
import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class ModelProvider extends SettingsStorage {

	private static ModelProvider sInstance;

	private final SchoolModel mSchoolModel = new SchoolModel();

	private PersistenceType mPersistenceType;
	private PersistenceSyncState mPersistenceSyncState;


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
	public void setActivePersistenceSyncState(PersistenceSyncState persistenceSyncState) {
		super.setActivePersistenceSyncState(persistenceSyncState);
		mPersistenceSyncState = persistenceSyncState;
	}


	@Override
	public PersistenceType getActivePersistenceType() {
		return mPersistenceType;
	}


	@Override
	public void setActivePersistenceType(PersistenceType persistenceType) {
		super.setActivePersistenceType(persistenceType);
		mPersistenceType = persistenceType;
	}


	public void loadModel(OnLoadResultListener successListener) {
		switch(mPersistenceType) {
			case REALM:
				initModelFromApi(successListener);
				break;
			case DB_FLOW:
				break;
			case NONE:
			default:
				initModelFromApi(successListener);
		}
	}


	public SchoolModel getSchoolModel() {
		return mSchoolModel;
	}


	private void initModelFromApi(OnLoadResultListener resultListener) {
		Maybe.zip(DbShowcaseAPIClient.getAPIService().classList(),
				DbShowcaseAPIClient.getAPIService().teacherList(),
				DbShowcaseAPIClient.getAPIService().studentList(),
				SchoolModelComposer::composeSchoolModel)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(list -> {
							mSchoolModel.setSchoolItems(list);
							setActivePersistenceType(PersistenceType.NONE);
							setActivePersistenceSyncState(PersistenceSyncState.DISABLED);
							resultListener.loadSuccess();
						},
						throwable -> {
							setActivePersistenceSyncState(PersistenceSyncState.ERROR);
							resultListener.loadFailed(throwable);
						});
	}
}

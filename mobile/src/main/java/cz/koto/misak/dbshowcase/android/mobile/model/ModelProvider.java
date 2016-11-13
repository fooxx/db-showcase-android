package cz.koto.misak.dbshowcase.android.mobile.model;

import cz.koto.misak.dbshowcase.android.mobile.api.DbShowcaseAPIClient;
import cz.koto.misak.dbshowcase.android.mobile.api.OnDataLoadedListener;
import cz.koto.misak.dbshowcase.android.mobile.model.utility.SchoolModelComposer;
import cz.koto.misak.dbshowcase.android.mobile.persistence.PersistenceSyncState;
import cz.koto.misak.dbshowcase.android.mobile.persistence.PersistenceType;
import cz.koto.misak.dbshowcase.android.mobile.persistence.preference.SettingsStorage;
import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class ModelProvider extends SettingsStorage {

	private static ModelProvider sInstance;

	private final SchoolModel mSchoolModel = new SchoolModel();

	private PersistenceType mPersistenceType;
	private PersistenceSyncState mPersistenceSyncState;


	public ModelProvider() {
		super();
		mPersistenceSyncState = getActivePersistenceSyncState();
		mPersistenceType = getActivePersistenceType();
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


	public void initModelFromApi(OnDataLoadedListener successListener) {
		Maybe.zip(DbShowcaseAPIClient.getAPIService().classList(),
				DbShowcaseAPIClient.getAPIService().teacherList(),
				DbShowcaseAPIClient.getAPIService().studentList(),
				SchoolModelComposer::composeSchoolModel)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(list -> {
							mSchoolModel.setSchoolItems(list);
							successListener.loadSuccess();
						},
						throwable -> {
							Timber.d(throwable, "on error init:");
						});
	}


	public SchoolModel getSchoolModel() {
		return mSchoolModel;
	}
}

package cz.koto.misak.dbshowcase.android.mobile.model;

import cz.koto.misak.dbshowcase.android.mobile.api.DbShowcaseAPIClient;
import cz.koto.misak.dbshowcase.android.mobile.api.OnDataLoadedListener;
import cz.koto.misak.dbshowcase.android.mobile.model.utility.SchoolModelComposer;
import cz.koto.misak.dbshowcase.android.mobile.persistence.PersistenceSyncState;
import cz.koto.misak.dbshowcase.android.mobile.persistence.PersistenceType;
import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class ModelProvider {

	private static final SchoolModel sSchoolModel = new SchoolModel();
	private static PersistenceType sPersistenceProviderType = PersistenceType.NONE;
	private static PersistenceSyncState sPersistenceSyncState = PersistenceSyncState.DISABLED;


	public static void initModelFromApi(OnDataLoadedListener successListener) {
		Maybe.zip(DbShowcaseAPIClient.getAPIService().classList(),
				DbShowcaseAPIClient.getAPIService().teacherList(),
				DbShowcaseAPIClient.getAPIService().studentList(),
				SchoolModelComposer::composeSchoolModel)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(list -> {
							sSchoolModel.setSchoolItems(list);
							successListener.loadSuccess();
						},
						throwable -> {
							Timber.d(throwable, "on error init:");
						});
	}


	public static SchoolModel getSchoolModel() {
		return sSchoolModel;
	}


	public static PersistenceType getPersistenceType() {
		return sPersistenceProviderType;
	}


	public static PersistenceSyncState getPersistenceSyncState() {
		return sPersistenceSyncState;
	}
}

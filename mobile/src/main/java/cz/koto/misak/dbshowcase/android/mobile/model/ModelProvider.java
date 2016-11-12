package cz.koto.misak.dbshowcase.android.mobile.model;

import java.util.List;

import cz.koto.misak.dbshowcase.android.mobile.api.DbShowcaseAPIClient;
import cz.koto.misak.dbshowcase.android.mobile.api.OnDataLoadedListener;
import cz.koto.misak.dbshowcase.android.mobile.persistence.dbflow.DbFlowCrudModule;
import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class ModelProvider {

	private static SchoolModel sSchoolModel = new SchoolModel();


	public static void initModelFromApi(OnDataLoadedListener successListener) {
		Maybe.zip(DbShowcaseAPIClient.getAPIService().classListDbFlow(),
				DbShowcaseAPIClient.getAPIService().teacherListDbFlow(),
				DbShowcaseAPIClient.getAPIService().studentListDbFlow(),
				(schoolClassEntities, teacherEntities, studentEntities) -> {
					DbFlowCrudModule.saveDataToDb(schoolClassEntities, teacherEntities, studentEntities, () -> {
						List<SchoolClassInterface> list = DbFlowCrudModule.getClassListDbFlow();
						Timber.d("onDataSavedToDb %s", list.size());
						sSchoolModel.setList(list);
						successListener.loadSuccess();
					});
					return new Object();
				})
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(o -> {
						},
						throwable -> {
							Timber.d(throwable, "on error init:");
						});
	}


	public static SchoolModel getSchoolModel() {
		return sSchoolModel;
	}
}

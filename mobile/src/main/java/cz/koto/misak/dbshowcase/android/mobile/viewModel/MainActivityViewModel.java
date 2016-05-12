package cz.koto.misak.dbshowcase.android.mobile.viewModel;

import android.util.Log;

import java.util.List;

import cz.kinst.jakub.viewmodelbinding.ViewModel;
import cz.koto.misak.dbshowcase.android.mobile.databinding.ActivityMainBinding;
import cz.koto.misak.dbshowcase.android.mobile.db.dbflow.DbHelper;
import cz.koto.misak.dbshowcase.android.mobile.entity.dbflow.SchoolClassDbFlowEntity;
import cz.koto.misak.dbshowcase.android.mobile.entity.dbflow.StudentDbFlowEntity;
import cz.koto.misak.dbshowcase.android.mobile.entity.dbflow.TeacherDbFlowEntity;
import cz.koto.misak.dbshowcase.android.mobile.rest.DbShowcaseAPIClient;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func3;
import rx.schedulers.Schedulers;


public class MainActivityViewModel extends ViewModel<ActivityMainBinding>
{

	@Override
	public void onViewModelCreated()
	{
		super.onViewModelCreated();
		loadApiData();
	}


	public void loadApiData()
	{
		Observable.zip(DbShowcaseAPIClient.getAPIService().classListDbFlow(),
				DbShowcaseAPIClient.getAPIService().teacherListDbFlow(),
				DbShowcaseAPIClient.getAPIService().studentListDbFlow(),
				new Func3<List<SchoolClassDbFlowEntity>, List<TeacherDbFlowEntity>, List<StudentDbFlowEntity>, Object>()
				{
					@Override
					public Object call(List<SchoolClassDbFlowEntity> schoolClassEntities, List<TeacherDbFlowEntity> teacherEntities, List<StudentDbFlowEntity> studentEntities)
					{
						DbHelper.saveDataToDb(schoolClassEntities, teacherEntities, studentEntities, new OnDataSavedToDbListener()
						{
							@Override
							public void onDataSavedToDb()
							{

							}
						});
						return null;
					}
				})
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<Object>()
				{
					@Override
					public void onCompleted()
					{
						Log.d(MainActivityViewModel.class.getSimpleName(), "saved to Db");
					}


					@Override
					public void onError(Throwable e)
					{
						Log.d(MainActivityViewModel.class.getSimpleName(), "not saved to Db - error");
					}


					@Override
					public void onNext(Object o)
					{
						Log.d(MainActivityViewModel.class.getSimpleName(), "saving to db on next");
					}
				});
	}

}

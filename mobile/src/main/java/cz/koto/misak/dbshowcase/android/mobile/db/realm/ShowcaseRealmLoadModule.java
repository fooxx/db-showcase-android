package cz.koto.misak.dbshowcase.android.mobile.db.realm;


import java.util.List;

import javax.inject.Singleton;

import cz.koto.misak.dbshowcase.android.mobile.entity.entityinterface.SchoolClassInterface;
import cz.koto.misak.dbshowcase.android.mobile.entity.rest.SchoolClassEntity;
import cz.koto.misak.dbshowcase.android.mobile.entity.rest.StudentEntity;
import cz.koto.misak.dbshowcase.android.mobile.entity.rest.TeacherEntity;
import cz.koto.misak.dbshowcase.android.mobile.rest.DbShowcaseAPIClient;
import cz.koto.misak.dbshowcase.android.mobile.util.BackgroundExecutor;
import dagger.Module;
import dagger.Provides;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;


@Module
public class ShowcaseRealmLoadModule
{

	@Provides
	@Singleton
	public ShowcaseRealmLoadModule provideShowcaseRealmLoadModule(){
		return new ShowcaseRealmLoadModule();
	}

	public void loadRealmFromApi()
	{
		loadClassData(createClassDataSubscriber());
	}


	private void loadClassData(Subscriber<List<SchoolClassEntity>> subscriber)
	{
		DbShowcaseAPIClient.getAPIService().classList()
				.subscribeOn(Schedulers.from(BackgroundExecutor.getSafeBackgroundExecutor()))
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(subscriber);
	}


	private Subscriber<List<SchoolClassEntity>> createClassDataSubscriber()
	{
		return new Subscriber<List<SchoolClassEntity>>()
		{
			//Realm realm = Realm.getInstance(ShowcaseRealmConfigModule.getInstance().getmRealmConfiguration());


			@Override
			public void onCompleted()
			{
				Timber.v("Realm Load/save classes for realm completed!");
				//realm.close();
			}


			@Override
			public void onError(Throwable e)
			{
				Timber.e(e, "Realm Load/save classes for realm error!");
			}


			@Override
			public void onNext(List<SchoolClassEntity> ts)
			{
				for(SchoolClassInterface schoolClassInterface : ts)
				{
					Timber.v("Realm SchoolClass from API: %s", schoolClassInterface);
				}

				// Copy elements from Retrofit to Realm to persist them.
				//realm.beginTransaction();
				//List<SchoolClassInterface> realmRepos = realm.copyToRealmOrUpdate(ts);
				//realm.commitTransaction();
			}
		};
	}


	private void loadTeacherData(Subscriber<List<TeacherEntity>> subscriber)
	{
		DbShowcaseAPIClient.getAPIService().teacherList()
				.subscribeOn(Schedulers.from(BackgroundExecutor.getSafeBackgroundExecutor()))
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(subscriber);

	}


	private Subscriber<List<TeacherEntity>> createTeacherDataSubscriber()
	{
		return new Subscriber<List<TeacherEntity>>()
		{
			//Realm realm = Realm.getInstance(ShowcaseRealmConfigModule.getInstance().getmRealmConfiguration());


			@Override
			public void onCompleted()
			{
				Timber.v("Realm Load/save teachers completed!");
				//realm.close();
			}


			@Override
			public void onError(Throwable e)
			{
				Timber.e(e, "Realm Load/save teachers error!");
			}


			@Override
			public void onNext(List<TeacherEntity> ts)
			{
				// Copy elements from Retrofit to Realm to persist them.
				//realm.beginTransaction();
				//List<TeacherInterface> realmRepos = realm.copyToRealmOrUpdate(ts);
				//realm.commitTransaction();
			}
		};
	}


	private void loadStudentData(Subscriber<List<StudentEntity>> subscriber)
	{
		DbShowcaseAPIClient.getAPIService().studentList()
				.subscribeOn(Schedulers.from(BackgroundExecutor.getSafeBackgroundExecutor()))
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(subscriber);
	}


	private Subscriber<List<StudentEntity>> createStudentDataSubscriber()
	{
		return new Subscriber<List<StudentEntity>>()
		{
			//Realm realm = Realm.getInstance(ShowcaseRealmConfigModule.getInstance().getmRealmConfiguration());


			@Override
			public void onCompleted()
			{
				Timber.v("Realm Load/save students completed!");
				//realm.close();
			}


			@Override
			public void onError(Throwable e)
			{
				Timber.e(e, "Realm Load/save students error!");
			}


			@Override
			public void onNext(List<StudentEntity> ts)
			{
				// Copy elements from Retrofit to Realm to persist them.
				//realm.beginTransaction();
				//List<SchoolClassInterface> realmRepos = realm.copyToRealmOrUpdate(ts);
				//realm.commitTransaction();
			}
		};
	}

}

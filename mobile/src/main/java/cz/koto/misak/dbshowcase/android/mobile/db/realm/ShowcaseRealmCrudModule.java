package cz.koto.misak.dbshowcase.android.mobile.db.realm;


import java.util.List;

import javax.inject.Singleton;

import cz.koto.misak.dbshowcase.android.mobile.entity.realm.SchoolClassRealmEntity;
import cz.koto.misak.dbshowcase.android.mobile.entity.realm.StudentRealmEntity;
import cz.koto.misak.dbshowcase.android.mobile.entity.realm.TeacherRealmEntity;
import cz.koto.misak.dbshowcase.android.mobile.rest.DbShowcaseAPIClient;
import cz.koto.misak.dbshowcase.android.mobile.util.BackgroundExecutor;
import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;


/**
 * Realm module related to CREATE/READ/UPDATE/DELETE operationss.
 */

@Module
public class ShowcaseRealmCrudModule {

    @Provides
    @Singleton
    public ShowcaseRealmCrudModule provideShowcaseRealmLoadModule() {
        return new ShowcaseRealmCrudModule();
    }

    public void loadRealmFromApi(RealmConfiguration realmConfiguration) {
        loadClassData(createClassDataSubscriber(realmConfiguration));
    }


    private void loadClassData(Subscriber<List<SchoolClassRealmEntity>> subscriber) {
        DbShowcaseAPIClient.getAPIService().classList()
                .subscribeOn(Schedulers.from(BackgroundExecutor.getSafeBackgroundExecutor()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    private Subscriber<List<SchoolClassRealmEntity>> createClassDataSubscriber(RealmConfiguration realmConfiguration) {
        return new Subscriber<List<SchoolClassRealmEntity>>() {
            Realm realm = Realm.getInstance(realmConfiguration);


            @Override
            public void onCompleted() {
                Timber.v("Realm Load/save classes for realm completed!");
                realm.close();
            }


            @Override
            public void onError(Throwable e) {
                Timber.e(e, "Realm Load/save classes for realm error!");
            }


            @Override
            public void onNext(List<SchoolClassRealmEntity> ts) {
                for (SchoolClassRealmEntity schoolClassRealmEntity : ts) {
                    Timber.v("Realm SchoolClass from API: %s", schoolClassRealmEntity);
                    // Copy elements from Retrofit to Realm to persist them.
                    realm.beginTransaction();
                    SchoolClassRealmEntity dispatchedSchoolClassRealmEntity = realm.copyToRealmOrUpdate(schoolClassRealmEntity);
                    realm.commitTransaction();

                }

            }
        };
    }


    private void loadTeacherData(Subscriber<List<TeacherRealmEntity>> subscriber) {
        DbShowcaseAPIClient.getAPIService().teacherList()
                .subscribeOn(Schedulers.from(BackgroundExecutor.getSafeBackgroundExecutor()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }


    private Subscriber<List<TeacherRealmEntity>> createTeacherDataSubscriber() {
        return new Subscriber<List<TeacherRealmEntity>>() {
            //Realm realm = Realm.getInstance(ShowcaseRealmConfigModule.getInstance().getmRealmConfiguration());


            @Override
            public void onCompleted() {
                Timber.v("Realm Load/save teachers completed!");
                //realm.close();
            }


            @Override
            public void onError(Throwable e) {
                Timber.e(e, "Realm Load/save teachers error!");
            }


            @Override
            public void onNext(List<TeacherRealmEntity> ts) {
                // Copy elements from Retrofit to Realm to persist them.
                //realm.beginTransaction();
                //List<TeacherInterface> realmRepos = realm.copyToRealmOrUpdate(ts);
                //realm.commitTransaction();
            }
        };
    }


    private void loadStudentData(Subscriber<List<StudentRealmEntity>> subscriber) {
        DbShowcaseAPIClient.getAPIService().studentList()
                .subscribeOn(Schedulers.from(BackgroundExecutor.getSafeBackgroundExecutor()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    private Subscriber<List<StudentRealmEntity>> createStudentDataSubscriber() {
        return new Subscriber<List<StudentRealmEntity>>() {
            //Realm realm = Realm.getInstance(ShowcaseRealmConfigModule.getInstance().getmRealmConfiguration());


            @Override
            public void onCompleted() {
                Timber.v("Realm Load/save students completed!");
                //realm.close();
            }


            @Override
            public void onError(Throwable e) {
                Timber.e(e, "Realm Load/save students error!");
            }


            @Override
            public void onNext(List<StudentRealmEntity> ts) {
                // Copy elements from Retrofit to Realm to persist them.
                //realm.beginTransaction();
                //List<SchoolClassInterface> realmRepos = realm.copyToRealmOrUpdate(ts);
                //realm.commitTransaction();
            }
        };
    }

}

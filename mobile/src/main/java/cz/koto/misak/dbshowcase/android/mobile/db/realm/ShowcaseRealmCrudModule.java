package cz.koto.misak.dbshowcase.android.mobile.db.realm;


import android.os.Looper;

import java.util.List;

import javax.inject.Singleton;

import cz.koto.misak.dbshowcase.android.mobile.entity.entityinterface.SchoolClassInterface;
import cz.koto.misak.dbshowcase.android.mobile.entity.realm.RealmLong;
import cz.koto.misak.dbshowcase.android.mobile.entity.realm.SchoolClassRealmEntity;
import cz.koto.misak.dbshowcase.android.mobile.entity.realm.StudentRealmEntity;
import cz.koto.misak.dbshowcase.android.mobile.entity.realm.TeacherRealmEntity;
import cz.koto.misak.dbshowcase.android.mobile.listener.DataSaveErrorListener;
import cz.koto.misak.dbshowcase.android.mobile.listener.DataSaveSuccessListener;
import cz.koto.misak.dbshowcase.android.mobile.rest.DbShowcaseAPIClient;
import cz.koto.misak.dbshowcase.android.mobile.util.BackgroundExecutor;
import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import rx.Observable;
import rx.Observer;
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

    @Provides
    public List<? extends SchoolClassInterface> provideSchoolClassRealmEntityList(RealmConfiguration realmConfiguration) {
        Realm realm = Realm.getInstance(realmConfiguration);
        return realm.copyFromRealm(realm.where(SchoolClassRealmEntity.class).findAll());
    }

    public void loadRealmFromApi(RealmConfiguration realmConfiguration,
                                 DataSaveSuccessListener saveSuccessListener,
                                 DataSaveErrorListener saveErrorListener) {
        //loadClassData(createClassDataSubscriber(realmConfiguration));

        Observable.zip(DbShowcaseAPIClient.getAPIService().classList(),
                DbShowcaseAPIClient.getAPIService().teacherList(),
                DbShowcaseAPIClient.getAPIService().studentList(),
                (schoolClassEntities, teacherEntities, studentEntities) -> {
                    saveRealmData(schoolClassEntities, teacherEntities, studentEntities, saveSuccessListener, saveErrorListener, realmConfiguration);
                    return null;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onCompleted() {
                        Timber.v("saved to Db");
                    }


                    @Override
                    public void onError(Throwable e) {
                        Timber.e("not saved to Db - error");
                        saveErrorListener.onDataSaveError();
                        e.printStackTrace();
                    }


                    @Override
                    public void onNext(Object o) {
                        Timber.v("saving to db on next");
                    }
                });

    }

    /**
     * It compose all bindings among available lists and save this object structure in the realm.io
     * Do NOT run this method on UI thread! RuntimeException will be thrown otherwise.
     *
     * @param schoolClassRealmEntityList
     * @param teacherRealmEntityList
     * @param studentRealmEntityList
     * @param saveSuccessListener
     * @param saveErrorListener
     * @param realmConfiguration
     */
    private void saveRealmData(List<SchoolClassRealmEntity> schoolClassRealmEntityList,
                               List<TeacherRealmEntity> teacherRealmEntityList,
                               List<StudentRealmEntity> studentRealmEntityList,
                               DataSaveSuccessListener saveSuccessListener,
                               DataSaveErrorListener saveErrorListener,
                               RealmConfiguration realmConfiguration) {

        if (Looper.myLooper() == Looper.getMainLooper()){
            throw new RuntimeException("Do NOT call saveRealmData on UI thread!");
        }

        for (SchoolClassRealmEntity schoolClass : schoolClassRealmEntityList) {
            for (TeacherRealmEntity teacher : teacherRealmEntityList) {

                for (RealmLong teacherId : schoolClass.getStudentIdRealmList()){
                    if (teacherId.getLongValue() == teacher.getId()){
                        schoolClass.getTeacherRealmList().add(teacher);
                    }
                }
            }

            for (StudentRealmEntity student : studentRealmEntityList) {


                for (RealmLong studentId : schoolClass.getStudentIdRealmList()){
                    if (studentId.getLongValue() == student.getId()){
                        schoolClass.getStudentRealmList().add(student);
                    }
                }

            }
        }

        Realm realm = Realm.getInstance(realmConfiguration);
//        realm.beginTransaction();
//        List<SchoolClassRealmEntity> dispatchedSchoolClassRealmEntity = realm.copyToRealmOrUpdate(schoolClassRealmEntityList);
//        realm.commitTransaction();
//        saveSuccessListener.onDataSaveSuccess();

//
//        RealmAsyncTask transaction = realm.executeTransactionAsync(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                List<SchoolClassRealmEntity> dispatchedSchoolClassRealmEntity = realm.copyToRealmOrUpdate(schoolClassRealmEntityList);
//            }
//        }, new Realm.Transaction.OnSuccess() {
//            @Override
//            public void onSuccess() {
//                saveSuccessListener.onDataSaveSuccess();
//            }
//        }, new Realm.Transaction.OnError() {
//            @Override
//            public void onError(Throwable error) {
//                saveErrorListener.onDataSaveError();
//            }
//        });


        /**
         * Yes, this is synchronous transaction.
         * Ensure ui thread difference via call saveRealmData on separated thread!
         */
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm) {
                List<SchoolClassRealmEntity> dispatchedSchoolClassRealmEntity = realm.copyToRealmOrUpdate(schoolClassRealmEntityList);
            }
        });

        //TODO fix missing error listener!
        saveSuccessListener.onDataSaveSuccess();
    }


    private void loadClassData(Subscriber<List<SchoolClassRealmEntity>> subscriber) {
        DbShowcaseAPIClient.getAPIService().classList()
                .subscribeOn(Schedulers.from(BackgroundExecutor.getSafeBackgroundExecutor()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    private Subscriber<List<SchoolClassRealmEntity>> createClassDataSubscriber(RealmConfiguration realmConfiguration) {
        return new Subscriber<List<SchoolClassRealmEntity>>() {
            //Realm realm = Realm.getInstance(realmConfiguration);


            @Override
            public void onCompleted() {
                Timber.v("Realm Load/save classes for realm completed!");
                //realm.close();
            }


            @Override
            public void onError(Throwable e) {
                Timber.e(e, "Realm Load/save classes for realm error!");
            }


            @Override
            public void onNext(List<SchoolClassRealmEntity> ts) {
//                for (SchoolClassRealmEntity schoolClassRealmEntity : ts) {
//                    Timber.v("Realm SchoolClass from API: %s", schoolClassRealmEntity);
//                    // Copy elements from Retrofit to Realm to persist them.
//                    realm.beginTransaction();
//                    SchoolClassRealmEntity dispatchedSchoolClassRealmEntity = realm.copyToRealmOrUpdate(schoolClassRealmEntity);
//                    realm.commitTransaction();
//
//                }

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

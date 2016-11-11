package cz.koto.misak.dbshowcase.android.mobile.ui;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import cz.kinst.jakub.view.StatefulLayout;
import cz.kinst.jakub.viewmodelbinding.ViewModel;
import cz.koto.misak.dbshowcase.android.mobile.DbApplication;
import cz.koto.misak.dbshowcase.android.mobile.DbConfig;
import cz.koto.misak.dbshowcase.android.mobile.R;
import cz.koto.misak.dbshowcase.android.mobile.adapter.ClassRecyclerViewAdapter;
import cz.koto.misak.dbshowcase.android.mobile.api.DbShowcaseAPIClient;
import cz.koto.misak.dbshowcase.android.mobile.databinding.ActivityMain2Binding;
import cz.koto.misak.dbshowcase.android.mobile.model.SchoolClassInterface;
import cz.koto.misak.dbshowcase.android.mobile.persistence.dbflow.DbFlowCrudModule;
import cz.koto.misak.dbshowcase.android.mobile.persistence.dbflow.model.SchoolClassDbFlowEntity;
import cz.koto.misak.dbshowcase.android.mobile.persistence.dbflow.model.StudentDbFlowEntity;
import cz.koto.misak.dbshowcase.android.mobile.persistence.dbflow.model.TeacherDbFlowEntity;
import cz.koto.misak.dbshowcase.android.mobile.persistence.listener.DataSaveSuccessListener;
import cz.koto.misak.dbshowcase.android.mobile.persistence.realm.ShowcaseRealmConfigurationMarker;
import cz.koto.misak.dbshowcase.android.mobile.persistence.realm.ShowcaseRealmCrudModule;
import cz.koto.misak.dbshowcase.android.mobile.ui.listener.OnClassItemClickListener;
import cz.koto.misak.dbshowcase.android.mobile.utility.RandomString;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.RealmConfiguration;
import timber.log.Timber;


public class MainActivityViewModel2 extends ViewModel<ActivityMain2Binding> {

	public final ObservableBoolean progress = new ObservableBoolean(false);

	public final ObservableField<StatefulLayout.State> state = new ObservableField<>();
	@Inject
	ShowcaseRealmCrudModule realmLoadModule;

	@Inject
	@ShowcaseRealmConfigurationMarker
	RealmConfiguration showcaseRealmConfiguration;
	private ClassRecyclerViewAdapter adapter;


	public MainActivityViewModel2() {
		DbApplication.get().getDbComponent().inject(this);
	}


	@Override
	public void onViewModelCreated() {
		super.onViewModelCreated();
		state.set(StatefulLayout.State.PROGRESS);
		adapter = new ClassRecyclerViewAdapter(new ArrayList<>(), new OnClassItemClickListener() {
			@Override
			public void onAddStudentClick(int position) {
				DbFlowCrudModule.insertNewStudentForClass(getRandomStudent(), (SchoolClassDbFlowEntity) adapter.getItem(position), new DataSaveSuccessListener() {
					@Override
					public void onDataSaveSuccess() {
						updateItemIfStudentsWereChanged(position);
					}
				});
			}


			@Override
			public void onAddTeacherClick(int position) {
				DbFlowCrudModule.insertNewTeacherForClass(getRandomTeacher(), (SchoolClassDbFlowEntity) adapter.getItem(position), new DataSaveSuccessListener() {
					@Override
					public void onDataSaveSuccess() {
						updateItemIfTeachersWereChanged(position);
					}
				});
			}


			@Override
			public void onRemoveStudentClick(int position) {
				DbFlowCrudModule.deleteFirstStudentFromClass((SchoolClassDbFlowEntity) adapter.getItem(position), new DataSaveSuccessListener() {
					@Override
					public void onDataSaveSuccess() {
						updateItemIfStudentsWereChanged(position);
					}
				});
			}


			@Override
			public void onRemoveTeacherClick(int position) {
				DbFlowCrudModule.deleteFirstTeacherFromClass((SchoolClassDbFlowEntity) adapter.getItem(position), new DataSaveSuccessListener() {
					@Override
					public void onDataSaveSuccess() {
						updateItemIfTeachersWereChanged(position);
					}
				});
			}
		});


		switch(DbConfig.DB_SHOWCASE) {
			case REALM_IO:
				realmLoadModule.loadRealmFromApi(showcaseRealmConfiguration, () -> {

					List<? extends SchoolClassInterface> list = realmLoadModule.provideSchoolClassRealmEntityList(showcaseRealmConfiguration);
					Timber.d("onDataSaveSuccess %s", list.size());
					state.set(StatefulLayout.State.CONTENT);

					/**
					 * Use ui for REFILL operation to prevent:
					 * Only the original thread that created a view hierarchy can touch its views.
					 */
					this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							adapter.refill(list);
						}
					});

				}, () -> {
					state.set(StatefulLayout.State.EMPTY);
				});
				break;
			case DB_FLOW:
				loadDbFlowFromApi();
				break;
			default:
				Timber.e("Configured database [%s] not implemented!", DbConfig.DB_SHOWCASE);
		}
	}


	public ClassRecyclerViewAdapter getAdapter() {
		return adapter;
	}


	public String getAppTitle() {
		return getResources().getString(R.string.app_title) + ":" +
				" [" + DbConfig.DB_SHOWCASE + "]";
	}


	public void loadDbFlowFromApi() {
		Observable.zip(DbShowcaseAPIClient.getAPIService().classListDbFlow(),
				DbShowcaseAPIClient.getAPIService().teacherListDbFlow(),
				DbShowcaseAPIClient.getAPIService().studentListDbFlow(),
				(schoolClassEntities, teacherEntities, studentEntities) -> {
					DbFlowCrudModule.saveDataToDb(schoolClassEntities, teacherEntities, studentEntities, () -> {
						List<SchoolClassInterface> list = DbFlowCrudModule.getClassListDbFlow();
						Timber.d("onDataSavedToDb %s", list.size());
						state.set(StatefulLayout.State.CONTENT);
						adapter.refill(list);
					});
					return null;
				})
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(o -> {
						},
						throwable -> {
						});
//                .subscribe(new Observer<Object>() {
//                    @Override
//                    public void onCompleted() {
//                        Timber.d("saved to Db");
//                    }
//
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Timber.d("not saved to Db - error");
//                        state.set(StatefulLayout.State.OFFLINE);
//                        e.printStackTrace();
//                    }
//
//
//                    @Override
//                    public void onNext(Object o) {
//                        Timber.d("saving to db on next");
//                    }
//                });
	}


	private void updateItemIfStudentsWereChanged(int position) {
		SchoolClassInterface schoolClass = adapter.getItem(position);
		if(schoolClass.getStudentList() != null) schoolClass.getStudentList().clear();
		if(schoolClass.getStudentIdList() != null) schoolClass.getStudentIdList().clear();
		adapter.notifyItemChanged(position);
	}


	private void updateItemIfTeachersWereChanged(int position) {
		SchoolClassInterface schoolClass = adapter.getItem(position);
		if(schoolClass.getTeacherList() != null) schoolClass.getTeacherList().clear();
		if(schoolClass.getTeacherIdList() != null) schoolClass.getTeacherIdList().clear();
		adapter.notifyItemChanged(position);
	}


	private StudentDbFlowEntity getRandomStudent() {
		StudentDbFlowEntity student = new StudentDbFlowEntity();
		student.setName(new RandomString(8).nextString());
		student.setBirthDate(new Date());
		return student;
	}


	private TeacherDbFlowEntity getRandomTeacher() {
		TeacherDbFlowEntity teacher = new TeacherDbFlowEntity();
		teacher.setName(new RandomString(8).nextString());
		return teacher;
	}

}

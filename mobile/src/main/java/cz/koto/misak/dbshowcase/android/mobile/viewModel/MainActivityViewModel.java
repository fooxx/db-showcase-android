package cz.koto.misak.dbshowcase.android.mobile.viewModel;

import android.databinding.ObservableField;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.kinst.jakub.view.StatefulLayout;
import cz.kinst.jakub.viewmodelbinding.ViewModel;
import cz.koto.misak.dbshowcase.android.mobile.adapter.ClassRecyclerViewAdapter;
import cz.koto.misak.dbshowcase.android.mobile.databinding.ActivityMainBinding;
import cz.koto.misak.dbshowcase.android.mobile.db.dbflow.DbHelper;
import cz.koto.misak.dbshowcase.android.mobile.entity.dbflow.SchoolClassDbFlowEntity;
import cz.koto.misak.dbshowcase.android.mobile.entity.dbflow.StudentDbFlowEntity;
import cz.koto.misak.dbshowcase.android.mobile.entity.entityinterface.SchoolClassInterface;
import cz.koto.misak.dbshowcase.android.mobile.entity.entityinterface.StudentInterface;
import cz.koto.misak.dbshowcase.android.mobile.listener.OnClassItemClickListener;
import cz.koto.misak.dbshowcase.android.mobile.listener.OnDataSavedToDbListener;
import cz.koto.misak.dbshowcase.android.mobile.rest.DbShowcaseAPIClient;
import cz.koto.misak.dbshowcase.android.mobile.util.RandomString;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivityViewModel extends ViewModel<ActivityMainBinding>
{

	public final ObservableField<StatefulLayout.State> state = new ObservableField<>();

	private ClassRecyclerViewAdapter adapter;


	public ClassRecyclerViewAdapter getAdapter()
	{
		return adapter;
	}


	@Override
	public void onViewModelCreated()
	{
		super.onViewModelCreated();
		state.set(StatefulLayout.State.PROGRESS);
		adapter = new ClassRecyclerViewAdapter(new ArrayList<>(), new OnClassItemClickListener()
		{
			@Override
			public void onAddStudentClick(int position)
			{
				StudentDbFlowEntity student = getRandomStudent();
				DbHelper.insertNewStudentForClass(student, (SchoolClassDbFlowEntity) adapter.getItem(position), new OnDataSavedToDbListener()
				{
					@Override
					public void onDataSavedToDb()
					{
						SchoolClassInterface schoolClass = adapter.getItem(position);
						if(schoolClass.getStudentList() != null) schoolClass.getStudentList().clear();
						if(schoolClass.getStudentIdList() != null) schoolClass.getStudentIdList().clear();
						adapter.notifyItemChanged(position);
					}
				});
			}


			@Override
			public void onAddTeacherClick(int position)
			{

			}


			@Override
			public void onRemoveStudentClick(int position)
			{

			}


			@Override
			public void onRemoveTeacherClick(int position)
			{

			}
		});
		loadApiData();
	}


	public void loadApiData()
	{
		Observable.zip(DbShowcaseAPIClient.getAPIService().classListDbFlow(),
				DbShowcaseAPIClient.getAPIService().teacherListDbFlow(),
				DbShowcaseAPIClient.getAPIService().studentListDbFlow(),
				(schoolClassEntities, teacherEntities, studentEntities) -> {
					DbHelper.saveDataToDb(schoolClassEntities, teacherEntities, studentEntities, () -> {
						List<SchoolClassInterface> list = DbHelper.getClassListDbFlow();
						Log.d(MainActivityViewModel.class.getSimpleName(), "onDataSavedToDb " + list.size());
						state.set(StatefulLayout.State.CONTENT);
						adapter.refill(list);
					});
					return null;
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
						state.set(StatefulLayout.State.OFFLINE);
						e.printStackTrace();
					}


					@Override
					public void onNext(Object o)
					{
						Log.d(MainActivityViewModel.class.getSimpleName(), "saving to db on next");
					}
				});
	}


	private StudentDbFlowEntity getRandomStudent()
	{
		StudentDbFlowEntity student = new StudentDbFlowEntity();
		student.setName(new RandomString(8).nextString());
		student.setBirthDate(new Date());
		return student;
	}

}

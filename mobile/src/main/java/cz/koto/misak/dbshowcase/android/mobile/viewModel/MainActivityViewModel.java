package cz.koto.misak.dbshowcase.android.mobile.viewModel;

import android.databinding.ObservableField;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cz.kinst.jakub.view.StatefulLayout;
import cz.kinst.jakub.viewmodelbinding.ViewModel;
import cz.koto.misak.dbshowcase.android.mobile.DbApplication;
import cz.koto.misak.dbshowcase.android.mobile.adapter.ClassRecyclerViewAdapter;
import cz.koto.misak.dbshowcase.android.mobile.databinding.ActivityMainBinding;
import cz.koto.misak.dbshowcase.android.mobile.db.dbflow.DbFlowCrudModule;
import cz.koto.misak.dbshowcase.android.mobile.db.realm.ShowcaseRealmConfigurationMarker;
import cz.koto.misak.dbshowcase.android.mobile.db.realm.ShowcaseRealmCrudModule;
import cz.koto.misak.dbshowcase.android.mobile.entity.entityinterface.SchoolClassInterface;
import cz.koto.misak.dbshowcase.android.mobile.listener.OnClassItemClickListener;
import cz.koto.misak.dbshowcase.android.mobile.rest.DbShowcaseAPIClient;
import io.realm.RealmConfiguration;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivityViewModel extends ViewModel<ActivityMainBinding>
{

    public MainActivityViewModel(){
        DbApplication.get().getDbComponent().inject(this);
    }

	@Inject
    ShowcaseRealmCrudModule realmLoadModule;

    @Inject
    @ShowcaseRealmConfigurationMarker
    RealmConfiguration showcaseRealmConfiguration;

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
        realmLoadModule.loadRealmFromApi(showcaseRealmConfiguration);
		loadDbFlowFromApi();
	}


	public void loadDbFlowFromApi()
	{
		Observable.zip(DbShowcaseAPIClient.getAPIService().classListDbFlow(),
				DbShowcaseAPIClient.getAPIService().teacherListDbFlow(),
				DbShowcaseAPIClient.getAPIService().studentListDbFlow(),
				(schoolClassEntities, teacherEntities, studentEntities) -> {
					DbFlowCrudModule.saveDataToDb(schoolClassEntities, teacherEntities, studentEntities, () -> {
						List<SchoolClassInterface> list = DbFlowCrudModule.getClassListDbFlow();
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

}

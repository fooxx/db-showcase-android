package cz.koto.misak.dbshowcase.android.mobile.ui.interaction;


import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.util.ArrayList;
import java.util.List;

import cz.koto.misak.dbshowcase.android.mobile.BR;
import cz.koto.misak.dbshowcase.android.mobile.R;
import cz.koto.misak.dbshowcase.android.mobile.databinding.ItemInteractionClassBinding;
import cz.koto.misak.dbshowcase.android.mobile.model.DataHandlerListener;
import cz.koto.misak.dbshowcase.android.mobile.model.ModelProvider;
import cz.koto.misak.dbshowcase.android.mobile.model.SchoolClassInterface;
import cz.koto.misak.dbshowcase.android.mobile.model.StudentInterface;
import cz.koto.misak.dbshowcase.android.mobile.model.TeacherInterface;
import io.reactivex.Observable;
import me.tatarka.bindingcollectionadapter2.OnItemBind;
import timber.log.Timber;

//import me.tatarka.bindingcollectionadapter.BaseItemViewSelector;
//import me.tatarka.bindingcollectionadapter.ItemView;
//import me.tatarka.bindingcollectionadapter.ItemViewSelector;


public class InteractionItemViewModel extends BaseObservable implements InteractionCard {

//	public final ItemViewSelector<StudentItemViewModel> studentItemView = new BaseItemViewSelector<StudentItemViewModel>() {
//		@Override
//		public void select(ItemView itemView, int position, StudentItemViewModel item) {
//			itemView.set(BR.studentViewModel, item.getPagerLayoutResource());
//		}
//	};

	public final OnItemBind<StudentItemViewModel> studentItemView = (itemBinding, position, item) -> itemBinding.set(BR.studentViewModel, item.getPagerLayoutResource());

//	public final ItemViewSelector<TeacherItemViewModel> teacherItemView = new BaseItemViewSelector<TeacherItemViewModel>() {
//		@Override
//		public void select(ItemView itemView, int position, TeacherItemViewModel item) {
//			itemView.set(BR.teacherViewModel, item.getPagerLayoutResource());
//		}
//	};

	public final OnItemBind<TeacherItemViewModel> teacherItemView = (itemBinding, position, item) -> itemBinding.set(BR.teacherViewModel, item.getPagerLayoutResource());



	public final ListScrollController listScrollController = new ListScrollController();
	Context modelContext;
	private SchoolClassInterface mSchoolModelItem;
	private ItemInteractionClassBinding classBinding;


	public InteractionItemViewModel(Context modelContext, SchoolClassInterface schoolModelItem) {
		mSchoolModelItem = schoolModelItem;
		//classBinding = DataBindingUtil.inflate(LayoutInflater.from(modelContext), R.layout.item_interaction_class, null, false);
		this.modelContext = modelContext;
	}


	public static InteractionCard getInstance(Context modelContext, SchoolClassInterface schoolModelItem) {
		return new InteractionItemViewModel(modelContext, schoolModelItem);
	}


	public int getPagerLayoutResource() {
		return R.layout.item_interaction_class;
	}


	public String getName() {
		if(mSchoolModelItem == null)
			return "";
		return mSchoolModelItem.getName();
	}


	public List<? extends StudentInterface> getStudentList() {
		return mSchoolModelItem.getStudentList();
	}


	@Bindable
	public List<StudentItemViewModel> getStudentViewModelList() {
		if(mSchoolModelItem == null)
			return new ArrayList<>();
		return Observable.fromIterable(getStudentList())
				.map(item -> new StudentItemViewModel(item))
				.toList().blockingGet();
	}


	public List<TeacherInterface> getTeacherList() {
		if(mSchoolModelItem == null)
			return new ArrayList<>();
		return mSchoolModelItem.getTeacherList();
	}


	@Bindable
	public List<TeacherItemViewModel> getTeacherViewModelList() {
		if(mSchoolModelItem == null)
			return new ArrayList<>();
		return Observable.fromIterable(getTeacherList())
				.map(item -> new TeacherItemViewModel(item))
				.toList().blockingGet();
	}


	public void addStudent() {
		if(mSchoolModelItem == null) return;
		ModelProvider.get().addRandomStudent(mSchoolModelItem, new DataHandlerListener() {
			@Override
			public void handleSuccess() {
				notifyPropertyChanged(BR.studentViewModelList);
				listScrollController.smoothScrollToEnd();
			}

			@Override
			public void handleFailed(Throwable throwable) {
				Timber.e(throwable);
			}
		});
	}


	public void addTeacher() {

	}

}

package cz.koto.misak.dbshowcase.android.mobile.ui.interaction;


import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cz.koto.misak.dbshowcase.android.mobile.BR;
import cz.koto.misak.dbshowcase.android.mobile.R;
import cz.koto.misak.dbshowcase.android.mobile.model.DataHandlerListener;
import cz.koto.misak.dbshowcase.android.mobile.model.ModelProvider;
import cz.koto.misak.dbshowcase.android.mobile.model.SchoolClassInterface;
import cz.koto.misak.dbshowcase.android.mobile.model.StudentInterface;
import cz.koto.misak.dbshowcase.android.mobile.model.TeacherInterface;
import cz.koto.misak.dbshowcase.android.mobile.utility.ContextProvider;
import io.reactivex.Observable;
import me.tatarka.bindingcollectionadapter.BaseItemViewSelector;
import me.tatarka.bindingcollectionadapter.ItemView;
import me.tatarka.bindingcollectionadapter.ItemViewSelector;


public class InteractionItemViewModel extends BaseObservable implements InteractionCard {

	public final ItemViewSelector<StudentItemViewModel> studentItemView = new BaseItemViewSelector<StudentItemViewModel>() {
		@Override
		public void select(ItemView itemView, int position, StudentItemViewModel item) {
			itemView.set(BR.studentViewModel, item.getPagerLayoutResource());
		}
	};
	public final ItemViewSelector<TeacherItemViewModel> teacherItemView = new BaseItemViewSelector<TeacherItemViewModel>() {
		@Override
		public void select(ItemView itemView, int position, TeacherItemViewModel item) {
			itemView.set(BR.teacherViewModel, item.getPagerLayoutResource());
		}
	};

	private SchoolClassInterface mSchoolModelItem;


	public InteractionItemViewModel(SchoolClassInterface schoolModelItem) {
		mSchoolModelItem = schoolModelItem;
	}


	public static InteractionCard getInstance(SchoolClassInterface schoolModelItem) {
		return new InteractionItemViewModel(schoolModelItem);
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
				Toast.makeText(ContextProvider.getContext(), "Success", Toast.LENGTH_SHORT);
				notifyPropertyChanged(BR.studentViewModelList);
			}


			@Override
			public void handleFailed(Throwable throwable) {
				Toast.makeText(ContextProvider.getContext(), throwable.getMessage(), Toast.LENGTH_LONG);
			}
		});
	}


	public void addTeacher() {

	}

}

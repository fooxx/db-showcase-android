package cz.koto.misak.dbshowcase.android.mobile.ui.interaction;


import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.util.ArrayList;
import java.util.List;

import cz.koto.misak.dbshowcase.android.mobile.BR;
import cz.koto.misak.dbshowcase.android.mobile.R;
import cz.koto.misak.dbshowcase.android.mobile.model.SchoolClassInterface;
import cz.koto.misak.dbshowcase.android.mobile.model.StudentInterface;
import cz.koto.misak.dbshowcase.android.mobile.model.TeacherInterface;
import io.reactivex.Observable;
import me.tatarka.bindingcollectionadapter.BaseItemViewSelector;
import me.tatarka.bindingcollectionadapter.ItemView;
import me.tatarka.bindingcollectionadapter.ItemViewSelector;


public class InteractionItemViewModel extends BaseObservable {

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


	public int getPagerLayoutResource() {
		return R.layout.item_interaction;
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

}

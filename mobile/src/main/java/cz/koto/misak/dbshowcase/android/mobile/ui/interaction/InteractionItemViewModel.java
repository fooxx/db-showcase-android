package cz.koto.misak.dbshowcase.android.mobile.ui.interaction;


import java.util.ArrayList;
import java.util.List;

import cz.koto.misak.dbshowcase.android.mobile.BR;
import cz.koto.misak.dbshowcase.android.mobile.R;
import cz.koto.misak.dbshowcase.android.mobile.model.SchoolClassInterface;
import cz.koto.misak.dbshowcase.android.mobile.model.StudentInterface;
import cz.koto.misak.dbshowcase.android.mobile.model.TeacherInterface;
import me.tatarka.bindingcollectionadapter.ItemView;


public class InteractionItemViewModel {

	public final ItemView studentItemView = ItemView.of(BR.studentViewModel, R.layout.item_interaction_student);
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


	public List<StudentInterface> getStudentList() {
		if(mSchoolModelItem == null)
			return new ArrayList<>();
		return mSchoolModelItem.getStudentList();
	}


	public List<TeacherInterface> getTeacherList() {
		if(mSchoolModelItem == null)
			return new ArrayList<>();
		return mSchoolModelItem.getTeacherList();
	}
}

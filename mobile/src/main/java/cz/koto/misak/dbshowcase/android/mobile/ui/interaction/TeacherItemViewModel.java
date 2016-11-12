package cz.koto.misak.dbshowcase.android.mobile.ui.interaction;


import cz.koto.misak.dbshowcase.android.mobile.R;
import cz.koto.misak.dbshowcase.android.mobile.model.TeacherInterface;


public class TeacherItemViewModel {

	private TeacherInterface mTeacher;


	public TeacherItemViewModel(TeacherInterface student) {
		mTeacher = student;
	}


	public int getPagerLayoutResource() {
		return R.layout.item_interaction_teacher;
	}


	public String getName() {
		if(mTeacher == null)
			return "";
		return mTeacher.getName();
	}

}

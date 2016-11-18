package cz.koto.misak.dbshowcase.android.mobile.ui.interaction;


import cz.koto.misak.dbshowcase.android.mobile.R;
import cz.koto.misak.dbshowcase.android.mobile.model.StudentInterface;


public class StudentItemViewModel {

	private StudentInterface mStudent;


	public StudentItemViewModel(StudentInterface student) {
		mStudent = student;
	}


	public int getPagerLayoutResource() {
		return R.layout.item_interaction_student;
	}


	public String getName() {
		if(mStudent == null)
			return "";
		return mStudent.getName();
	}

}

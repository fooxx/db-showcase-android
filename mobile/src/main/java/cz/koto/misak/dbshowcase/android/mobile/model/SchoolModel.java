package cz.koto.misak.dbshowcase.android.mobile.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import timber.log.Timber;


public class SchoolModel {

	private List<SchoolClassInterface> schoolItems = new ArrayList<>();


	public final List<SchoolClassInterface> getSchoolItems() {
		return schoolItems;
	}


	public void setSchoolItems(List<? extends SchoolClassInterface> list) {
		if((list == null) || (list.size() == 0)) return;
		this.schoolItems.clear();
		this.schoolItems.addAll(list);
	}


	public final List<? extends SchoolClassInterface> getSchoolItemsSrc() {
		return schoolItems;
	}


	public final List<? super SchoolClassInterface> getSchoolItemsDest() {
		return schoolItems;
	}


	public void addSchoolItems(List<? extends SchoolClassInterface> list) {
		if((list == null) || (list.size() == 0)) return;
		this.schoolItems.addAll(list);
	}


	public int getSchoolClassCount() {
		return schoolItems.size();
	}


	public int getTeachersCount() {
		Set<Long> teacherIdSet = new HashSet<>();
		for(SchoolClassInterface school : schoolItems) {
			for(Object teacher : school.getTeacherList()) {
				teacherIdSet.add(((TeacherInterface) teacher).getId());
			}
		}
		Timber.d("TeacherIdSet %s", teacherIdSet);
		return teacherIdSet.size();
	}


	public int getStudentsCount() {
		Set<Long> studentIdSet = new HashSet<>();
		for(SchoolClassInterface school : schoolItems) {
			for(Object student : school.getStudentList()) {
				studentIdSet.add(((StudentInterface) student).getId());
			}
		}
		Timber.d("StudentIdSet %s", studentIdSet);
		return studentIdSet.size();
	}
}

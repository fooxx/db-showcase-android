package cz.koto.misak.kotipoint.android.mobile.entity.entityinterface;

import java.util.List;

public interface SchoolClassInterface<S extends StudentInterface, T extends TeacherInterface>
{

	long getClassId();
	void setClassId(long id);

	String getClassName();
	void setClassName(String name);

	int getClassGrade();
	void setClassGrade(int grade);

	List<S> getStudentList();
	void setStudentList(List<S> list);

	List<T> getTeacherList();
	void setTeacherList(List<T> list);

}

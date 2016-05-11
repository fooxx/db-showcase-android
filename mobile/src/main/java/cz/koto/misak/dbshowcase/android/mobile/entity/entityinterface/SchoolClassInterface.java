package cz.koto.misak.dbshowcase.android.mobile.entity.entityinterface;

import java.util.List;

public interface SchoolClassInterface<S extends StudentInterface, T extends TeacherInterface>
{

	long getId();
	void setId(long id);

	String getName();
	void setName(String name);

	int getGrade();
	void setGrade(int grade);

	List<Long> getStudentIdList();
	void setStudentIdList(List<Long> list);

	List<Long> getTeacherIdList();
	void setTeacherIdList(List<Long> list);

	List<S> getStudentList();
	void setStudentList(List<S> list);

	List<T> getTeacherList();
	void setTeacherList(List<T> list);

}

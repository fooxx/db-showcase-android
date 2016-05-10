package cz.koto.misak.kotipoint.android.mobile.entity.entityinterface;

import java.util.List;

public interface TeacherInterface<S extends SchoolClassInterface>
{

	long getTeacherId();
	void setTeacherId(long id);

	String getTeacherName();
	void setTeacherName(String name);

	List<S> getSchoolClassList();
	void setSchoolClassList(List<S> schoolClassList);

}

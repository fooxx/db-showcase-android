package cz.koto.misak.dbshowcase.android.mobile.entity.entityinterface;

import java.util.List;

public interface TeacherInterface<S extends SchoolClassInterface>
{

	long getId();
	void setId(long id);

	String getName();
	void setName(String name);

	List<S> getSchoolClassList();
	void setSchoolClassList(List<S> schoolClassList);

}

package cz.koto.misak.dbshowcase.android.mobile.entity.entityinterface;

import java.util.Date;


public interface StudentInterface<T extends SchoolClassInterface>
{

	long getId();
	void setId(long id);

	String getName();
	void setName(String name);

	Date getBirthDate();
	void setBirthDate(Date birthDate);

	T getSchoolClass();
	void setSchoolClass(T schoolClass);

}

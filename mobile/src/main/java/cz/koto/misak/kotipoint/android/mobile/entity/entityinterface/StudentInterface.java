package cz.koto.misak.kotipoint.android.mobile.entity.entityinterface;

import java.util.Date;


public interface StudentInterface<T extends SchoolClassInterface>
{

	long getStudentId();
	void setStudentId(long id);

	String getStudentName();
	void setStudentName(String name);

	Date getStudentBirthDate();
	void setStudentBirthDate(Date birthDate);

	T getSchoolClass();
	void setSchoolClass(T schoolClass);

}

package cz.koto.misak.dbshowcase.android.mobile.model;

import java.util.List;


/**
 * public static <T> void copy(List<? super T> dest,List<? extends T> src)
 *
 * @param <S>
 * @param <T>
 */
public interface SchoolClassInterface<S extends StudentInterface, T extends TeacherInterface>
{

	long getId();
	void setId(long id);

	String getName();
	void setName(String name);

	int getGrade();
	void setGrade(int grade);

	List<Long> getStudentIdList();

	List<Long> getTeacherIdList();

	List<S> getStudentList();

	List<T> getTeacherList();

	String getTeacherListString();
	String getStudentListString();

}

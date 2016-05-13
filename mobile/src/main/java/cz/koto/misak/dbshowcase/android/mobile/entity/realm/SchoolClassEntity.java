package cz.koto.misak.dbshowcase.android.mobile.entity.realm;


import com.google.gson.annotations.SerializedName;

import cz.koto.misak.dbshowcase.android.mobile.entity.realm.RealmLong;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
public class SchoolClassEntity implements /*SchoolClassInterface<StudentEntity, TeacherEntity>,*/ RealmModel
{

	@io.realm.annotations.PrimaryKey
	@SerializedName("id")
	private long id;

	@SerializedName("name")
	private String name;

	@SerializedName("grade")
	private int grade;

	@SerializedName("studentIdList")
	RealmList<RealmLong> studentIdList;

	@SerializedName("teacherIdList")
	RealmList<RealmLong> teacherIdList;

//	List<StudentEntity> studentList;
//
//	List<TeacherEntity> teacherList;


	//@Override
	public long getId()
	{
		return this.id;
	}


	//@Override
	public void setId(long id)
	{
		this.id = id;
	}


	//@Override
	public String getName()
	{
		return this.name;
	}


	//@Override
	public void setName(String name)
	{
		this.name = name;
	}


	//@Override
	public int getGrade()
	{
		return this.grade;
	}


	//@Override
	public void setGrade(int grade)
	{
		this.grade = grade;
	}


//	@Override
//	public List<StudentEntity> getStudentList()
//	{
//		return this.studentList;
//	}


//	@Override
//	public void setStudentList(List<StudentEntity> list)
//	{
//		this.studentList = list;
//	}
//
//
//	@Override
//	public List<TeacherEntity> getTeacherList()
//	{
//		return this.teacherList;
//	}
//
//
//	@Override
//	public void setTeacherList(List<TeacherEntity> list)
//	{
//		this.teacherList = list;
//	}


	//@Override
	public String getTeacherListString()
	{
		return null;
	}


	//@Override
	public String getStudentListString()
	{
		return null;
	}


	//@Override
	public RealmList<RealmLong> getStudentIdList()
	{
		return studentIdList;
	}


	//@Override
	public void setStudentIdList(RealmList<RealmLong> studentIdList)
	{
		this.studentIdList = studentIdList;
	}


	//@Override
	public RealmList<RealmLong> getTeacherIdList()
	{
		return teacherIdList;
	}


	//@Override
	public void setTeacherIdList(RealmList<RealmLong> teacherIdList)
	{
		this.teacherIdList = teacherIdList;
	}


	@Override
	public String toString()
	{
		return "SchoolClassEntity{" +
				"id=" + id +
				", name='" + name + '\'' +
				", grade=" + grade +
				", studentIdList=" + studentIdList +
				", teacherIdList=" + teacherIdList +
//				", studentList=" + studentList +
//				", teacherList=" + teacherList +
				'}';
	}
}

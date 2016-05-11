package cz.koto.misak.dbshowcase.android.mobile.entity.rest;


import com.google.gson.annotations.SerializedName;

import java.util.List;

import cz.koto.misak.dbshowcase.android.mobile.entity.entityinterface.SchoolClassInterface;


public class SchoolClassEntity implements SchoolClassInterface<StudentEntity, TeacherEntity>
{

	@SerializedName("id")
	private long id;

	@SerializedName("name")
	private String name;

	@SerializedName("grade")
	private int grade;

	@SerializedName("studentIdList")
	List<Long> studentIdList;

	@SerializedName("teacherIdList")
	List<Long> teacherIdList;

	List<StudentEntity> studentList;

	List<TeacherEntity> teacherList;


	@Override
	public long getId()
	{
		return this.id;
	}


	@Override
	public void setId(long id)
	{
		this.id = id;
	}


	@Override
	public String getName()
	{
		return this.name;
	}


	@Override
	public void setName(String name)
	{
		this.name = name;
	}


	@Override
	public int getGrade()
	{
		return this.grade;
	}


	@Override
	public void setGrade(int grade)
	{
		this.grade = grade;
	}


	@Override
	public List<StudentEntity> getStudentList()
	{
		return this.studentList;
	}


	@Override
	public void setStudentList(List<StudentEntity> list)
	{
		this.studentList = list;
	}


	@Override
	public List<TeacherEntity> getTeacherList()
	{
		return this.teacherList;
	}


	@Override
	public void setTeacherList(List<TeacherEntity> list)
	{
		this.teacherList = list;
	}


	@Override
	public List<Long> getStudentIdList()
	{
		return studentIdList;
	}


	@Override
	public void setStudentIdList(List<Long> studentIdList)
	{
		this.studentIdList = studentIdList;
	}


	@Override
	public List<Long> getTeacherIdList()
	{
		return teacherIdList;
	}


	@Override
	public void setTeacherIdList(List<Long> teacherIdList)
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
				", studentList=" + studentList +
				", teacherList=" + teacherList +
				'}';
	}
}

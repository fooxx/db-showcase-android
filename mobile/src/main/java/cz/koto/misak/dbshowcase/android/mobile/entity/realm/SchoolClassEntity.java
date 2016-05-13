package cz.koto.misak.dbshowcase.android.mobile.entity.realm;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import cz.koto.misak.dbshowcase.android.mobile.entity.entityinterface.SchoolClassInterface;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
public class SchoolClassEntity implements SchoolClassInterface<StudentEntity, TeacherEntity>, RealmModel
{

	@io.realm.annotations.PrimaryKey
	@SerializedName("id")
	private long id;

	@SerializedName("name")
	private String name;

	@SerializedName("grade")
	private int grade;

	@SerializedName("studentIdList")
	RealmList<RealmLong> studentIdRealmList;

	@SerializedName("teacherIdList")
	RealmList<RealmLong> teacherIdRealmList;

	RealmList<StudentEntity> studentRealmList;

	RealmList<TeacherEntity> teacherRealmList;


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

	public RealmList<StudentEntity> getStudentRealmList()
	{
		return this.studentRealmList;
	}


	public void setStudentList(RealmList<StudentEntity> realmList)
	{
		this.studentRealmList = realmList;
	}


	public RealmList<TeacherEntity> getTeacherRealmList()
	{
		return this.teacherRealmList;
	}


	public void setTeacherRealmList(RealmList<TeacherEntity> realmList)
	{
		this.teacherRealmList = realmList;
	}


	@Override
	public String getTeacherListString()
	{
        StringBuilder builder = new StringBuilder();
        getTeacherList();
        for(TeacherEntity t: teacherRealmList) {
            builder.append(t.getName());
            builder.append("\n");
        }
        return builder.toString();
	}


	@Override
	public String getStudentListString()
	{
        StringBuilder builder = new StringBuilder();
        getTeacherList();
        for(StudentEntity t: studentRealmList) {
            builder.append(t.getName());
            builder.append("\n");
        }
        return builder.toString();
	}


	public RealmList<RealmLong> getStudentIdRealmList()
	{
		return studentIdRealmList;
	}


	public void setStudentIdRealmList(RealmList<RealmLong> studentIdList)
	{
		this.studentIdRealmList = studentIdList;
	}

    public RealmList<RealmLong> getTeacherIdRealmList()
    {
        return teacherIdRealmList;
    }



	public void setTeacherIdRealmList(RealmList<RealmLong> teacherIdList)
	{
		this.teacherIdRealmList = teacherIdList;
	}


    @Override
    public List<Long> getStudentIdList() {
        List<Long> ret = new ArrayList<>();
        //TODO try to replace with collect all
        for (RealmLong realmLong:this.studentIdRealmList){
            ret.add(realmLong.getLongValue());
        }
        return ret;
    }

    @Override
    public List<Long> getTeacherIdList() {
        List<Long> ret = new ArrayList<>();
        //TODO try to replace with collect all
        for (RealmLong realmLong:this.teacherIdRealmList){
            ret.add(realmLong.getLongValue());
        }
        return ret;
    }

    @Override
    public List<StudentEntity> getStudentList() {
        return this.studentRealmList;
    }

    @Override
    public List<TeacherEntity> getTeacherList() {
        return this.teacherRealmList;
    }


    @Override
	public String toString()
	{
		return "SchoolClassEntity{" +
				"id=" + id +
				", name='" + name + '\'' +
				", grade=" + grade +
				", studentIdRealmList=" + studentIdRealmList +
				", teacherIdRealmList=" + teacherIdRealmList +
//				", studentList=" + studentList +
//				", teacherList=" + teacherList +
				'}';
	}
}

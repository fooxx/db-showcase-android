package cz.koto.misak.dbshowcase.android.mobile.entity.dbflow;

import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ManyToMany;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;

import cz.koto.misak.dbshowcase.android.mobile.db.dbflow.DbFlowDatabase;
import cz.koto.misak.dbshowcase.android.mobile.entity.entityinterface.SchoolClassInterface;


@Table(database = DbFlowDatabase.class)
@ManyToMany(referencedTable = TeacherDbFlowEntity.class)
public class SchoolClassDbFlowEntity extends BaseDbFlowModel implements SchoolClassInterface<StudentDbFlowEntity, TeacherDbFlowEntity>
{
	@Column
	@PrimaryKey(autoincrement = true)
	@SerializedName("id")
	long id;
	@Column
	@SerializedName("name")
	String name;
	@Column
	@SerializedName("grade")
	int grade;
	@SerializedName("studentIdList")
	List<Long> studentIdList;
	@SerializedName("teacherIdList")
	List<Long> teacherIdList;
	List<StudentDbFlowEntity> studentList;
	List<TeacherDbFlowEntity> teacherList;


	@Bindable
	@Override
	public long getId()
	{
		return id;
	}


	@Override
	public void setId(long id)
	{
		this.id = id;
		notifyPropertyChanged(BR.id);
	}


	@Bindable
	@Override
	public String getName()
	{
		return name;
	}


	@Override
	public void setName(String name)
	{
		this.name = name;
		notifyPropertyChanged(BR.name);
	}


	@Bindable
	@Override
	public int getGrade()
	{
		return grade;
	}


	@Override
	public void setGrade(int grade)
	{
		this.grade = grade;
		notifyPropertyChanged(BR.grade);
	}


	@Override
	public List<Long> getStudentIdList()
	{
		return studentIdList;
	}


	@Override
	public void setStudentIdList(List<Long> list)
	{
		studentIdList = list;
	}


	@Override
	public List<Long> getTeacherIdList()
	{
		return teacherIdList;
	}


	@Override
	public void setTeacherIdList(List<Long> list)
	{
		teacherIdList = list;
	}


	@Bindable
	@OneToMany(methods = {OneToMany.Method.ALL}, variableName = "studentList")
	@Override
	public List<StudentDbFlowEntity> getStudentList()
	{
		if(studentList == null || studentList.isEmpty())
		{
			studentList = new Select()
					.from(StudentDbFlowEntity.class)
					.where(StudentDbFlowEntity_Table.schoolClass_id.is(id))
					.queryList();
		}
		return studentList;
	}


	@Override
	public void setStudentList(List<StudentDbFlowEntity> list)
	{
		studentList = list;
		notifyPropertyChanged(BR.studentList);
	}


	@Bindable
	@Override
	public List<TeacherDbFlowEntity> getTeacherList()
	{
		if(teacherList == null || teacherList.isEmpty())
		{
			teacherList = new Select()
					.from(TeacherDbFlowEntity.class)
					.innerJoin(SchoolClassDbFlowEntity_TeacherDbFlowEntity.class)
					.on(SchoolClassDbFlowEntity_Table.id.eq(SchoolClassDbFlowEntity_TeacherDbFlowEntity_Table.schoolClassDbFlowEntity_id))
					.where(SchoolClassDbFlowEntity_TeacherDbFlowEntity_Table.schoolClassDbFlowEntity_id.eq(id))
					.queryList();
		}
		return teacherList;
	}


	@Override
	public void setTeacherList(List<TeacherDbFlowEntity> list)
	{
		teacherList = list;
		notifyPropertyChanged(BR.teacherList);
	}


	@Override
	public String getTeacherListString()
	{
		StringBuilder builder = new StringBuilder();
		getTeacherList();
		for(TeacherDbFlowEntity t: teacherList) {
			builder.append(t.getName());
			builder.append("\n");
		}
		return builder.toString();
	}


	@Override
	public String getStudentListString()
	{
		StringBuilder builder = new StringBuilder();
		getStudentList();
		for(StudentDbFlowEntity s: studentList) {
			builder.append(s.getName());
			builder.append("\n");
		}
		return builder.toString();
	}
}

package cz.koto.misak.dbshowcase.android.mobile.persistence.dbflow.model;

import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ManyToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;

import cz.koto.misak.dbshowcase.android.mobile.model.SchoolClassInterface;
import cz.koto.misak.dbshowcase.android.mobile.model.dbflow.SchoolClassDbFlowEntity_TeacherDbFlowEntity;
import cz.koto.misak.dbshowcase.android.mobile.model.dbflow.SchoolClassDbFlowEntity_TeacherDbFlowEntity_Table;
import cz.koto.misak.dbshowcase.android.mobile.model.dbflow.StudentDbFlowEntity_Table;
import cz.koto.misak.dbshowcase.android.mobile.model.dbflow.TeacherDbFlowEntity_Table;
import cz.koto.misak.dbshowcase.android.mobile.persistence.dbflow.DbFlowDatabase;


@Table(database = DbFlowDatabase.class)
@ManyToMany(referencedTable = TeacherDbFlowEntity.class)
public class SchoolClassDbFlowEntity extends BaseDbFlowModel implements SchoolClassInterface<StudentDbFlowEntity, TeacherDbFlowEntity>
{
	@Column
	@PrimaryKey(autoincrement = true)
	long dbId;
	@Column
	@SerializedName("id")
	long serverId;
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
		return dbId;
	}


	@Override
	public void setId(long id)
	{
		this.dbId = id;
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


	public void setStudentIdList(List<Long> list)
	{
		studentIdList = list;
	}


	@Override
	public List<Long> getTeacherIdList()
	{
		return teacherIdList;
	}


	public void setTeacherIdList(List<Long> list)
	{
		teacherIdList = list;
	}


	@Bindable
//	@OneToMany(methods = {OneToMany.Method.ALL}, variableName = "studentList")
	@Override
	public List<StudentDbFlowEntity> getStudentList()
	{
		if(studentList == null || studentList.isEmpty())
		{
			studentList = new Select()
					.from(StudentDbFlowEntity.class)
					.where(StudentDbFlowEntity_Table.schoolClass_dbId.is(dbId))
					.queryList();
		}
		return studentList;
	}


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
					.on(TeacherDbFlowEntity_Table.dbId.eq(SchoolClassDbFlowEntity_TeacherDbFlowEntity_Table.teacherDbFlowEntity_dbId))
					.where(SchoolClassDbFlowEntity_TeacherDbFlowEntity_Table.schoolClassDbFlowEntity_dbId.eq(dbId))
					.queryList();
		}
		return teacherList;
	}


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
		for(TeacherDbFlowEntity t : teacherList)
		{
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
		for(StudentDbFlowEntity s : studentList)
		{
			builder.append(s.getName());
			builder.append("\n");
		}
		return builder.toString();
	}


	public long getServerId() {
		return serverId;
	}


	public void setServerId(long serverId) {
		this.serverId = serverId;
	}


	public void update(SchoolClassDbFlowEntity entity) {
		serverId = entity.getServerId();
		name = entity.getName();
		grade = entity.getGrade();
	}
}

package cz.koto.misak.kotipoint.android.mobile.entity.dbflow;

import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ManyToMany;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import cz.koto.misak.kotipoint.android.mobile.db.dbflow.DbFlowDatabase;
import cz.koto.misak.kotipoint.android.mobile.entity.entityinterface.SchoolClassInterface;

@Table(database = DbFlowDatabase.class)
@ManyToMany(referencedTable = TeacherDbFlowEntity.class)
public class SchoolClassDbFlowEntity extends BaseDbFlowModel implements SchoolClassInterface<StudentDbFlowEntity, TeacherDbFlowEntity>
{
	@Column
	@PrimaryKey(autoincrement = true)
	@SerializedName("id")
	private long id;
	@Column
	@SerializedName("name")
	private String name;
	@Column
	@SerializedName("grade")
	private int grade;
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


	@Bindable
	@OneToMany(methods = {OneToMany.Method.ALL}, variableName = "studentList")
	@Override
	public List<StudentDbFlowEntity> getStudentList()
	{
//		if (studentList == null || studentList.isEmpty()) {
//			studentList = SQLite.select()
//					.from(StudentDbFlowEntity.class)
//					.where(StudentDbFlowEntity_Table.id.eq(id))
//					.queryList();
//		}
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
//		if (teacherList == null || teacherList.isEmpty()) {
//			teacherList = SQLite.select()
//					.from(TeacherDbFlowEntity.class)
//					.where(TeacherDbFlowEntity_Table.id.eq(id))
//					.queryList();
//		}
		return teacherList;
	}


	@Override
	public void setTeacherList(List<TeacherDbFlowEntity> list)
	{
		teacherList = list;
		notifyPropertyChanged(BR.teacherList);
	}
}

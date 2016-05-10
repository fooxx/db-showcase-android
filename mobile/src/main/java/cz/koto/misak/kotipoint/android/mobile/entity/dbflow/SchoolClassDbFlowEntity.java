package cz.koto.misak.kotipoint.android.mobile.entity.dbflow;

import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import cz.koto.misak.kotipoint.android.mobile.entity.entityinterface.SchoolClassInterface;


public class SchoolClassDbFlowEntity extends BaseDbFlowModel implements SchoolClassInterface<StudentDbFlowEntity, TeacherDbFlowEntity>
{

	@SerializedName("id")
	private long mId;
	@SerializedName("name")
	private String mName;
	@SerializedName("grade")
	private int mGrade;
	private List<StudentDbFlowEntity> mStudentList;
	private List<TeacherDbFlowEntity> mTeacherList;


	@Bindable
	@Override
	public long getClassId()
	{
		return mId;
	}


	@Override
	public void setClassId(long id)
	{
		mId = id;
		notifyPropertyChanged(BR.classId);
	}


	@Bindable
	@Override
	public String getClassName()
	{
		return mName;
	}


	@Override
	public void setClassName(String name)
	{
		mName = name;
		notifyPropertyChanged(BR.className);
	}


	@Bindable
	@Override
	public int getClassGrade()
	{
		return mGrade;
	}


	@Override
	public void setClassGrade(int grade)
	{
		mGrade = grade;
		notifyPropertyChanged(BR.classGrade);
	}


	@Bindable
	@Override
	public List<StudentDbFlowEntity> getStudentList()
	{
		return mStudentList;
	}


	@Override
	public void setStudentList(List<StudentDbFlowEntity> list)
	{
		mStudentList = list;
		notifyPropertyChanged(BR.studentList);
	}


	@Bindable
	@Override
	public List<TeacherDbFlowEntity> getTeacherList()
	{
		return mTeacherList;
	}


	@Override
	public void setTeacherList(List<TeacherDbFlowEntity> list)
	{
		mTeacherList = list;
		notifyPropertyChanged(BR.teacherList);
	}
}

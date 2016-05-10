package cz.koto.misak.kotipoint.android.mobile.entity.dbflow;

import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import cz.koto.misak.kotipoint.android.mobile.entity.entityinterface.TeacherInterface;


public class TeacherDbFlowEntity extends BaseDbFlowModel implements TeacherInterface
{

	@SerializedName("id")
	private long mId;
	@SerializedName("name")
	private String mName;
	private List<SchoolClassDbFlowEntity> mSchoolClassList;


	@Bindable
	@Override
	public long getTeacherId()
	{
		return mId;
	}


	@Override
	public void setTeacherId(long id)
	{
		mId = id;
		notifyPropertyChanged(BR.teacherId);
	}


	@Bindable
	@Override
	public String getTeacherName()
	{
		return mName;
	}


	@Override
	public void setTeacherName(String name)
	{
		mName = name;
		notifyPropertyChanged(BR.teacherName);
	}


	@Bindable
	@Override
	public List getSchoolClassList()
	{
		return mSchoolClassList;
	}


	@Override
	public void setSchoolClassList(List schoolClassList)
	{
		mSchoolClassList = schoolClassList;
		notifyPropertyChanged(BR.schoolClassList);
	}
}

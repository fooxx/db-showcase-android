package cz.koto.misak.kotipoint.android.mobile.entity.dbflow;

import android.databinding.Bindable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import cz.koto.misak.kotipoint.android.mobile.BR;
import cz.koto.misak.kotipoint.android.mobile.entity.entityinterface.StudentInterface;


public class StudentDbFlowEntity extends BaseDbFlowModel implements StudentInterface<SchoolClassDbFlowEntity>
{

	@SerializedName("id")
	private long mId;
	@SerializedName("name")
	private String mName;
	@SerializedName("birthDate")
	private Date mBirthDate;
	@SerializedName("schoolClass")
	private SchoolClassDbFlowEntity mSchoolClass;


	@Bindable
	@Override
	public long getStudentId()
	{
		return mId;
	}


	@Override
	public void setStudentId(long id)
	{
		mId = id;
		notifyPropertyChanged(BR.studentId);
	}


	@Bindable
	@Override
	public String getStudentName()
	{
		return mName;
	}


	@Override
	public void setStudentName(String name)
	{
		mName = name;
		notifyPropertyChanged(BR.studentName);
	}


	@Bindable
	@Override
	public Date getStudentBirthDate()
	{
		return mBirthDate;
	}


	@Override
	public void setStudentBirthDate(Date birthDate)
	{
		mBirthDate = birthDate;
		notifyPropertyChanged(BR.studentBirthDate);
	}


	@Bindable
	@Override
	public SchoolClassDbFlowEntity getSchoolClass()
	{
		return mSchoolClass;
	}


	@Override
	public void setSchoolClass(SchoolClassDbFlowEntity schoolClass)
	{
		mSchoolClass = schoolClass;
		notifyPropertyChanged(BR.schoolClass);
	}
}

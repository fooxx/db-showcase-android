package cz.koto.misak.dbshowcase.android.mobile.persistence.dbflow.model;

import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.Date;

import cz.koto.misak.dbshowcase.android.mobile.model.StudentInterface;
import cz.koto.misak.dbshowcase.android.mobile.persistence.dbflow.DbFlowDatabase;

@Table(database = DbFlowDatabase.class)
public class StudentDbFlowEntity extends BaseDbFlowModel implements StudentInterface<SchoolClassDbFlowEntity>
{

	@PrimaryKey(autoincrement = true)
	@Column
	long dbId;
	@Column
	@SerializedName("id")
	long serverId;
	@Column
	@SerializedName("name")
	String name;
	@Column
	@SerializedName("birthDate")
	Date birthDate;
	@SerializedName("schoolClassId")
	long schoolClassId;
	@Column
	@ForeignKey
	SchoolClassDbFlowEntity schoolClass;


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
	public Date getBirthDate()
	{
		return birthDate;
	}


	@Override
	public void setBirthDate(Date birthDate)
	{
		this.birthDate = birthDate;
		notifyPropertyChanged(BR.birthDate);
	}


	@Bindable
	@Override
	public long getSchoolClassId()
	{
		return schoolClassId;
	}


	@Override
	public void setSchoolClassId(long id)
	{
		schoolClassId = id;
		notifyPropertyChanged(BR.schoolClassId);
	}


	@Bindable
	@Override
	public SchoolClassDbFlowEntity getSchoolClass()
	{
		if(schoolClass == null) {
			new Select()
					.from(SchoolClassDbFlowEntity.class)
					.where(SchoolClassDbFlowEntity_Table.dbId.eq(schoolClassId))
					.querySingle();
		}
		return schoolClass;
	}


	@Override
	public void setSchoolClass(SchoolClassDbFlowEntity schoolClass)
	{
		this.schoolClass = schoolClass;
		notifyPropertyChanged(BR.schoolClass);
	}


	public long getServerId() {
		return serverId;
	}


	public void setServerId(long serverId) {
		this.serverId = serverId;
	}


	public void update(StudentDbFlowEntity entity) {
		serverId = entity.getServerId();
		name = entity.getName();
		birthDate = entity.getBirthDate();
		schoolClassId = entity.getSchoolClassId();
	}
}

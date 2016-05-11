package cz.koto.misak.dbshowcase.android.mobile.entity.dbflow;

import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.Date;

import cz.koto.misak.dbshowcase.android.mobile.db.dbflow.DbFlowDatabase;
import cz.koto.misak.dbshowcase.android.mobile.entity.entityinterface.StudentInterface;

@Table(database = DbFlowDatabase.class)
public class StudentDbFlowEntity extends BaseDbFlowModel implements StudentInterface<SchoolClassDbFlowEntity>
{

	@PrimaryKey(autoincrement = true)
	@Column
	@SerializedName("id")
	protected long id;
	@Column
	@SerializedName("name")
	protected String name;
	@Column
	@SerializedName("birthDate")
	protected Date birthDate;
	@Column
	@ForeignKey
	@SerializedName("schoolClass")
	protected SchoolClassDbFlowEntity schoolClass;


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
	public SchoolClassDbFlowEntity getSchoolClass()
	{
		if(schoolClass == null) {
			new Select()
					.from(SchoolClassDbFlowEntity.class)
					.where(SchoolClassDbFlowEntity_Table.id.eq(id))
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
}

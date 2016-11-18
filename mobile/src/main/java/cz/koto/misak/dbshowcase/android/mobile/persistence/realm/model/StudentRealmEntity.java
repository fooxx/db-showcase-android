package cz.koto.misak.dbshowcase.android.mobile.persistence.realm.model;


import com.google.gson.annotations.SerializedName;

import java.util.Date;

import cz.koto.misak.dbshowcase.android.mobile.model.StudentInterface;
import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
public class StudentRealmEntity implements StudentInterface<SchoolClassRealmEntity>, RealmModel
{

	@io.realm.annotations.PrimaryKey
	@SerializedName("id")
	protected long id;

	@SerializedName("name")
	protected String name;

	@SerializedName("birthDate")
	protected Date birthDate;

	@SerializedName("schoolClassId")
	protected long schoolClassId;

	@SerializedName(value = "schoolClass")
	protected SchoolClassRealmEntity schoolClass;


	@Override
	public long getId()
	{
		return id;
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
	public Date getBirthDate()
	{
		return this.birthDate;
	}


	@Override
	public void setBirthDate(Date birthDate)
	{
		this.birthDate = birthDate;
	}


	@Override
	public long getSchoolClassId()
	{
		return schoolClassId;
	}


	@Override
	public void setSchoolClassId(long id)
	{
		schoolClassId = id;
	}


	@Override
	public SchoolClassRealmEntity getSchoolClass()
	{
		return this.schoolClass;
	}


	@Override
	public void setSchoolClass(SchoolClassRealmEntity schoolClass)
	{
		this.schoolClass = schoolClass;
	}


	@Override
	public String toString()
	{
		return "StudentRealmEntity{" +
				"id=" + id +
				", name='" + name + '\'' +
				", birthDate=" + birthDate +
				", schoolClassId=" + schoolClassId +
				", schoolClass=" + schoolClass +
				'}';
	}
}

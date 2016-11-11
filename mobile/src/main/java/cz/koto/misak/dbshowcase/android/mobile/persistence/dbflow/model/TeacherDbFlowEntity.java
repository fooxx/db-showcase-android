package cz.koto.misak.dbshowcase.android.mobile.persistence.dbflow.model;

import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;

import cz.koto.misak.dbshowcase.android.mobile.model.TeacherInterface;
import cz.koto.misak.dbshowcase.android.mobile.model.dbflow.SchoolClassDbFlowEntity_TeacherDbFlowEntity;
import cz.koto.misak.dbshowcase.android.mobile.model.dbflow.SchoolClassDbFlowEntity_TeacherDbFlowEntity_Table;
import cz.koto.misak.dbshowcase.android.mobile.model.dbflow.TeacherDbFlowEntity_Table;
import cz.koto.misak.dbshowcase.android.mobile.persistence.dbflow.DbFlowDatabase;


@Table(database = DbFlowDatabase.class)
public class TeacherDbFlowEntity extends BaseDbFlowModel implements TeacherInterface<SchoolClassDbFlowEntity>
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
	List<SchoolClassDbFlowEntity> schoolClassList;



	@Bindable
	public long getId()
	{
		return dbId;
	}


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
	public List<SchoolClassDbFlowEntity> getSchoolClassList()
	{
		if (schoolClassList == null || schoolClassList.isEmpty()) {
			schoolClassList = new Select()
					.from(SchoolClassDbFlowEntity.class)
					.innerJoin(SchoolClassDbFlowEntity_TeacherDbFlowEntity.class)
					.on(TeacherDbFlowEntity_Table.dbId.eq(SchoolClassDbFlowEntity_TeacherDbFlowEntity_Table.teacherDbFlowEntity_dbId))
					.where(SchoolClassDbFlowEntity_TeacherDbFlowEntity_Table.teacherDbFlowEntity_dbId.eq(dbId))
					.queryList();
		}
		return schoolClassList;
	}


	@Override
	public void setSchoolClassList(List<SchoolClassDbFlowEntity> schoolClassList)
	{
		this.schoolClassList = schoolClassList;
		notifyPropertyChanged(BR.schoolClassList);
	}


	public long getServerId() {
		return serverId;
	}


	public void setServerId(long serverId) {
		this.serverId = serverId;
	}


	public void update(TeacherDbFlowEntity entity) {
		serverId = entity.getServerId();
		name = entity.getName();
	}
}

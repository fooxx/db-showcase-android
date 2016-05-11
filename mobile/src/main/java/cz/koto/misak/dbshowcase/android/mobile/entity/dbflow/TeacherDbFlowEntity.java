package cz.koto.misak.dbshowcase.android.mobile.entity.dbflow;

import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;

import cz.koto.misak.dbshowcase.android.mobile.db.dbflow.DbFlowDatabase;
import cz.koto.misak.dbshowcase.android.mobile.entity.entityinterface.TeacherInterface;


@Table(database = DbFlowDatabase.class)
public class TeacherDbFlowEntity extends BaseDbFlowModel implements TeacherInterface<SchoolClassDbFlowEntity>
{

	@PrimaryKey(autoincrement = true)
	@Column
	@SerializedName("id")
	long id;
	@Column
	@SerializedName("name")
	String name;
	List<SchoolClassDbFlowEntity> schoolClassList;



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
	public List<SchoolClassDbFlowEntity> getSchoolClassList()
	{
		if (schoolClassList == null || schoolClassList.isEmpty()) {
			schoolClassList = new Select()
					.from(SchoolClassDbFlowEntity.class)
					.innerJoin(SchoolClassDbFlowEntity_TeacherDbFlowEntity.class)
					.on(TeacherDbFlowEntity_Table.id.eq(SchoolClassDbFlowEntity_TeacherDbFlowEntity_Table.teacherDbFlowEntity_id))
					.where(SchoolClassDbFlowEntity_TeacherDbFlowEntity_Table.teacherDbFlowEntity_id.eq(id))
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
}

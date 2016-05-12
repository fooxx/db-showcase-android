package cz.koto.misak.dbshowcase.android.mobile.db.dbflow;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.List;

import cz.koto.misak.dbshowcase.android.mobile.listener.OnDataSavedToDbListener;
import cz.koto.misak.dbshowcase.android.mobile.entity.dbflow.SchoolClassDbFlowEntity;
import cz.koto.misak.dbshowcase.android.mobile.entity.dbflow.SchoolClassDbFlowEntity_TeacherDbFlowEntity;
import cz.koto.misak.dbshowcase.android.mobile.entity.dbflow.StudentDbFlowEntity;
import cz.koto.misak.dbshowcase.android.mobile.entity.dbflow.TeacherDbFlowEntity;


public class DbHelper
{

	public static void saveDataToDb(List<SchoolClassDbFlowEntity> schoolClassEntities, List<TeacherDbFlowEntity> teacherEntities, List<StudentDbFlowEntity> studentEntities, OnDataSavedToDbListener listener)
	{

		Transaction transaction = FlowManager.getDatabase(DbFlowDatabase.class).beginTransactionAsync(new ITransaction()
		{
			@Override
			public void execute(DatabaseWrapper databaseWrapper)
			{
				deleteAllTables();
				//save all classes
				for(SchoolClassDbFlowEntity sc : schoolClassEntities)
				{
					sc.save();
				}
				//save all teachers
				for(TeacherDbFlowEntity t : teacherEntities)
				{
					t.save();
				}
				//save all students with their classes 1-N relationShip
				for(StudentDbFlowEntity s : studentEntities)
				{
					for(SchoolClassDbFlowEntity sc : schoolClassEntities)
					{
						if(sc.getId() == s.getSchoolClassId())
						{
							s.setSchoolClass(sc);
						}
					}
					s.save();
				}
				//save M-N relationShip between teacher and class
				for(SchoolClassDbFlowEntity sc : schoolClassEntities)
				{
					for(TeacherDbFlowEntity t : teacherEntities)
					{
						if(sc.getTeacherIdList().contains(t.getId()))
						{
							SchoolClassDbFlowEntity_TeacherDbFlowEntity entity = new SchoolClassDbFlowEntity_TeacherDbFlowEntity();
							entity.setTeacherDbFlowEntity(t);
							entity.setSchoolClassDbFlowEntity(sc);
							entity.save();
						}
					}

				}
			}
		}).success(new Transaction.Success()
		{
			@Override
			public void onSuccess(Transaction transaction)
			{
				listener.onDataSavedToDb();
			}
		}).build();

		transaction.execute();

	}


	public static void deleteAllTables()
	{
		new Delete().from(SchoolClassDbFlowEntity_TeacherDbFlowEntity.class).execute();
		new Delete().from(SchoolClassDbFlowEntity.class).execute();
		new Delete().from(TeacherDbFlowEntity.class).execute();
		new Delete().from(StudentDbFlowEntity.class).execute();
	}

}

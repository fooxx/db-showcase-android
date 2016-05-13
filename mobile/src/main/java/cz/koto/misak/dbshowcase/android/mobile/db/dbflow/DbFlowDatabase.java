package cz.koto.misak.dbshowcase.android.mobile.db.dbflow;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.sql.SQLiteType;
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;

import cz.koto.misak.dbshowcase.android.mobile.entity.dbflow.StudentDbFlowEntity;


@Database(name = DbFlowDatabase.NAME, version = DbFlowDatabase.VERSION)
public class DbFlowDatabase
{

	public static final String NAME = "MyDataBase";

	public static final int VERSION = 2;

//
//	@Migration(version = 2, database = DbFlowDatabase.class)
//	public static class AddEmailToUserMigration extends AlterTableMigration<StudentDbFlowEntity>
//	{
//
//		public AddEmailToUserMigration(Class<StudentDbFlowEntity> table) {
//			super(table);
//		}
//
//		@Override
//		public void onPreMigrate() {
//			addColumn(SQLiteType.TEXT, "email");
//		}
//	}


}

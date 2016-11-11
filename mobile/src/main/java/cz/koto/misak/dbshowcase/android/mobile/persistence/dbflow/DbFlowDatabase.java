package cz.koto.misak.dbshowcase.android.mobile.persistence.dbflow;

import com.raizlabs.android.dbflow.annotation.Database;


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

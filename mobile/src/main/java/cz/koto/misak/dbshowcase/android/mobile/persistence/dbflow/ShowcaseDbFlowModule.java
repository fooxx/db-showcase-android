package cz.koto.misak.dbshowcase.android.mobile.persistence.dbflow;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.rx2.language.RXSQLite;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import cz.koto.misak.dbshowcase.android.mobile.model.DataHandlerListener;
import cz.koto.misak.dbshowcase.android.mobile.model.SchoolClassInterface;
import cz.koto.misak.dbshowcase.android.mobile.persistence.ShowcasePersistence;
import cz.koto.misak.dbshowcase.android.mobile.persistence.dbflow.model.SchoolClassDbFlowEntity;
import cz.koto.misak.dbshowcase.android.mobile.persistence.dbflow.model.SchoolClassDbFlowEntity_TeacherDbFlowEntity;
import cz.koto.misak.dbshowcase.android.mobile.persistence.dbflow.model.SchoolClassDbFlowEntity_TeacherDbFlowEntity_Table;
import cz.koto.misak.dbshowcase.android.mobile.persistence.dbflow.model.StudentDbFlowEntity;
import cz.koto.misak.dbshowcase.android.mobile.persistence.dbflow.model.StudentDbFlowEntity_Table;
import cz.koto.misak.dbshowcase.android.mobile.persistence.dbflow.model.TeacherDbFlowEntity;
import cz.koto.misak.dbshowcase.android.mobile.persistence.listener.DataSaveSuccessListener;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * DBFlow module related to CREATE/READ/UPDATE/DELETE operations.
 */
@Module
public class ShowcaseDbFlowModule implements ShowcasePersistence {

	public static void saveDataToDb(List<SchoolClassDbFlowEntity> schoolClassEntities, List<TeacherDbFlowEntity> teacherEntities, List<StudentDbFlowEntity> studentEntities, DataSaveSuccessListener listener) {


	}


	public static void insertNewStudentForClass(StudentDbFlowEntity student, SchoolClassDbFlowEntity schoolClass, DataSaveSuccessListener listener) {
		Transaction transaction = FlowManager.getDatabase(DbFlowDatabase.class).beginTransactionAsync(new ITransaction() {
			@Override
			public void execute(DatabaseWrapper databaseWrapper) {
				student.setSchoolClass(schoolClass);
				student.save();
			}
		}).success(transaction1 -> listener.onDataSaveSuccess()).build();

		transaction.execute();
	}


	public static void insertNewTeacherForClass(TeacherDbFlowEntity teacher, SchoolClassDbFlowEntity schoolClass, DataSaveSuccessListener listener) {
		Transaction transaction = FlowManager.getDatabase(DbFlowDatabase.class).beginTransactionAsync(new ITransaction() {
			@Override
			public void execute(DatabaseWrapper databaseWrapper) {
				teacher.save();
				SchoolClassDbFlowEntity_TeacherDbFlowEntity entity = new SchoolClassDbFlowEntity_TeacherDbFlowEntity();
				entity.setSchoolClassDbFlowEntity(schoolClass);
				entity.setTeacherDbFlowEntity(teacher);
				entity.save();
			}
		}).success(new Transaction.Success() {
			@Override
			public void onSuccess(Transaction transaction) {
				listener.onDataSaveSuccess();
			}
		}).build();

		transaction.execute();
	}


	public static void deleteFirstStudentFromClass(SchoolClassDbFlowEntity schoolClass, DataSaveSuccessListener listener) {
		Transaction transaction = FlowManager.getDatabase(DbFlowDatabase.class).beginTransactionAsync(new ITransaction() {
			@Override
			public void execute(DatabaseWrapper databaseWrapper) {
				StudentDbFlowEntity student = new Select().from(StudentDbFlowEntity.class).where(StudentDbFlowEntity_Table.schoolClass_dbId.eq(schoolClass.getId())).querySingle();
				if(student != null) {
					student.delete();
				}
			}
		}).success(new Transaction.Success() {
			@Override
			public void onSuccess(Transaction transaction) {
				listener.onDataSaveSuccess();
			}
		}).build();

		transaction.execute();
	}


	public static void deleteFirstTeacherFromClass(SchoolClassDbFlowEntity schoolClass, DataSaveSuccessListener listener) {
		Transaction transaction = FlowManager.getDatabase(DbFlowDatabase.class).beginTransactionAsync(new ITransaction() {
			@Override
			public void execute(DatabaseWrapper databaseWrapper) {
				SchoolClassDbFlowEntity_TeacherDbFlowEntity teacherClassEntity = new Select().from(SchoolClassDbFlowEntity_TeacherDbFlowEntity.class)
						.where(SchoolClassDbFlowEntity_TeacherDbFlowEntity_Table.schoolClassDbFlowEntity_dbId.eq(schoolClass.getId())).querySingle();
				if(teacherClassEntity != null) {
					TeacherDbFlowEntity teacher = teacherClassEntity.getTeacherDbFlowEntity();
					if(teacher != null) {
						SchoolClassDbFlowEntity_TeacherDbFlowEntity entity = new Select().from(SchoolClassDbFlowEntity_TeacherDbFlowEntity.class)
								.where(SchoolClassDbFlowEntity_TeacherDbFlowEntity_Table.teacherDbFlowEntity_dbId.eq(teacher.getId()))
								.and(SchoolClassDbFlowEntity_TeacherDbFlowEntity_Table.schoolClassDbFlowEntity_dbId.eq(schoolClass.getId()))
								.querySingle();
						if(entity != null) {
							entity.delete();
						}
					}
				}

			}
		}).success(new Transaction.Success() {
			@Override
			public void onSuccess(Transaction transaction) {
				listener.onDataSaveSuccess();
			}
		}).build();

		transaction.execute();
	}


	private static void setClassForStudent(List<SchoolClassDbFlowEntity> schoolClassEntities, StudentDbFlowEntity s) {
		for(SchoolClassDbFlowEntity sc : schoolClassEntities) {
			if(sc.getServerId() == s.getSchoolClassId()) {
				s.setSchoolClass(sc);
			}
		}
	}


	private static void deleteTeacherClassRelationship() {
		new Delete().from(SchoolClassDbFlowEntity_TeacherDbFlowEntity.class).execute();
	}


	@Override
	public void saveOrUpdateSchoolClass(List<? extends SchoolClassInterface> schoolModel, DataHandlerListener dataHandlerListener) {

		Transaction transaction = FlowManager.getDatabase(DbFlowDatabase.class).beginTransactionAsync(new ITransaction() {
			@Override
			public void execute(DatabaseWrapper databaseWrapper) {
				deleteTeacherClassRelationship();


				List<SchoolClassDbFlowEntity> persistenceClassModel = new ArrayList<>();
				List<TeacherDbFlowEntity> persistenceTeacherModel = new ArrayList<>();
				List<StudentDbFlowEntity> persistenceStudentModel = new ArrayList<>();

				for(SchoolClassInterface schoolClass : schoolModel) {
					if(schoolClass instanceof SchoolClassDbFlowEntity) {
						persistenceClassModel.add((SchoolClassDbFlowEntity) schoolClass);
						persistenceStudentModel.addAll(((SchoolClassDbFlowEntity) schoolClass).getStudentList());
						persistenceTeacherModel.addAll(((SchoolClassDbFlowEntity) schoolClass).getTeacherList());
					} else {
						//TODO translate from Realm entity to DBFlow entity
						throw new RuntimeException("Translation from Realm entity to DBFlow entity is NOT implemented yet!");
					}
				}

				//save all classes
				List<SchoolClassDbFlowEntity> schoolClassList = new Select().from(SchoolClassDbFlowEntity.class).queryList();
				for(SchoolClassDbFlowEntity sc : persistenceClassModel) {
					boolean updated = false;
					for(SchoolClassDbFlowEntity sc1 : schoolClassList) {
						if(sc1.getServerId() == sc.getServerId()) {
							sc1.update(sc);
							sc1.save();
							updated = true;
						}
					}
					if(!updated) {
						sc.save();
					}
				}
				//save all teachers
				List<TeacherDbFlowEntity> teacherList = new Select().from(TeacherDbFlowEntity.class).queryList();
				for(TeacherDbFlowEntity t : persistenceTeacherModel) {
					boolean updated = false;
					for(TeacherDbFlowEntity t1 : teacherList) {
						if(t1.getServerId() == t.getServerId()) {
							t1.update(t);
							t1.save();
							updated = true;
						}
					}
					if(!updated) {
						t.save();
					}
				}
				//save all students with their classes 1-N relationShip
				List<StudentDbFlowEntity> studentList = new Select().from(StudentDbFlowEntity.class).queryList();
				List<SchoolClassDbFlowEntity> schoolClassEntities1 = new Select().from(SchoolClassDbFlowEntity.class).queryList();
				for(StudentDbFlowEntity s : persistenceStudentModel) {
					boolean updated = false;
					for(StudentDbFlowEntity s1 : studentList) {
						if(s1.getServerId() == s.getServerId()) {
							s1.update(s);
							setClassForStudent(schoolClassEntities1, s1);
							s1.save();
							updated = true;
						}
					}

					if(!updated) {
						setClassForStudent(schoolClassEntities1, s);
						s.save();
					}
				}
				//save M-N relationShip between teacher and class
				List<TeacherDbFlowEntity> teacherList1 = new Select().from(TeacherDbFlowEntity.class).queryList();
				for(SchoolClassDbFlowEntity sc : schoolClassEntities1) {
					for(SchoolClassDbFlowEntity sc1 : persistenceClassModel) {
						if(sc1.getServerId() == sc.getServerId()) {
							sc.setTeacherIdList(sc1.getTeacherIdList());
						}
					}
					for(TeacherDbFlowEntity t : teacherList1) {
						if(sc.getTeacherIdList().contains(t.getServerId())) {
							SchoolClassDbFlowEntity_TeacherDbFlowEntity entity = new SchoolClassDbFlowEntity_TeacherDbFlowEntity();
							entity.setTeacherDbFlowEntity(t);
							entity.setSchoolClassDbFlowEntity(sc);
							entity.save();
						}
					}

				}
			}
		}).error((transactionResult, throwable) -> dataHandlerListener.handleFailed(throwable))
				.success(transactionResult -> dataHandlerListener.handleSuccess()).build();
		transaction.execute();
	}


	@Provides
	@Singleton
	public ShowcaseDbFlowModule provideShowcaseDbFlowModule() {
		return new ShowcaseDbFlowModule();
	}


	public Single<List<SchoolClassDbFlowEntity>> getSchoolClass() {
		return RXSQLite.rx(SQLite.select().from(SchoolClassDbFlowEntity.class)).queryList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
	}
}

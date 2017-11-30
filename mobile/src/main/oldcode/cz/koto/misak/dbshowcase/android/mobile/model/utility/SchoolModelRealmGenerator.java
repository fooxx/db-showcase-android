package cz.koto.misak.dbshowcase.android.mobile.model.utility;


import java.util.Arrays;

import cz.koto.misak.dbshowcase.android.mobile.persistence.realm.model.RealmLong;
import cz.koto.misak.dbshowcase.android.mobile.persistence.realm.model.SchoolClassRealmEntity;
import cz.koto.misak.dbshowcase.android.mobile.persistence.realm.model.StudentRealmEntity;
import cz.koto.misak.dbshowcase.android.mobile.persistence.realm.model.TeacherRealmEntity;
import cz.koto.misak.dbshowcase.android.mobile.utility.RandomUtilsKt;
import io.realm.RealmList;


public class SchoolModelRealmGenerator {



	public static SchoolClassRealmEntity generateSchoolClassRealmEntity(int currentTeacherCount,
																		int currentStudentCount,
																		int currentClassCount) {
		SchoolClassRealmEntity schoolClass = new SchoolClassRealmEntity();
		schoolClass.setName(RandomUtilsKt.getRandomString(2, 4));
		schoolClass.setId(currentClassCount + 1);
		schoolClass.setGrade(2);

		RealmList<TeacherRealmEntity> teacherList = new RealmList<>();
		RealmList<RealmLong> teacherIdList = new RealmList<>();
		for(int i = 0; i < RandomUtilsKt.getRandomInt(1, 3); i++) {
			TeacherRealmEntity teacher = getRandomTeacher(Long.valueOf(currentTeacherCount + 2 + i));
			teacher.setSchoolClassList(Arrays.asList(schoolClass));
			teacherList.add(teacher);
			teacherIdList.add(new RealmLong(teacher.getId()));
		}
		schoolClass.setTeacherIdRealmList(teacherIdList);
		schoolClass.setTeacherRealmList(teacherList);


		RealmList<StudentRealmEntity> studentList = new RealmList<>();
		RealmList<RealmLong> studentIdList = new RealmList<>();
		for(int i = 0; i < RandomUtilsKt.getRandomInt(1, 20); i++) {
			StudentRealmEntity student = getRandomStudent(Long.valueOf(currentStudentCount + 2 + i));
			student.setSchoolClass(schoolClass);
			studentList.add(student);
			studentIdList.add(new RealmLong(student.getId()));
		}
		schoolClass.setStudentIdRealmList(studentIdList);
		schoolClass.setStudentList(studentList);

		return schoolClass;
	}


	public static StudentRealmEntity getRandomStudent(Long id) {
		StudentRealmEntity student = new StudentRealmEntity();
		if(id != null)
			student.setId(id);
		student.setBirthDate(RandomUtilsKt.getRandomBirthday(10, 15));
		student.setName(RandomUtilsKt.getRandomString(5, 8) + " " + RandomUtilsKt.getRandomString(5, 8));
		return student;
	}


	public static TeacherRealmEntity getRandomTeacher(Long id) {
		TeacherRealmEntity teacher = new TeacherRealmEntity();
		if(id != null)
			teacher.setId(id);
		teacher.setBirthDate(RandomUtilsKt.getRandomBirthday(25, 65));
		teacher.setName(RandomUtilsKt.getRandomString(5, 8) + " " + RandomUtilsKt.getRandomString(5, 8));
		return teacher;
	}



}
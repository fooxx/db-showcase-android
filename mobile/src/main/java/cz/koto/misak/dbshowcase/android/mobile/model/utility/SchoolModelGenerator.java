package cz.koto.misak.dbshowcase.android.mobile.model.utility;


import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import cz.koto.misak.dbshowcase.android.mobile.persistence.realm.model.RealmLong;
import cz.koto.misak.dbshowcase.android.mobile.persistence.realm.model.SchoolClassRealmEntity;
import cz.koto.misak.dbshowcase.android.mobile.persistence.realm.model.StudentRealmEntity;
import cz.koto.misak.dbshowcase.android.mobile.persistence.realm.model.TeacherRealmEntity;
import io.realm.RealmList;


public class SchoolModelGenerator {
	public static SchoolClassRealmEntity generateSchoolClassRealmEntity(int currentTeacherCount,
																		int currentStudentCount,
																		int currentClassCount) {
		SchoolClassRealmEntity schoolClass = new SchoolClassRealmEntity();
		schoolClass.setName(getRandomString(2, 4));
		schoolClass.setId(currentClassCount + 1);
		schoolClass.setGrade(2);

		RealmList<TeacherRealmEntity> teacherList = new RealmList<>();
		RealmList<RealmLong> teacherIdList = new RealmList<>();
		for(int i = 0; i < getRandomInt(1, 3); i++) {
			TeacherRealmEntity teacher = getRandomTeacher(Long.valueOf(currentTeacherCount + 2 + i));
			teacher.setSchoolClassList(Arrays.asList(schoolClass));
			teacherList.add(teacher);
			teacherIdList.add(new RealmLong(teacher.getId()));
		}
		schoolClass.setTeacherIdRealmList(teacherIdList);
		schoolClass.setTeacherRealmList(teacherList);


		RealmList<StudentRealmEntity> studentList = new RealmList<>();
		RealmList<RealmLong> studentIdList = new RealmList<>();
		for(int i = 0; i < getRandomInt(1, 20); i++) {
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
		student.setBirthDate(getRandomBirthday(10, 15));
		student.setName(getRandomString(5, 8) + " " + getRandomString(5, 8));
		return student;
	}


	public static TeacherRealmEntity getRandomTeacher(Long id) {
		TeacherRealmEntity teacher = new TeacherRealmEntity();
		if(id != null)
			teacher.setId(id);
		teacher.setBirthDate(getRandomBirthday(25, 65));
		teacher.setName(getRandomString(5, 8) + " " + getRandomString(5, 8));
		return teacher;
	}


	private static Date getRandomBirthday(int minAge, int maxAge) {
		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();
		cal.add(Calendar.YEAR, -getRandomInt(minAge, maxAge));
		return cal.getTime();
	}


	private static int getRandomInt(int minValue, int maxValue) {
		Random number = new Random(System.nanoTime());
		int ret = minValue;
		while(ret <= minValue) {
			ret = number.nextInt(maxValue);
		}
		return ret;
	}


	private static String getRandomString(int minLenght, int maxLenght) {
		Random generator = new Random(System.nanoTime());
		StringBuilder randomStringBuilder = new StringBuilder();
		int randomLength = getRandomInt(minLenght, maxLenght);
		char tempChar;
		for(int i = 0; i < randomLength; i++) {
			tempChar = (char) (generator.nextInt(96) + 32);
			randomStringBuilder.append(tempChar);
		}
		return randomStringBuilder.toString();
	}
}
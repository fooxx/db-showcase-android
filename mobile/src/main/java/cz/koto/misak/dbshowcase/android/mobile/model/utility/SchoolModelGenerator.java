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
			TeacherRealmEntity teacher = new TeacherRealmEntity();
			teacher.setId(currentTeacherCount + 2 + i);
			teacherIdList.add(new RealmLong(teacher.getId()));
			teacher.setBirthDate(getRandomBirthday(25, 65));
			teacher.setName(getRandomString(5, 8) + " " + getRandomString(5, 8));
			teacher.setSchoolClassList(Arrays.asList(schoolClass));
			teacherList.add(teacher);
		}
		schoolClass.setTeacherIdRealmList(teacherIdList);
		schoolClass.setTeacherRealmList(teacherList);


		RealmList<StudentRealmEntity> studentList = new RealmList<>();
		RealmList<RealmLong> studentIdList = new RealmList<>();
		for(int i = 0; i < getRandomInt(1, 20); i++) {
			StudentRealmEntity student = new StudentRealmEntity();
			student.setId(currentStudentCount + 2 + i);
			studentIdList.add(new RealmLong(student.getId()));
			student.setBirthDate(getRandomBirthday(10, 15));
			student.setName(getRandomString(5, 8) + " " + getRandomString(5, 8));
			student.setSchoolClass(schoolClass);
			studentList.add(student);
		}
		schoolClass.setStudentIdRealmList(studentIdList);
		schoolClass.setStudentList(studentList);

		return schoolClass;
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
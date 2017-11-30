package cz.koto.misak.dbshowcase.android.mobile.model.utility;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.koto.misak.dbshowcase.android.mobile.persistence.dbflow.model.SchoolClassDbFlowEntity;
import cz.koto.misak.dbshowcase.android.mobile.persistence.dbflow.model.StudentDbFlowEntity;
import cz.koto.misak.dbshowcase.android.mobile.persistence.dbflow.model.TeacherDbFlowEntity;
import cz.koto.misak.dbshowcase.android.mobile.utility.RandomUtilsKt;


public class SchoolModelDbFlowGenerator {


	public static SchoolClassDbFlowEntity generateSchoolClassDbFlowEntity(int currentTeacherCount,
																		  int currentStudentCount,
																		  int currentClassCount) {
		SchoolClassDbFlowEntity schoolClass = new SchoolClassDbFlowEntity();
		schoolClass.setName(RandomUtilsKt.getRandomString(2, 4));
		schoolClass.setId(currentClassCount + 1);
		schoolClass.setGrade(2);

		List<TeacherDbFlowEntity> teacherList = new ArrayList<>();
		List<Long> teacherIdList = new ArrayList<>();
		for(int i = 0; i < RandomUtilsKt.getRandomInt(1, 3); i++) {
			TeacherDbFlowEntity teacher = getRandomTeacher(Long.valueOf(currentTeacherCount + 2 + i));
			teacher.setSchoolClassList(Arrays.asList(schoolClass));
			teacherList.add(teacher);
			teacherIdList.add(teacher.getId());
		}
		schoolClass.setTeacherList(teacherList);
		schoolClass.setTeacherIdList(teacherIdList);


		List<StudentDbFlowEntity> studentList = new ArrayList<>();
		List<Long> studentIdList = new ArrayList<>();
		for(int i = 0; i < RandomUtilsKt.getRandomInt(1, 20); i++) {
			StudentDbFlowEntity student = getRandomStudent(Long.valueOf(currentStudentCount + 2 + i));
			student.setSchoolClass(schoolClass);
			studentList.add(student);
			studentIdList.add(student.getId());
		}
		schoolClass.setStudentList(studentList);
		schoolClass.setStudentIdList(studentIdList);

		return schoolClass;
	}


	public static StudentDbFlowEntity getRandomStudent(Long id) {
		StudentDbFlowEntity student = new StudentDbFlowEntity();
		if(id != null)
			student.setId(id);
		student.setBirthDate(RandomUtilsKt.getRandomBirthday(10, 15));
		student.setName(RandomUtilsKt.getRandomString(5, 8) + " " + RandomUtilsKt.getRandomString(5, 8));
		return student;
	}


	public static TeacherDbFlowEntity getRandomTeacher(Long id) {
		TeacherDbFlowEntity teacher = new TeacherDbFlowEntity();
		if(id != null)
			teacher.setId(id);
		//TODO teacher.setBirthDate(RandomUtilsKt.getRandomBirthday(25, 65));
		teacher.setName(RandomUtilsKt.getRandomString(5, 8) + " " + RandomUtilsKt.getRandomString(5, 8));
		return teacher;
	}


}
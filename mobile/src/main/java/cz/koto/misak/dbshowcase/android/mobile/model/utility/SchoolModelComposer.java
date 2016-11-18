package cz.koto.misak.dbshowcase.android.mobile.model.utility;


import android.os.Looper;

import java.util.List;

import cz.koto.misak.dbshowcase.android.mobile.model.SchoolClassInterface;
import cz.koto.misak.dbshowcase.android.mobile.model.StudentInterface;
import cz.koto.misak.dbshowcase.android.mobile.model.TeacherInterface;
import timber.log.Timber;


public class SchoolModelComposer {
	public static List<? extends SchoolClassInterface> composeSchoolModel(List<? extends SchoolClassInterface> schoolClassEntityList,
																		  List<? extends TeacherInterface> teacherEntityList,
																		  List<? extends StudentInterface> studentEntityList) {

		if(Looper.myLooper() == Looper.getMainLooper()) {
			throw new RuntimeException("Do NOT call saveRealmData on UI thread!");
		}

		for(SchoolClassInterface schoolClass : schoolClassEntityList) {
			for(TeacherInterface teacher : teacherEntityList) {
				List<Long> tl = schoolClass.getTeacherIdList();
				for(Long teacherId : tl) {
					if(teacherId.longValue() == teacher.getId()) {
						schoolClass.getTeacherList().add(teacher);
						Timber.d("Added teacher [%s]", teacher);
					}
				}
			}

			for(StudentInterface student : studentEntityList) {
				List<Long> sl = schoolClass.getStudentIdList();
				for(Long studentId : sl) {
					if(studentId.longValue() == student.getId()) {
						schoolClass.getStudentList().add(student);
						Timber.d("Added student [%s]", student);
					}
				}

			}
		}
		return schoolClassEntityList;
	}
}

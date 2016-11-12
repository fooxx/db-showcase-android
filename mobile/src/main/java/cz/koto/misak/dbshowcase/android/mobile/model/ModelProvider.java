package cz.koto.misak.dbshowcase.android.mobile.model;

import android.os.Looper;

import java.util.List;

import cz.koto.misak.dbshowcase.android.mobile.api.DbShowcaseAPIClient;
import cz.koto.misak.dbshowcase.android.mobile.api.OnDataLoadedListener;
import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class ModelProvider {

	private static SchoolModel sSchoolModel = new SchoolModel();


	public static void initModelFromApi(OnDataLoadedListener successListener) {
		Maybe.zip(DbShowcaseAPIClient.getAPIService().classList(),
				DbShowcaseAPIClient.getAPIService().teacherList(),
				DbShowcaseAPIClient.getAPIService().studentList(),
				ModelProvider::composeSchoolModel)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(list -> {
							sSchoolModel.setSchoolItems(list);
							successListener.loadSuccess();
						},
						throwable -> {
							Timber.d(throwable, "on error init:");
						});
	}


	public static SchoolModel getSchoolModel() {
		return sSchoolModel;
	}


	private static List<? extends SchoolClassInterface> composeSchoolModel(List<? extends SchoolClassInterface> schoolClassEntityList,
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

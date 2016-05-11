package cz.koto.misak.dbshowcase.android.mobile.api.request;

import java.util.List;

import cz.koto.misak.dbshowcase.android.mobile.entity.dbflow.StudentDbFlowEntity;
import retrofit2.Response;
import retrofit2.http.GET;
import rx.Observable;


public interface StudentListDbFlowRequest
{
	@GET("/students")
	Observable<Response<List<StudentDbFlowEntity>>> getStudentList();
}

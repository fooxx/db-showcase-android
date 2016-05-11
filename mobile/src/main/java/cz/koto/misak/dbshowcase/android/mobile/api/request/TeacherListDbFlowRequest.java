package cz.koto.misak.dbshowcase.android.mobile.api.request;

import java.util.List;

import cz.koto.misak.dbshowcase.android.mobile.entity.dbflow.TeacherDbFlowEntity;
import retrofit2.Response;
import retrofit2.http.GET;
import rx.Observable;


/**
 * Created by Juraj on 5/11/2016.
 */
public interface TeacherListDbFlowRequest
{
	@GET("/teachers")
	Observable<Response<List<TeacherDbFlowEntity>>> getTeacherList();
}

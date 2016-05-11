package cz.koto.misak.dbshowcase.android.mobile.rest;


import java.util.List;

import cz.koto.misak.dbshowcase.android.mobile.entity.entityinterface.SchoolClassInterface;
import retrofit2.http.GET;
import rx.Observable;

public interface DbShowcaseAPIService {

    @GET("/api/dbshowcase/class")
    Observable<List<SchoolClassInterface>> classList();

}

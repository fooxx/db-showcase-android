package cz.koto.misak.dbshowcase.android.mobile.rest;


import java.util.List;

import cz.koto.misak.dbshowcase.android.mobile.entity.entityinterface.SchoolClassInterface;
import retrofit2.http.GET;
import rx.Observable;

public interface DbShowcaseAPIService {

    /**
     * Data on the server defined by dev file: https://github.com/kotomisak/kotinode/blob/master/app/data/dbshowcase.class.json
     * @return
     */
    @GET("/api/dbshowcase/class")
    Observable<List<SchoolClassInterface>> classList();

}

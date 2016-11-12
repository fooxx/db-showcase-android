package cz.koto.misak.dbshowcase.android.mobile.api;


import java.util.List;

import cz.koto.misak.dbshowcase.android.mobile.persistence.dbflow.model.SchoolClassDbFlowEntity;
import cz.koto.misak.dbshowcase.android.mobile.persistence.dbflow.model.StudentDbFlowEntity;
import cz.koto.misak.dbshowcase.android.mobile.persistence.dbflow.model.TeacherDbFlowEntity;
import cz.koto.misak.dbshowcase.android.mobile.persistence.realm.model.SchoolClassRealmEntity;
import cz.koto.misak.dbshowcase.android.mobile.persistence.realm.model.StudentRealmEntity;
import cz.koto.misak.dbshowcase.android.mobile.persistence.realm.model.TeacherRealmEntity;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface DbShowcaseAPIService {

    /**
     * Data on the server defined by dev file: https://github.com/kotomisak/kotinode/blob/master/app/data/dbshowcase.class.json
     * Direct server request: http://kotopeky.cz/api/dbshowcase/class
     * @return
     */
    @GET("/api/dbshowcase/class")
    Observable<List<SchoolClassRealmEntity>> classList();

    /**
     * Data on the server defined by dev file: https://github.com/kotomisak/kotinode/blob/master/app/data/dbshowcase.teacher.json
     * Direct server request: http://kotopeky.cz/api/dbshowcase/teacher
     * @return
     */
    @GET("/api/dbshowcase/teacher")
    Observable<List<TeacherRealmEntity>> teacherList();

    /**
     * Data on the server defined by dev file: https://github.com/kotomisak/kotinode/blob/master/app/data/dbshowcase.student.json
     * Direct server request: http://kotopeky.cz/api/dbshowcase/student
     * @return
     */
    @GET("/api/dbshowcase/student")
    Observable<List<StudentRealmEntity>> studentList();

    /**
     * Data on the server defined by dev file: https://github.com/kotomisak/kotinode/blob/master/app/data/dbshowcase.class.json
     * Direct server request: http://kotopeky.cz/api/dbshowcase/class
     * @return
     */
    @GET("/api/dbshowcase/class")
    Maybe<List<SchoolClassDbFlowEntity>> classListDbFlow();

    /**
     * Data on the server defined by dev file: https://github.com/kotomisak/kotinode/blob/master/app/data/dbshowcase.teacher.json
     * Direct server request: http://kotopeky.cz/api/dbshowcase/teacher
     * @return
     */
    @GET("/api/dbshowcase/teacher")
    Maybe<List<TeacherDbFlowEntity>> teacherListDbFlow();

    /**
     * Data on the server defined by dev file: https://github.com/kotomisak/kotinode/blob/master/app/data/dbshowcase.student.json
     * Direct server request: http://kotopeky.cz/api/dbshowcase/student
     * @return
     */
    @GET("/api/dbshowcase/student")
    Maybe<List<StudentDbFlowEntity>> studentListDbFlow();
}

package cz.koto.misak.dbshowcase.android.mobile.rest;


import java.util.List;

import cz.koto.misak.dbshowcase.android.mobile.entity.dbflow.SchoolClassDbFlowEntity;
import cz.koto.misak.dbshowcase.android.mobile.entity.dbflow.StudentDbFlowEntity;
import cz.koto.misak.dbshowcase.android.mobile.entity.dbflow.TeacherDbFlowEntity;
import cz.koto.misak.dbshowcase.android.mobile.entity.rest.SchoolClassEntity;
import cz.koto.misak.dbshowcase.android.mobile.entity.rest.StudentEntity;
import cz.koto.misak.dbshowcase.android.mobile.entity.rest.TeacherEntity;
import retrofit2.http.GET;
import rx.Observable;

public interface DbShowcaseAPIService {

    /**
     * Data on the server defined by dev file: https://github.com/kotomisak/kotinode/blob/master/app/data/dbshowcase.class.json
     * Direct server request: http://kotopeky.cz/api/dbshowcase/class
     * @return
     */
    @GET("/api/dbshowcase/class")
    Observable<List<SchoolClassEntity>> classList();

    /**
     * Data on the server defined by dev file: https://github.com/kotomisak/kotinode/blob/master/app/data/dbshowcase.teacher.json
     * Direct server request: http://kotopeky.cz/api/dbshowcase/teacher
     * @return
     */
    @GET("/api/dbshowcase/teacher")
    Observable<List<TeacherEntity>> teacherList();

    /**
     * Data on the server defined by dev file: https://github.com/kotomisak/kotinode/blob/master/app/data/dbshowcase.student.json
     * Direct server request: http://kotopeky.cz/api/dbshowcase/student
     * @return
     */
    @GET("/api/dbshowcase/student")
    Observable<List<StudentEntity>> studentList();

    /**
     * Data on the server defined by dev file: https://github.com/kotomisak/kotinode/blob/master/app/data/dbshowcase.class.json
     * Direct server request: http://kotopeky.cz/api/dbshowcase/class
     * @return
     */
    @GET("/api/dbshowcase/class")
    Observable<List<SchoolClassDbFlowEntity>> classListDbFlow();

    /**
     * Data on the server defined by dev file: https://github.com/kotomisak/kotinode/blob/master/app/data/dbshowcase.teacher.json
     * Direct server request: http://kotopeky.cz/api/dbshowcase/teacher
     * @return
     */
    @GET("/api/dbshowcase/teacher")
    Observable<List<TeacherDbFlowEntity>> teacherListDbFlow();

    /**
     * Data on the server defined by dev file: https://github.com/kotomisak/kotinode/blob/master/app/data/dbshowcase.student.json
     * Direct server request: http://kotopeky.cz/api/dbshowcase/student
     * @return
     */
    @GET("/api/dbshowcase/student")
    Observable<List<StudentDbFlowEntity>> studentListDbFlow();
}

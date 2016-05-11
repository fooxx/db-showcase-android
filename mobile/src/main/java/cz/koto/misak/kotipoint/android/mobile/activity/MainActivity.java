package cz.koto.misak.kotipoint.android.mobile.activity;

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;
import com.squareup.haha.perflib.Main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.koto.misak.kotipoint.android.mobile.R;
import cz.koto.misak.kotipoint.android.mobile.databinding.ActivityMainBinding;
import cz.koto.misak.kotipoint.android.mobile.db.dbflow.DbFlowDatabase;
import cz.koto.misak.kotipoint.android.mobile.entity.dbflow.SchoolClassDbFlowEntity;
import cz.koto.misak.kotipoint.android.mobile.entity.dbflow.SchoolClassDbFlowEntity_TeacherDbFlowEntity;
import cz.koto.misak.kotipoint.android.mobile.entity.dbflow.StudentDbFlowEntity;
import cz.koto.misak.kotipoint.android.mobile.entity.dbflow.TeacherDbFlowEntity;


public class  MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.toolbar)
    android.support.v7.widget.Toolbar mToolbar;


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.button.setOnClickListener(v -> saveToDb());
    }


    private void saveToDb() {

        Log.d(TAG, "save start");
        TeacherDbFlowEntity teacher = new TeacherDbFlowEntity();
        teacher.setName("Muller");


        TeacherDbFlowEntity teacher1 = new TeacherDbFlowEntity();
        teacher1.setName("Zimmer");


        TeacherDbFlowEntity teacher2 = new TeacherDbFlowEntity();
        teacher2.setName("Faust");


        SchoolClassDbFlowEntity schoolClass = new SchoolClassDbFlowEntity();
        schoolClass.setName("1st Grade");
        schoolClass.setGrade(5);


        SchoolClassDbFlowEntity schoolClass1 = new SchoolClassDbFlowEntity();
        schoolClass1.setName("2nd Grade");
        schoolClass1.setGrade(2);


        List<StudentDbFlowEntity> studentList = generateStudents(5, schoolClass);

        SchoolClassDbFlowEntity_TeacherDbFlowEntity classTeacher = new SchoolClassDbFlowEntity_TeacherDbFlowEntity();
        classTeacher.setSchoolClassDbFlowEntity(schoolClass);
        classTeacher.setTeacherDbFlowEntity(teacher);


        SchoolClassDbFlowEntity_TeacherDbFlowEntity classTeacher1 = new SchoolClassDbFlowEntity_TeacherDbFlowEntity();
        classTeacher1.setSchoolClassDbFlowEntity(schoolClass1);
        classTeacher1.setTeacherDbFlowEntity(teacher);


        SchoolClassDbFlowEntity_TeacherDbFlowEntity classTeacher2 = new SchoolClassDbFlowEntity_TeacherDbFlowEntity();
        classTeacher2.setSchoolClassDbFlowEntity(schoolClass1);
        classTeacher2.setTeacherDbFlowEntity(teacher2);


        SchoolClassDbFlowEntity_TeacherDbFlowEntity classTeacher3 = new SchoolClassDbFlowEntity_TeacherDbFlowEntity();
        classTeacher3.setSchoolClassDbFlowEntity(schoolClass);
        classTeacher3.setTeacherDbFlowEntity(teacher1);


        DatabaseDefinition database = FlowManager.getDatabase(DbFlowDatabase.class);
        database.executeTransaction(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                teacher.save();
                teacher1.save();
                teacher2.save();
                schoolClass.save();
                schoolClass1.save();
                classTeacher.save();
                classTeacher1.save();
                classTeacher2.save();
                classTeacher3.save();
                for(StudentDbFlowEntity s: studentList) {
                    s.save();
                }
                Log.d(MainActivity.class.getSimpleName(), "saved");
            }
        });

        Log.d(TAG, "save end");
    }


    private List<StudentDbFlowEntity> generateStudents(int count, SchoolClassDbFlowEntity schoolClass) {
        List<StudentDbFlowEntity> list = new ArrayList<>();
        for(int i =0; i < count; i++)
        {
            StudentDbFlowEntity student = new StudentDbFlowEntity();
            student.setBirthDate(new Date());
            student.setName("John" + i);
            student.setSchoolClass(schoolClass);
            list.add(student);
        }
        return list;
    }


    private void readFromDb() {
        List<SchoolClassDbFlowEntity> classList = new Select().from(SchoolClassDbFlowEntity.class).queryList();
        List<StudentDbFlowEntity> studentListForClass = classList.get(0).getStudentList();
        List<TeacherDbFlowEntity> teacherListForClass = classList.get(0).getTeacherList();

        List<TeacherDbFlowEntity> teacherList = new Select().from(TeacherDbFlowEntity.class).queryList();
        List<SchoolClassDbFlowEntity> classListForTeacher = teacherList.get(0).getSchoolClassList();

        List<StudentDbFlowEntity> studentList = new Select().from(StudentDbFlowEntity.class).queryList();
        SchoolClassDbFlowEntity schoolClassForStudent = studentList.get(0).getSchoolClass();

        Log.d(MainActivity.class.getSimpleName(), classList.toString());
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfiguration) {
        super.onConfigurationChanged(newConfiguration);
    }
}
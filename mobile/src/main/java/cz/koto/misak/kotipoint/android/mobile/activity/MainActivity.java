package cz.koto.misak.kotipoint.android.mobile.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.koto.misak.kotipoint.android.mobile.R;
import cz.koto.misak.kotipoint.android.mobile.entity.dbflow.SchoolClassDbFlowEntity;
import cz.koto.misak.kotipoint.android.mobile.entity.dbflow.SchoolClassDbFlowEntity_TeacherDbFlowEntity;
import cz.koto.misak.kotipoint.android.mobile.entity.dbflow.StudentDbFlowEntity;
import cz.koto.misak.kotipoint.android.mobile.entity.dbflow.TeacherDbFlowEntity;


public class  MainActivity extends AppCompatActivity {


    FragmentManager mFragmentManager;

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
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mFragmentManager = getSupportFragmentManager();

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                List<SchoolClassDbFlowEntity> list = new Select().from(SchoolClassDbFlowEntity.class).queryList();
                Log.d(MainActivity.class.getSimpleName(), list.toString());
            }
        });


    }


    private void saveToDb() {
//        StudentDbFlowEntity student = new StudentDbFlowEntity();
//        student.setBirthDate(new Date());
//        student.setName("John");
//        TeacherDbFlowEntity teacher = new TeacherDbFlowEntity();
//        teacher.setName("Muller");
//        SchoolClassDbFlowEntity schoolClass = new SchoolClassDbFlowEntity();
//        schoolClass.setName("1st Grade");
//        schoolClass.setGrade(5);
//        student.setSchoolClass(schoolClass);
//        SchoolClassDbFlowEntity_TeacherDbFlowEntity classTeacher = new SchoolClassDbFlowEntity_TeacherDbFlowEntity();
//        classTeacher.setSchoolClassDbFlowEntity(schoolClass);
//        classTeacher.setTeacherDbFlowEntity(teacher);
//
//        schoolClass.save();
//        teacher.save();
//        student.save();
//        classTeacher.save();
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
package cz.koto.misak.dbshowcase.android.mobile.activity;

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.kinst.jakub.viewmodelbinding.ViewModelActivity;
import cz.kinst.jakub.viewmodelbinding.ViewModelBindingConfig;
import cz.koto.misak.dbshowcase.android.mobile.R;
import cz.koto.misak.dbshowcase.android.mobile.databinding.ActivityMainBinding;
import cz.koto.misak.dbshowcase.android.mobile.db.dbflow.DbFlowDatabase;
import cz.koto.misak.dbshowcase.android.mobile.db.dbflow.DbHelper;
import cz.koto.misak.dbshowcase.android.mobile.entity.dbflow.SchoolClassDbFlowEntity;
import cz.koto.misak.dbshowcase.android.mobile.entity.dbflow.SchoolClassDbFlowEntity_TeacherDbFlowEntity;
import cz.koto.misak.dbshowcase.android.mobile.entity.dbflow.StudentDbFlowEntity;
import cz.koto.misak.dbshowcase.android.mobile.entity.dbflow.TeacherDbFlowEntity;
import cz.koto.misak.dbshowcase.android.mobile.viewModel.MainActivityViewModel;


public class  MainActivity extends ViewModelActivity<ActivityMainBinding, MainActivityViewModel>
{

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    public ViewModelBindingConfig<MainActivityViewModel> getViewModelBindingConfig()
    {
        return new ViewModelBindingConfig<>(R.layout.activity_main, MainActivityViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
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
}
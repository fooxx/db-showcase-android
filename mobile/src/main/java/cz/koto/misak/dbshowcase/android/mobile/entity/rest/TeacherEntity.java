package cz.koto.misak.dbshowcase.android.mobile.entity.rest;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.koto.misak.dbshowcase.android.mobile.entity.dbflow.SchoolClassDbFlowEntity;
import cz.koto.misak.dbshowcase.android.mobile.entity.entityinterface.TeacherInterface;

public class TeacherEntity implements TeacherInterface<SchoolClassDbFlowEntity> {


    @SerializedName("id")
    protected long id;

    @SerializedName("name")
    protected String name;

    @SerializedName(value = "birthDate")
    protected Date birthDate;

    protected List<SchoolClassDbFlowEntity> schoolClassList = new ArrayList<>();

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public List<SchoolClassDbFlowEntity> getSchoolClassList() {
        return schoolClassList;
    }

    @Override
    public void setSchoolClassList(List<SchoolClassDbFlowEntity> schoolClassList) {
        schoolClassList.clear();
        schoolClassList.addAll(schoolClassList);
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "TeacherEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", schoolClassList=" + schoolClassList +
                '}';
    }
}

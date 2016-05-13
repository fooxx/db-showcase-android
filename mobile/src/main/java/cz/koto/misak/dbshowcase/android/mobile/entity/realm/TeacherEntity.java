package cz.koto.misak.dbshowcase.android.mobile.entity.realm;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@RealmClass
public class TeacherEntity /*implements TeacherInterface<SchoolClassEntity>/*, RealmModel*/ {


    @SerializedName("id")
    protected long serverId;

    @SerializedName("name")
    protected String name;

    @SerializedName(value = "birthDate")
    protected Date birthDate;

    protected List<SchoolClassEntity> schoolClassList = new ArrayList<>();


    public long getId()
    {
        return serverId;
    }


    public void setId(long serverId)
    {
        this.serverId = serverId;
    }
//
//
//    @Override
//    public String getName() {
//        return this.name;
//    }
//
//    @Override
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    @Override
//    public List<SchoolClassEntity> getSchoolClassList() {
//        return schoolClassList;
//    }
//
//    @Override
//    public void setSchoolClassList(List<SchoolClassEntity> schoolClassList) {
//        schoolClassList.clear();
//        schoolClassList.addAll(schoolClassList);
//    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "TeacherEntity{" +
                "id=" + serverId +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", schoolClassList=" + schoolClassList +
                '}';
    }
}

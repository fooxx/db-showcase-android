package cz.koto.misak.dbshowcase.android.mobile.persistence.realm.model;


import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

import cz.koto.misak.dbshowcase.android.mobile.model.TeacherInterface;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
public class TeacherRealmEntity implements TeacherInterface<SchoolClassRealmEntity>, RealmModel {


    @io.realm.annotations.PrimaryKey
    @SerializedName("id")
    protected long serverId;

    @SerializedName("name")
    protected String name;

    @SerializedName(value = "birthDate")
    protected Date birthDate;

    protected RealmList<SchoolClassRealmEntity> schoolClassList = new RealmList<>();



    public long getId()
    {
        return serverId;
    }


    public void setId(long serverId)
    {
        this.serverId = serverId;
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
    public List<SchoolClassRealmEntity> getSchoolClassList() {
        return schoolClassList;
    }

    @Override
    public void setSchoolClassList(List<SchoolClassRealmEntity> list) {
        schoolClassList.clear();
        schoolClassList.addAll(list);
    }


    @Override
    public String toString() {
        return "TeacherRealmEntity{" +
                "id=" + serverId +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", schoolClassList=" + schoolClassList +
                '}';
    }


    public Date getBirthDate() {
        return birthDate;
    }


    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}

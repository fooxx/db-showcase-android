package cz.koto.misak.dbshowcase.android.mobile.entity.realm;

import io.realm.RealmObject;

public class RealmLong extends RealmObject {

    private Long longValue;

    public RealmLong(){}


    public RealmLong(Long longValue){
        this.longValue = longValue;
    }

    public Long getLongValue() {
        return longValue;
    }

    public void setLongValue(Long longValue) {
        this.longValue = longValue;
    }

}

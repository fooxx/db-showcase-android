package cz.koto.misak.dbshowcase.android.mobile.db.realm;

import org.parceler.Parcel;

import io.realm.RealmObject;
import io.realm.RealmStringRealmProxy;

/**
 * Wrapper over String to support setting a list of Strings in a RealmObject
 * To use it with GSON, please see RealmStringDeserializer
 *
 * http://www.myandroidsolutions.com/2015/05/29/gson-realm-string-array/
 */
@Parcel(implementations = { RealmStringRealmProxy.class }, value = Parcel.Serialization.BEAN, analyze = { RealmString.class })
public class RealmString extends RealmObject {

    private String stringValue;

    public RealmString(){}

    public RealmString(String stringValue){
        this.stringValue =  stringValue;
    }


    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

}

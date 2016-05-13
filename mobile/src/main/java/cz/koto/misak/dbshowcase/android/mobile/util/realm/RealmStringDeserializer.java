package cz.koto.misak.dbshowcase.android.mobile.util.realm;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import cz.koto.misak.dbshowcase.android.mobile.entity.realm.RealmString;
import io.realm.RealmList;

/**
 * Used to deserialize a list of realm string objects
 *
 * http://www.myandroidsolutions.com/2015/05/29/gson-realm-string-array/
 */
public class RealmStringDeserializer implements
        JsonDeserializer<RealmList<RealmString>> {

    @Override
    public RealmList<RealmString> deserialize(JsonElement json, Type typeOfT,
                                              JsonDeserializationContext context) throws JsonParseException {

        RealmList<RealmString> realmStrings = new RealmList<>();
        JsonArray stringList = json.getAsJsonArray();

        for (JsonElement stringElement : stringList) {
            realmStrings.add(new RealmString(stringElement.getAsString()));
        }

        return realmStrings;
    }
}
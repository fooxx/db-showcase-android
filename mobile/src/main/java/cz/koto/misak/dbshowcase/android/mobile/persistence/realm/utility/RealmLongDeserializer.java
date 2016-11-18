package cz.koto.misak.dbshowcase.android.mobile.persistence.realm.utility;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import cz.koto.misak.dbshowcase.android.mobile.persistence.realm.model.RealmLong;
import io.realm.RealmList;

/**
 * Used to deserialize a list of realm long objects
 */
public class RealmLongDeserializer implements
        JsonDeserializer<RealmList<RealmLong>> {

    @Override
    public RealmList<RealmLong> deserialize(JsonElement json, Type typeOfT,
                                              JsonDeserializationContext context) throws JsonParseException {

        RealmList<RealmLong> realmLongs = new RealmList<>();
        JsonArray longList = json.getAsJsonArray();

        for (JsonElement longElement : longList) {
            realmLongs.add(new RealmLong(longElement.getAsLong()));
        }

        return realmLongs;
    }
}
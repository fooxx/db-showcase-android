package cz.koto.misak.dbshowcase.android.mobile.util.realm;

import org.parceler.converter.CollectionParcelConverter;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * https://gist.github.com/cmelchior/72c35fcb55cec33a71e1
 * @param <T>
 */
// Abstract class for working with RealmLists
public abstract class RealmListParcelConverter<T extends RealmObject> extends CollectionParcelConverter<T, RealmList<T>> {
    @Override
    public RealmList<T> createCollection() {
        return new RealmList<T>();
    }
}


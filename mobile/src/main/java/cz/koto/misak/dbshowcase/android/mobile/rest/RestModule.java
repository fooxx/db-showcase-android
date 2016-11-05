package cz.koto.misak.dbshowcase.android.mobile.rest;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import cz.koto.misak.dbshowcase.android.mobile.DbConfig;
import cz.koto.misak.dbshowcase.android.mobile.db.dbflow.DbFlowExclusionStrategy;
import cz.koto.misak.dbshowcase.android.mobile.db.realm.RealmExclusionStrategy;
import cz.koto.misak.dbshowcase.android.mobile.entity.realm.RealmLong;
import cz.koto.misak.dbshowcase.android.mobile.entity.realm.RealmString;
import cz.koto.misak.dbshowcase.android.mobile.util.realm.RealmLongDeserializer;
import cz.koto.misak.dbshowcase.android.mobile.util.realm.RealmStringDeserializer;
import dagger.Module;
import dagger.Provides;
import io.realm.RealmList;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class RestModule {

    protected static final int TIMEOUT = 30;


    /**
     * Ensure logging of okhttp3.OkHttpClient
     * Add this always as the last interceptor!
     *
     * @return
     */
    @Provides
    @NonNull
    public static HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }


    @Provides
    @Singleton
    Gson provideGson(){
        GsonBuilder gsonBuilder = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'")//ISO-8601
                .setExclusionStrategies(new DbFlowExclusionStrategy(), new RealmExclusionStrategy())
                //Realm adapters
                .registerTypeAdapter(new TypeToken<RealmList<RealmString>>() {
                }.getType(), new RealmStringDeserializer())
                .registerTypeAdapter(new TypeToken<RealmList<RealmLong>> () {
                }.getType(), new RealmLongDeserializer());
        return gsonBuilder.create();
    }


    @Provides
    @Singleton
    GsonConverterFactory provideGsonConverterFactory(Gson gson){
        return GsonConverterFactory.create(gson);
    }


    @Provides
    @Singleton
    OkHttpClient.Builder provideOkHttpClientBuilder(HttpLoggingInterceptor httpLoggingInterceptor){
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        client.readTimeout(TIMEOUT, TimeUnit.SECONDS);
        client.writeTimeout(TIMEOUT, TimeUnit.SECONDS);
        if (DbConfig.LOGS){
            client.addInterceptor(httpLoggingInterceptor);
        }

        return client;
    }


    @DbShowcaseRetrofitAdapter
    @Provides
    @Singleton
    Retrofit provideDbRetrofit(GsonConverterFactory gsonConverterFactory, OkHttpClient.Builder client) {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(DbConfig.API_DB_ENDPOINT)
                .client(client.build())
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());//important for RX2!!!


        return builder.build();
    }
}
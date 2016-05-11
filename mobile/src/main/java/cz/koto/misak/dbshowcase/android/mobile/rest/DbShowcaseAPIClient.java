package cz.koto.misak.dbshowcase.android.mobile.rest;

import javax.inject.Inject;

import cz.koto.misak.dbshowcase.android.mobile.DbApplication;
import retrofit2.Retrofit;


public class DbShowcaseAPIClient {
    private static final DbShowcaseAPIClient INSTANCE = new DbShowcaseAPIClient();
    private DbShowcaseAPIService mDbShowcaseService;

    @Inject
    @DbShowcaseRetrofitAdapter
    Retrofit mDbRetrofit;

    private DbShowcaseAPIClient() {
        DbApplication.get().getNetComponent().inject(this);
        mDbShowcaseService = mDbRetrofit.create(DbShowcaseAPIService.class);
    }

    public static DbShowcaseAPIClient getInstance() {
        return INSTANCE;
    }

    public static DbShowcaseAPIService getAPIService() {
        return getInstance().mDbShowcaseService;
    }

}

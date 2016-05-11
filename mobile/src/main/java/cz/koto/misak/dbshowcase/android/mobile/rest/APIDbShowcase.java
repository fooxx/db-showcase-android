package cz.koto.misak.dbshowcase.android.mobile.rest;

import javax.inject.Inject;

import cz.koto.misak.dbshowcase.android.mobile.DbApplication;
import retrofit2.Retrofit;


public class APIDbShowcase {
    private static final APIDbShowcase INSTANCE = new APIDbShowcase();
    private DbShowcaseAPIService mDbShowcaseService;

    @Inject
    @DbRetrofitAdapter
    Retrofit mDbRetrofit;

    private APIDbShowcase() {
        DbApplication.get().getNetComponent().inject(this);
        mDbShowcaseService = mDbRetrofit.create(DbShowcaseAPIService.class);
    }

    public static APIDbShowcase getInstance() {
        return INSTANCE;
    }

    public static DbShowcaseAPIService getAPIService() {
        return getInstance().mDbShowcaseService;
    }

}

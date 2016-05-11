package cz.koto.misak.dbshowcase.android.mobile;

import javax.inject.Singleton;

import cz.koto.misak.dbshowcase.android.mobile.rest.DbShowcaseAPIClient;
import cz.koto.misak.dbshowcase.android.mobile.rest.DbShowcaseRetrofitAdapter;
import cz.koto.misak.dbshowcase.android.mobile.rest.RestModule;
import dagger.Component;
import retrofit2.Retrofit;


@Singleton
@Component(modules = {RestModule.class})
public interface NetComponent {

    void inject(DbShowcaseAPIClient apiInstagram);

    // downstream components need these exposed
    @DbShowcaseRetrofitAdapter
    Retrofit retrofitSparkAdapter();

}

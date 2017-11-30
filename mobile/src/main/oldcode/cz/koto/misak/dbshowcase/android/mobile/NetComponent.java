package cz.koto.misak.dbshowcase.android.mobile;

import javax.inject.Singleton;

import cz.koto.misak.dbshowcase.android.mobile.api.DbShowcaseAPIClient;
import cz.koto.misak.dbshowcase.android.mobile.api.DbShowcaseRetrofitAdapter;
import cz.koto.misak.dbshowcase.android.mobile.api.RestModule;
import dagger.Component;
import retrofit2.Retrofit;


@Singleton
@Component(modules = {RestModule.class})
public interface NetComponent {

    void inject(DbShowcaseAPIClient dbShowcaseAPIClient);

    // downstream components need these exposed
    @DbShowcaseRetrofitAdapter
    Retrofit retrofitDbShowcaseAdapter();

}

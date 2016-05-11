package cz.koto.misak.dbshowcase.android.mobile.api;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cz.koto.misak.dbshowcase.android.mobile.DbConfig;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class RetrofitHelper
{
	protected static final int TIMEOUT = 30;
	protected static Retrofit mRetrofit;

	private static final RetrofitHelper sHelper = new RetrofitHelper();


	private RetrofitHelper()
	{
	}


	public static RetrofitHelper getInstance()
	{
		return sHelper;
	}


	public Retrofit getRetrofit()
	{
		if(mRetrofit == null)
		{
			init();
		}
		return mRetrofit;
	}


	public <T> Observable<T> makeObservable(Observable<T> observable)
	{
		return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
	}


	public <T> Subscription makeSubscription(Observable<T> observable, Action1<T> action, Action1<Throwable> throwableAction)
	{
		return makeObservable(observable).subscribe(action, throwableAction);
	}


	public Retrofit buildRetrofitFromClient(OkHttpClient.Builder builder)
	{
		return new Retrofit.Builder()
				.baseUrl(DbConfig.API_DB_ENDPOINT)
				.addConverterFactory(GsonConverterFactory.create(GsonHelper.getInstance()))
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.client(builder.build())
				.build();
	}


	public Response getResponse(Interceptor.Chain chain) throws IOException
	{
		Request.Builder builder = chain.request().newBuilder();

		Log.d(chain.request().method(), chain.request().url().toString());

		Response response;
		try
		{
			response = chain.proceed(builder.build());
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		return response;
	}


	public OkHttpClient.Builder getBaseOkHttpClient()
	{
		OkHttpClient.Builder client = new OkHttpClient.Builder();
		client.interceptors().add(new Interceptor()
		{
			@Override
			public Response intercept(Interceptor.Chain chain) throws IOException
			{
				return getResponse(chain);
			}
		});
		return client;
	}


	private void init()
	{
		OkHttpClient.Builder builder = getBaseOkHttpClient();
		builder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
		builder.readTimeout(TIMEOUT, TimeUnit.SECONDS);
		builder.writeTimeout(TIMEOUT, TimeUnit.SECONDS);
		mRetrofit = buildRetrofitFromClient(builder);
	}

}
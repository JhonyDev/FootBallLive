package com.bakrin.fblive.api;

import android.content.Context;
import android.util.Log;

import com.bakrin.fblive.info.Info;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CertificatePinner;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class APIManager implements Info {

    private static APIManager instance;
    private static Context mContext;

    public static APIManager getInstance(Context context) {
        mContext = context;
        if (instance == null) {
            instance = new APIManager();
        }
        return instance;
    }


    public APIService getAPIService() {

        GsonBuilder gsonBuilder = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setDateFormat("E, dd MMM yyyy HH:mm:ss Z")
                .serializeNulls();
        Gson gson = gsonBuilder.create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://v2.api-football.com/")
//                .baseUrl("https://v2.api-football.com/")
                .client(getClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(APIService.class);
    }
    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://165.227.124.80/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }


    public File getCacheFile(Context context) {
        return new File(context.getCacheDir(), "okhttp.cache");
    }


    public OkHttpClient getClient() {

        File file = new File(mContext.getCacheDir(), "okhttp.cache");
        Cache cache = new Cache(file, 10 * 1000 * 1000);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .cache(cache)
                .addNetworkInterceptor(
                        chain -> {
                            Request request = null;
                            Request original = chain.request();
                            Request.Builder requestBuilder = null;
                            requestBuilder = original.newBuilder()
                                    .addHeader("x-rapidapi-host", "api-football-v1.p.rapidapi.com")
                                    .addHeader("x-apisports-key", "af4532daada823806464862dc4e8e435")
//                                      .addHeader("x-rapidapi-key", "507a1d448cmsh2004d89a1825801p18d14ajsnd05309e3f5ec")
                                    .addHeader("Content-Type", "application/json")
                                    .addHeader("Accept", "application/json");

                            request = requestBuilder.build();

                            return chain.proceed(request);
                        })
                .build();
    }


}

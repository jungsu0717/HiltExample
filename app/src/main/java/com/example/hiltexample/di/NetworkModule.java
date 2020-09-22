package com.example.hiltexample.di;

import com.example.hiltexample.BuildConfig;
import com.example.hiltexample.model.network.ApiService;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(ApplicationComponent.class)
public class NetworkModule {

    private static final int CONNECT_TIMEOUT = 15;
    private static final int WRITE_TIMEOUT = 15;
    private static final int READ_TIMEOUT = 15;

//    @Provides
//    @Singleton
//    Interceptor provideInterceptor() {
//        return chain -> {
//            if ("token" != null && !"token".isEmpty()) {
//                final String bearer = "BEARER " + "token";
//                final Request.Builder builder = chain.request().newBuilder()
//                        .header("Authorization", bearer)
//                        .header("Accept", "application/json");
//
//                return chain.proceed(builder.build());
//            } else {
//                return chain.proceed(chain.request());
//            }
//
//        };
//    }


    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient
                .Builder()
//                .addNetworkInterceptor(new NetworkInterceptor())
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    ApiService provideApiService(OkHttpClient okHttpClient) {
        return new Retrofit
                .Builder()
                .client(okHttpClient)
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService.class);
    }

}

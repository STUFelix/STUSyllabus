package com.example.daidaijie.syllabusapplication.util;

import com.example.daidaijie.syllabusapplication.bean.HttpResult;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by daidaijie on 2016/7/18.
 */
public class RetrofitUtil {
    //.baseUrl("https://stuapps.com/")
    private static Retrofit mRetrofit = new Retrofit.Builder()
            .baseUrl("http://120.79.134.178:8080/")
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static Retrofit getDefault() {
        return mRetrofit;
    }

    public static boolean isSuccessful(HttpResult httpResult) {
        if (httpResult.getCode() == 200 && httpResult.getMessage().equals("success")) {
            return true;
        }
        return false;
    }

}

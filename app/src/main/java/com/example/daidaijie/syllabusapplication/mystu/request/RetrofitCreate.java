package com.example.daidaijie.syllabusapplication.mystu.request;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitCreate {
    private static final int TIME_OUT =50;

    public Getdata_Interface getRetrofit(){

        OkHttpClient client = new OkHttpClient().newBuilder()
            .readTimeout(TIME_OUT,TimeUnit.SECONDS)
            .connectTimeout(TIME_OUT,TimeUnit.SECONDS)
            .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://class.stuapps.com")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Getdata_Interface cookiesRequest = retrofit.create(Getdata_Interface.class);
        return cookiesRequest;
    }
}

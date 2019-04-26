package com.example.daidaijie.syllabusapplication;

import android.util.Log;

import com.example.daidaijie.syllabusapplication.di.qualifier.retrofitQualifier.BmobRetrofit;
import com.example.daidaijie.syllabusapplication.di.qualifier.retrofitQualifier.LibraryRetrofit;
import com.example.daidaijie.syllabusapplication.di.qualifier.retrofitQualifier.OARetrofit;
import com.example.daidaijie.syllabusapplication.di.qualifier.retrofitQualifier.SchoolRetrofit;
import com.example.daidaijie.syllabusapplication.di.qualifier.retrofitQualifier.TestRetrofit;
import com.example.daidaijie.syllabusapplication.di.qualifier.retrofitQualifier.TodoRetrofit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by daidaijie on 2016/10/8.
 */

@Module
public class RetrofitModule {
    private static final String TAG = "RetrofitModule";
    @SchoolRetrofit
    @Provides
    @Singleton
    public Retrofit provideSchoolRetrofit() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            // Log信息拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//这里可以选择拦截级别

            //设置 Debug Log 模式
            builder.addInterceptor(loggingInterceptor);
        }
        OkHttpClient client = builder.build();
        return new Retrofit.Builder()
                .baseUrl("https://class.stuapps.com")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    @BmobRetrofit
    @Provides
    @Singleton
    public Retrofit provideBmobRetrofit() {
        Log.d(TAG, "provideTESTRetrofit: ");
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            // Log信息拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//这里可以选择拦截级别

            //设置 Debug Log 模式
            builder.addInterceptor(loggingInterceptor);
        }

        OkHttpClient client = builder.build();
        return new Retrofit.Builder()
                .baseUrl("https://api.bmob.cn/1/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    @OARetrofit
    @Provides
    @Singleton
    public Retrofit provideOARetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://wechat.stu.edu.cn/webservice_oa/oa_stu_/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @LibraryRetrofit
    @Provides
    @Singleton
    public Retrofit provideLibraryRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://opac.lib.stu.edu.cn:83/opac/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    @TodoRetrofit
    @Provides
    @Singleton
    public Retrofit provideTODORetrofit(){
        //TODO
        return new Retrofit.Builder()
                .baseUrl("http://wechat.stu.edu.cn/webservice_oa/oa_stu_/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    @TestRetrofit
    @Provides
    @Singleton
    public Retrofit provideTESTRetrofit(){
        Log.d(TAG, "provideTESTRetrofit: ");
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            // Log信息拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//这里可以选择拦截级别

            //设置 Debug Log 模式
            builder.addInterceptor(loggingInterceptor);
        }

        OkHttpClient client = builder.build();
        return new Retrofit.Builder()
                .baseUrl("http://118.126.92.214:8083/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
}

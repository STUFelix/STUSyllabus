package com.example.daidaijie.syllabusapplication.todo;

import com.example.daidaijie.syllabusapplication.todo.Bean.TodoBean;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HttpMethods {
    public static final String BASE_URL = "https://stuapps.com/";

    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;
    private TodoApi todoApi;

    //构造方法私有
    private HttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        todoApi = retrofit.create(TodoApi.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance(){
        return SingletonHolder.INSTANCE;
    }


    public void addNewTask(Subscriber<TodoBean> subscriber, int uid, String token, String label, String title, String content, String startTime, String deadlineTime, int priority){
        todoApi.addNewTask(uid, token, label, title, content, startTime, deadlineTime, priority)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    public void getAllTask(Subscriber<TodoBean> subscriber, int uid, String token, int mode, int page_index, int page_size) {
        todoApi.getAllTask(uid, token, mode, page_index, page_size)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    public void editTask(Subscriber<TodoBean> subscriber, int uid, String token, int todoID, String label, String title, String content, String startTime, String deadlineTime, int priority) {
        todoApi.editTask(uid, token, todoID, label, title, content, startTime, deadlineTime, priority)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    public void deleteTask(Subscriber<TodoBean> subscriber, int uid, String token, int todoID) {
        todoApi.deleteTask(uid, token, todoID)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
//    public void updateTask(Subscriber<TodoBean> subscriber, int uid, String token, int todoID, int status) {
//        todoApi.updateStatus(uid, token, todoID, status)
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);
//    }

}
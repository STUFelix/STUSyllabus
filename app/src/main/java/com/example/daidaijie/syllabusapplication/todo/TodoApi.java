package com.example.daidaijie.syllabusapplication.todo;

import com.example.daidaijie.syllabusapplication.bean.HttpResult;
import com.example.daidaijie.syllabusapplication.todo.bean.HttpBean;
import com.example.daidaijie.syllabusapplication.todo.bean.TODOAllBean;
import com.example.daidaijie.syllabusapplication.todo.dataTemp.TaskBeanFromNet;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 16zhchen on 2018/9/28.
 */

public interface TodoApi {


    @GET("extension/api/v2/todo")
    Observable<TODOAllBean> getAllTask(@Query("uid") int uid,
                                       @Query("token") String token,
                                       @Query("mode") int mode,
                                       @Query("page_index") int page_index,
                                       @Query("page_size") int page_size);
    @FormUrlEncoded
    @POST("extension/api/v2/todo")
    Observable<HttpBean> addNewTask(@Field("uid") int uid,
                                  @Field("token") String token,
                                  @Field("label") String label,
                                  @Field("title") String title,
                                  @Field("content") String content,
                                  @Field("start_time") String startTime,
                                  @Field("deadline_time") String deadlineTime,
                                  @Field("img_link") String imgLink,
                                  @Field("priority") int priority);

    @FormUrlEncoded
    @PUT("extension/api/v2/todo")
    Observable<HttpBean> editTask(@Field("uid") int uid,
                                @Field("token") String token,
                                @Field("todo_id") int todoID,
                                @Field("label") String label,
                                @Field("title") String title,
                                @Field("content") String content,
                                @Field("start_time") String startTime,
                                @Field("deadline_time") String deadlineTime,
                                @Field("img_link") String imgLink,
                                @Field("priority") int priority);
    @DELETE("extension/api/v2/todo")
    Observable<HttpBean> deleteTask(@Header("uid") int uid,
                                    @Header("token") String token,
                                    @Header("todoid") int todoID);
    @FormUrlEncoded
    @PUT("extension/api/v2/todo_status")
    Observable<HttpBean> updateStatus(@Field("uid") int uid,
                                      @Field("token") String token,
                                      @Field("todo_id") int id,
                                      @Field("status") int status);
}

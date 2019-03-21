package com.example.daidaijie.syllabusapplication.todo;

import com.example.daidaijie.syllabusapplication.todo.Bean.TodoBean;

import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import rx.Observable;

public interface TodoApi {

    //获取个人todo
    @GET("extension/api/v2/todo")
    Observable<TodoBean> getAllTask(@Query("uid") int uid,
                                    @Query("token") String token,
                                    @Query("mode") int mode,
                                    @Query("page_index") int page_index,
                                    @Query("page_size") int page_size);

    //新增todo
    @FormUrlEncoded
    @POST("extension/api/v2/todo")
    Observable<TodoBean> addNewTask(@Field("uid") int uid,
                                    @Field("token") String token,
                                    @Field("label") String label,
                                    @Field("title") String title,
                                    @Field("content") String content,
                                    @Field("start_time") String startTime,
                                    @Field("deadline_time") String deadlineTime,
//                                    @Field("img_link") String imgLink,
                                    @Field("priority") int priority);

    //修改todo
    @FormUrlEncoded
    @PUT("extension/api/v2/todo")
    Observable<TodoBean> editTask(@Field("uid") int uid,
                                  @Field("token") String token,
                                  @Field("todo_id") int todoID,
                                  @Field("label") String label,
                                  @Field("title") String title,
                                  @Field("content") String content,
                                  @Field("start_time") String startTime,
                                  @Field("deadline_time") String deadlineTime,
//                                  @Field("img_link") String imgLink,
                                  @Field("priority") int priority);

    //删除todo
    @DELETE("extension/api/v2/todo")
    Observable<TodoBean> deleteTask(@Header("uid") int uid,
                                    @Header("token") String token,
                                    @Header("todoid") int todoID);

//    //更新todo状态
//    @FormUrlEncoded
//    @PUT("extension/api/v2/todo_status")
//    Observable<TodoBean> updateStatus(@Field("uid") int uid,
//                                      @Field("token") String token,
//                                      @Field("todo_id") int todoID,
//                                      @Field("status") int status);
}

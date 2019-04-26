package com.example.daidaijie.syllabusapplication.syllabus.LessonEvaluation;

import com.example.daidaijie.syllabusapplication.bean.HttpResult;
import com.example.daidaijie.syllabusapplication.syllabus.LessonEvaluation.bean.EvalAllBean;
import com.example.daidaijie.syllabusapplication.syllabus.LessonEvaluation.bean.EvalBeanSimple;
import com.example.daidaijie.syllabusapplication.syllabus.LessonEvaluation.bean.PostEvalBean;
import com.example.daidaijie.syllabusapplication.todo.bean.HttpBean;

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
 * Created by 16zhchen on 2018/9/19.
 */

public interface EvalApi {

    @GET("extension/api/v2/eva")
    Observable<EvalAllBean> getAllLessonEval(@Query("uid") int uid,
                                             @Query("token") String token,
                                             @Query("mode") int mode,
                                             @Query("page_index") int page_index,
                                             @Query("page_size") int page_size);

    @FormUrlEncoded
    @POST("extension/api/v2/eva")
    Observable<HttpBean> addLessonEval(@Field("uid") int uid,
                                        @Field("token") String token,
                                        @Field("class_id") int classID,
                                        @Field("score") int score,
                                        @Field("tags") String tags,
                                        @Field("content") String content);

    @FormUrlEncoded
    @PUT("extension/api/v2/eva")
    Observable<HttpBean> editLessonEval(@Field("uid") int uid,
                                        @Field("token") String token,
                                        @Field("eva_id") int evaID,
                                        @Field("score") int score,
                                        @Field("tags") String tags,
                                        @Field("content") String content);

    @DELETE("extension/api/v2/eva")
    Observable<HttpBean> deleteTask(@Header("uid") int uid,
                                    @Header("token") String token,
                                    @Header("evaid") int evaID);
}

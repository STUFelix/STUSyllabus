package com.example.daidaijie.syllabusapplication.Modulefindlost.bean;


import com.example.daidaijie.syllabusapplication.bean.ThumbUp;
import com.example.daidaijie.syllabusapplication.bean.ThumbUpReturn;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 16zhchen on 2018/12/14.
 */

public interface InfoApi {
    @GET("interaction/api/v2/posts")
    Observable<FindHttpBean> getAllTask();

    @GET("interaction/api/v2/post_sort")
    Observable<FindHttpBean> getOnePageTask(@Query("topic_id") int topic_id,
                                            @Query("page_index") int page_index);
//    @Headers("Content-Type: application/json")
////    @FormUrlEncoded
////    @POST("interaction/api/v2/like")
    @POST("interaction/api/v2/like")
    Observable<ThumbUpReturn> like(@Body ThumbUp thumbUp);

    @DELETE("interaction/api/v2/like")
    Observable<ThumbUpReturn> unlike(@Header("post_id") int id,
                                     @Header("uid") int uid,
                                     @Header("token") String token);
}

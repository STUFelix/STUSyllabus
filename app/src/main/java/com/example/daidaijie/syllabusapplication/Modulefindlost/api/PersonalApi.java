package com.example.daidaijie.syllabusapplication.Modulefindlost.api;

import com.example.daidaijie.syllabusapplication.Modulefindlost.bean.InfoDeleteReturn;
import com.example.daidaijie.syllabusapplication.Modulefindlost.bean.PersonalHttpBean;
import com.example.daidaijie.syllabusapplication.todo.bean.HttpBean;

import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by daidaijie on 16-8-9.
 * 获取最新的posts
 */
public interface PersonalApi {
    @GET("extension/api/v2/personal_findlosts")
    Observable<PersonalHttpBean> getPersonalPost(@Query("uid") int uid,
                                             @Query("token") String token,
                                             @Query("page_index") int page_index);

    @DELETE("/extension/api/v2/findlost")
    Observable<InfoDeleteReturn> deletePost(@Header("findlostid") int post_id, @Header("uid") int uid, @Header("token") String token);

//    @PUT("/extension/api/v2/findlost")
//    Observable<InfoDeleteReturn> modifyPost(@Header("findlostid") int post_id, @Header("uid") int uid, @Header("token") String token);
    @FormUrlEncoded
    @PUT("/extension/api/v2/findlost")
    Observable<HttpBean> updatePost(@Field("uid") int uid,
                                  @Field("token") String token,
                                  @Field("findlost_id") int findlost_id ,
                                  @Field("kind") int kind,
                                  @Field("title") String title,
                                  @Field("description") String description,
                                  @Field("location") String location,
                                  @Field("contact") String contact,
                                  @Field("img_link") String imgLink);
}

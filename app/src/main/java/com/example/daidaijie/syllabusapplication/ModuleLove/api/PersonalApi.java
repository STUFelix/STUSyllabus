package com.example.daidaijie.syllabusapplication.ModuleLove.api;

import com.example.daidaijie.syllabusapplication.ModuleLove.bean.InfoDeleteReturn;
import com.example.daidaijie.syllabusapplication.ModuleLove.bean.InfoHttpBean;
import com.example.daidaijie.syllabusapplication.ModuleLove.bean.PersonalHttpBean;

import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by daidaijie on 16-8-9.
 * 获取最新的posts
 */
public interface PersonalApi {
    @GET("interaction/api/v2/personal_anonymous")
    Observable<InfoHttpBean> getPersonalPost(@Query("uid") int uid,
                                             @Query("token") String token);

    @DELETE("/interaction/api/v2/anonymous")
    Observable<InfoDeleteReturn> deletePost(@Header("id") int post_id, @Header("uid") int uid, @Header("token") String token);

}

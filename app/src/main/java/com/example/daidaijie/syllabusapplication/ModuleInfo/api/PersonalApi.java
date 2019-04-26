package com.example.daidaijie.syllabusapplication.ModuleInfo.api;

import com.example.daidaijie.syllabusapplication.ModuleInfo.bean.InfoDeleteReturn;
import com.example.daidaijie.syllabusapplication.ModuleInfo.bean.InfoHttpBean;
import com.example.daidaijie.syllabusapplication.ModuleInfo.bean.PersonalHttpBean;

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
    @GET("interaction/api/v2/personal_post")
    Observable<InfoHttpBean> getPersonalPost(@Query("uid")int uid,
                                        @Query("token")String token,
                                        @Query("page_index") int page_index);
    @GET("interaction/api/v2/personal_comment")
    Observable<PersonalHttpBean> getPersonalComment(@Query("uid")int uid,
                                                    @Query("token")String token,
                                                    @Query("page_index") int page_index);

    @DELETE("/interaction/api/v2/post")
    Observable<InfoDeleteReturn> deletePost(@Header("id") int post_id, @Header("uid") int uid, @Header("token") String token);

    @DELETE("/interaction/api/v2/comment")
    Observable<InfoDeleteReturn> deleteComment(@Header("id") int post_id, @Header("uid") int uid, @Header("token") String token);

}

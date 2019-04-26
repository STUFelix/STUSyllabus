package com.example.daidaijie.syllabusapplication.Modulefindlost.api;

import com.example.daidaijie.syllabusapplication.Modulefindlost.bean.FindHttpBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by daidaijie on 16-8-9.
 * 获取最新的posts
 */
public interface FIndApi {
    @GET("extension/api/v2/findlosts")
    Observable<FindHttpBean> getFinds(@Query("uid") int uid,
                                        @Query("token")String token,
                                        @Query("kind") int kind,
                                        @Query("page_index") int page_index);
}

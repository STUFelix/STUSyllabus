package com.example.daidaijie.syllabusapplication.ModuleInfo.api;

import com.example.daidaijie.syllabusapplication.ModuleInfo.bean.InfoHttpBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by daidaijie on 16-8-9.
 * 获取最新的posts
 */
public interface CirclesApi {
    @GET("interaction/api/v2/post_sort")
    Observable<InfoHttpBean> getCircles(@Query("topic_id")int topic_id,
                                         @Query("page_index") int page_index);
}

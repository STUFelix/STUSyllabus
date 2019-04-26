package com.example.daidaijie.syllabusapplication.ModuleLove.api;

import com.example.daidaijie.syllabusapplication.ModuleLove.bean.InfoHttpBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by daidaijie on 16-8-9.
 * 获取最新的posts
 */
public interface CirclesApi {
    @GET("interaction/api/v2/anonymous")
    Observable<InfoHttpBean> getCircles(@Query("page_index") int page_index);
}

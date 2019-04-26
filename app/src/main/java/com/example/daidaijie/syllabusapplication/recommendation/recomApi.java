package com.example.daidaijie.syllabusapplication.recommendation;

import com.example.daidaijie.syllabusapplication.recommendation.bean.CourseBean;
import com.example.daidaijie.syllabusapplication.recommendation.bean.HttpResultRecom;
import com.example.daidaijie.syllabusapplication.recommendation.bean.Q1Bean;
import com.example.daidaijie.syllabusapplication.recommendation.bean.Q2M1Bean;
import com.example.daidaijie.syllabusapplication.recommendation.bean.Q2M2Bean;
import com.example.daidaijie.syllabusapplication.recommendation.bean.Q3ResultBean;
import com.example.daidaijie.syllabusapplication.recommendation.bean.TeacherBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;


public interface recomApi {
    @FormUrlEncoded
    @POST("extension/api/v2/eva/fuzzy_search_1")
    Observable<HttpResultRecom<Q1Bean<CourseBean>>> Q1_getfuzzyResultBym1(@Field("mode") int mode,
                                                                          @Field("fuzzy_input") String keyword);
    @FormUrlEncoded
    @POST("extension/api/v2/eva/fuzzy_search_1")
    Observable<HttpResultRecom<Q1Bean<TeacherBean>>> Q1_getfuzzyResultBym2(@Field("mode") int mode,
                                                                           @Field("fuzzy_input") String keyword);

    @FormUrlEncoded
    @POST("extension/api/v2/eva/fuzzy_search_1")
    Observable<HttpResultRecom<Q1Bean<String>>> Q1_getfuzzyResultBym3(@Field("mode") int mode,
                                                                      @Field("fuzzy_input") String keyword);

    //规范参数用
    @FormUrlEncoded
    @POST("extension/api/v2/eva/fuzzy_search_2")
    Observable<HttpResultRecom<Q2M1Bean>> Q2_getDetailResultBym1(@Field("mode") int mode,
                                                                 @Field("cid") int cid);

    //规范参数用
    @FormUrlEncoded
    @POST("extension/api/v2/eva/fuzzy_search_2")
    Observable<HttpResultRecom<Q2M2Bean>> Q2_getDetailResultBym2(@Field("mode") int mode,
                                                                 @Field("teacher_id") int teacherID);
    //规范参数用
    @FormUrlEncoded
    @POST("extension/api/v2/eva/fuzzy_search_2")
    Observable<HttpResultRecom<Q1Bean<CourseBean>>> Q2_getDetailResultBym3(@Field("mode") int mode,
                                                                           @Field("department") String department);
    @FormUrlEncoded
    @POST("extension/api/v2/eva/fuzzy_search_3")
    Observable<Q3ResultBean> Q3_getScore(@Field("cid") int cid,
                                         @Field("teacher_id") int teacherID);
}

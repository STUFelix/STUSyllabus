package com.example.daidaijie.syllabusapplication.mystu.request;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Getdata_Interface {

    /**根据用户账号和密码拿到cookies*/
    @POST("/mystu/api/v2/cookies")
    @FormUrlEncoded
    Call<ResponseBody> getCookies(@Field("username") String username,@Field("password") String password);

    /**根据Cookie和学年与学期拿到课程列表*/
    @POST("/mystu/api/v2/courses")
    @FormUrlEncoded
    Call<ResponseBody> getCourseList(@Header("Cookie") String cookie, @Field("years") String years, @Field("semester") int id);

    /**根据Cookie和course_linkid拿到mystu课件*/
    @POST("/mystu/api/v2/coursefiles")
    @FormUrlEncoded
    Call<ResponseBody> getCourseWare(@Header("Cookie") String cookie, @Field("course_linkid") String courselinkid);

    /**根据Cookie和folder_linkid拿到mystu 文件夹 的课件*/
    @POST("/mystu/api/v2/folderfiles")
    @FormUrlEncoded
    Call<ResponseBody> getCourseWareFolder(@Header("Cookie") String cookie, @Field("folder_linkid") String folderlinkid);

    /**根据Cookie和course_linkid拿到课程作业列表*/
    @POST("/mystu/api/v2/assignslink")
    @FormUrlEncoded
    Call<ResponseBody> getCourseWorkList(@Header("Cookie") String cookie, @Field("course_linkid") String courselinkid);

    /**根据Cookie和assign_linkid拿到课程作业详情*/
    @POST("/mystu/api/v2/assignment")
    @FormUrlEncoded
    Call<ResponseBody> getCourseDetails(@Header("Cookie") String cookie, @Field("assign_linkid") String assignlinkid);

    /**根据Cookie和pageSize与pageNo拿到课程讨论*/
    @POST("/mystu/api/v2/index")
    @FormUrlEncoded
    Call<ResponseBody> getCourseIndex(@Header("Cookie")String cookie,@Field("pageSize") int pageSize,@Field("pageNo") int pageNo);

}

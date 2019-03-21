package com.example.daidaijie.syllabusapplication.mealcard;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

import rx.Observable;

public interface RequestUrl {

    /**卡上余额，返回值Cookie会被用于请求消费流水账单*/
    @FormUrlEncoded
    @POST("card/api/v2/balance")
    Call<YKTBean> getBalance(@Field("username") String username, @Field("password") String password);

    /**几天内的饭卡消费流水账单*/
    @FormUrlEncoded
    @POST("card/api/v2/detail")
    Call<TheBean>  getDetail(@Header ("Cookie") String cookie, @Field("username") String username, @Field("password") String password, @Field("days") String days);

    /**某段时间内的消费流水账单*/
    @FormUrlEncoded
    @POST("card/api/v2/detail")
    Call<ResponseBody> getDateDetail(@Header("Cookie") String cookie,@Field("username") String username,@Field("password") String password,
                                     @Field("start_year") String start_year,@Field("start_month") String start_month,@Field("start_day") String start_day,
                                     @Field("end_year") String end_year,@Field("end_month") String end_month,@Field("end_day") String end_day  );
}

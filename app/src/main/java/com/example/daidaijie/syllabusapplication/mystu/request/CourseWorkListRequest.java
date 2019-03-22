package com.example.daidaijie.syllabusapplication.mystu.request;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CourseWorkListRequest {

    private String Cookie;
    private String course_linkid;
    private Handler courseworklistHandler;
    private Context context;
    private SwipeRefreshLayout refreshLayout;

    public CourseWorkListRequest() {

    }

    public CourseWorkListRequest(String Cookie, String course_linkid, Handler worklistHandler,Context context,SwipeRefreshLayout refreshLayout) {
        this.Cookie = Cookie;
        this.course_linkid = course_linkid;
        this.courseworklistHandler = worklistHandler;
        this.context = context;
        this.refreshLayout =refreshLayout;
    }

    public void getWorkList() {
        Getdata_Interface retrofit =new RetrofitCreate().getRetrofit();

        Call<ResponseBody> call = retrofit.getCourseWorkList(Cookie, course_linkid);

        call.enqueue(new Callback<ResponseBody>() {

            @Override

            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int code = response.code();
                try {
                    if (response.body()!=null&&code!=400) {
                        String str = response.body().string();
                        JSONObject jsonObject = new JSONObject(str);

                        Message worklistmsg = new Message();
                        worklistmsg.what = 30003;
                        worklistmsg.obj = jsonObject;

                        courseworklistHandler.sendMessage(worklistmsg);
                    }else {
                        Toast.makeText(context,"Error：请求失败",Toast.LENGTH_SHORT).show();
                        refreshLayout.setRefreshing(false);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            //请求失败时回调
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Toast.makeText(context,"作业列表请求失败\n请检测网络后刷新",Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
            }
        });

    }

}

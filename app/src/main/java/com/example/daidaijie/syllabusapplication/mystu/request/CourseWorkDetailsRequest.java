package com.example.daidaijie.syllabusapplication.mystu.request;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import com.example.daidaijie.syllabusapplication.mystu.request.Getdata_Interface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CourseWorkDetailsRequest {

    private String Cookie;
    private String assign_linkid;
    private Handler workdetailsHandler;
    private Context context;
    private SwipeRefreshLayout swipeRefreshLayout;

    public CourseWorkDetailsRequest(){

    }

    public CourseWorkDetailsRequest(String Cookie,String assign_linkid,Handler workdetailsHandler,Context context,SwipeRefreshLayout swipeRefreshLayout){
        this.Cookie =Cookie;
        this.assign_linkid =assign_linkid;
        this.workdetailsHandler=workdetailsHandler;
        this.context =context;
        this.swipeRefreshLayout =swipeRefreshLayout;
    }

    public void getWorkList(){
        Getdata_Interface retrofit =new RetrofitCreate().getRetrofit();

        Call<ResponseBody> call = retrofit.getCourseDetails(Cookie, assign_linkid);

        call.enqueue(new Callback<ResponseBody>() {

            @Override

            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int code = response.code();
                try {
                    if (response.body()!=null&&code!=400) {
                        String str = response.body().string();
                        JSONObject jsonObject = new JSONObject(str);
                        Message workdetailsmsg = new Message();
                        workdetailsmsg.what = 40004;
                        workdetailsmsg.obj = jsonObject;
                        workdetailsHandler.sendMessage(workdetailsmsg);
                    }else {
                        Toast.makeText(context,"Error：请求失败",Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Toast.makeText(context,"作业详情请求失败\n请检测网络后刷新",Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);

            }
        });
    }
}

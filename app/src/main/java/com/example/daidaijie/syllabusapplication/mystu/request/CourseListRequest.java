package com.example.daidaijie.syllabusapplication.mystu.request;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseListRequest {

    private Context context;
    private Handler mcourseListHandler;
    public  String cookies;
    public  String years;
    public  int semester;
    public SwipeRefreshLayout swipeRefreshLayout;

    public CourseListRequest(){

    }

    public CourseListRequest(String tCookies, String tYears, int tSemester, Handler thandler,Context context,SwipeRefreshLayout swipeRefreshLayout){
    this.cookies = tCookies;
    this.years = tYears;
    this.semester= tSemester;
    this.mcourseListHandler= thandler;
    this.context = context;
    this.swipeRefreshLayout = swipeRefreshLayout;
    }

    public  void getCourseList() {

        Getdata_Interface retrofit =new RetrofitCreate().getRetrofit();

        Call<ResponseBody> call = retrofit.getCourseList(cookies,years,semester);

        call.enqueue(new Callback<ResponseBody>() {

            @Override

            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                   int code= response.code();
                try {
                    if (response.body()!=null&&code!=400){
                        String str =response.body().string();
                        JSONObject jsonObject = new JSONObject(str);
                        Message courseListmsg = new Message();
                        courseListmsg.what = 20002;
                        courseListmsg.obj = jsonObject;
                        mcourseListHandler.sendMessage(courseListmsg);
                    }else {
                        Toast.makeText(context,"Error：请求失败",Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Toast.makeText(context,"课程列表请求失败\n请检测网络后刷新",Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}

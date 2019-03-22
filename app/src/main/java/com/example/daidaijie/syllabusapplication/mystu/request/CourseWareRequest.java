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

public class CourseWareRequest {

    private Handler coursewareHandler;
    public  String cookies;
    public  String course_linkid;
    private Context context;
    private SwipeRefreshLayout refreshLayout;

    public CourseWareRequest(){

    }

    public CourseWareRequest(String tCookies, String tYears,Handler thandler,Context context,SwipeRefreshLayout refreshLayout){
        this.cookies = tCookies;
        this.course_linkid = tYears;
        this.coursewareHandler =thandler;
        this.context=context;
        this.refreshLayout =refreshLayout;
    }

    public  void getCourseWare() {
        Getdata_Interface retrofit =new RetrofitCreate().getRetrofit();

        Call<ResponseBody> call = retrofit.getCourseWare(cookies,course_linkid);

        call.enqueue(new Callback<ResponseBody>() {

            @Override

            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                int code = response.code();
                try {
                    if(response.body()!=null&&code!=400){
                    String str =response.body().string();
                    JSONObject jsonObject = new JSONObject(str);

                    Message courseListmsg = new Message();
                    courseListmsg.what = 50005;
                    courseListmsg.obj = jsonObject;
                    coursewareHandler.sendMessage(courseListmsg);
                    }else {
                        refreshLayout.setRefreshing(false);
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
                Toast.makeText(context,"课件列表请求失败\n请检测网络后刷新",Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
            }
        });
    }
}

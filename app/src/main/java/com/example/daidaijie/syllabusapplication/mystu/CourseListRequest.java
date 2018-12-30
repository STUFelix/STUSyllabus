package com.example.daidaijie.syllabusapplication.mystu;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
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

public class CourseListRequest {

    private Context context;
    private Handler mcourseListHandler;
    public  String cookies;
    public  String years;
    public  int semester;


    CourseListRequest(){

    }

    CourseListRequest(String tCookies, String tYears, int tSemester, Handler thandler,Context context){
    this.cookies = tCookies;
    this.years = tYears;
    this.semester=tSemester;
    this.mcourseListHandler=thandler;
    this.context =context;
    }

    public  void getCourseList() {

        //步骤4:创建Retrofit对象

        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl("https://class.stuapps.com") // 设置 网络请求 Url

                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)

                .build();

        // 步骤5:创建 网络请求接口 的实例

        Getdata_Interface cookiesRequest = retrofit.create(Getdata_Interface.class);


        Call<ResponseBody> call = cookiesRequest.getCourseList(cookies,years,semester);


        //步骤6:发送网络请求(异步)

        call.enqueue(new Callback<ResponseBody>() {

            //请求成功时回调

            @Override

            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                // 请求处理,输出结果

                try {
                    String str =response.body().string();
                    JSONObject jsonObject = new JSONObject(str);

                    Message courseListmsg = new Message();
                    courseListmsg.what = 20002;
                    courseListmsg.obj = jsonObject;

                    mcourseListHandler.sendMessage(courseListmsg);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            //请求失败时回调
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Toast.makeText(context,"课程列表请求失败",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

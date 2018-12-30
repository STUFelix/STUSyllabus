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

public class CourseWareRequest {

    private Handler coursewareHandler;
    public  String cookies;
    public  String course_linkid;
    private Context context;

    CourseWareRequest(){

    }

    CourseWareRequest(String tCookies, String tYears,Handler thandler,Context context){
        this.cookies = tCookies;
        this.course_linkid = tYears;
        this.coursewareHandler =thandler;
        this.context=context;
    }

    public  void getCourseWare() {


        //步骤4:创建Retrofit对象

        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl("https://class.stuapps.com") // 设置 网络请求 Url

                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)

                .build();

        // 步骤5:创建 网络请求接口 的实例

        Getdata_Interface wareRequest = retrofit.create(Getdata_Interface.class);


        Call<ResponseBody> call = wareRequest.getCourseWare(cookies,course_linkid);


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
                    courseListmsg.what = 50005;
                    courseListmsg.obj = jsonObject;

                    /**判空，提示用户。

                    String judgeStr =jsonObject.toString(); // 若jsonObject为空，然后toString会报空指针异常的，故后面的无法显示！
                    if(judgeStr.length() ==0 || judgeStr ==null){
                        Toast.makeText(context,"此课程暂无课件",Toast.LENGTH_LONG).show();
                    }
                     * */

                    coursewareHandler.sendMessage(courseListmsg);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            //请求失败时回调
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Toast.makeText(context,"课件列表数据请求失败",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

package com.example.daidaijie.syllabusapplication.mystu.request;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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

public class DiscussionRequest {

    private Context context;
    private Handler mcourseIndexHandler;
    public  String cookies;
    private int pageSize;
    private int pageNo;



    public DiscussionRequest(){

    }

    public DiscussionRequest(String cookies,int pageSize,int pageNo, Handler handler,Context context){
        this.cookies = cookies;
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        this.mcourseIndexHandler=handler;
        this.context =context;
    }

    public  void getCourseDiscussion() {

        //步骤4:创建Retrofit对象

        Retrofit retrofit = new Retrofit.Builder()

                //.baseUrl("https://class.stuapps.com") // 设置 网络请求 Url

                .baseUrl("http://118.126.92.214:8083/")

                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)

                .build();

        // 步骤5:创建 网络请求接口 的实例

        Getdata_Interface cookiesRequest = retrofit.create(Getdata_Interface.class);


        Call<ResponseBody> call = cookiesRequest.getCourseIndex(cookies,pageSize,pageNo);


        //步骤6:发送网络请求(异步)

        call.enqueue(new Callback<ResponseBody>() {

            //请求成功时回调

            @Override

            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                // 请求处理,输出结果

                try {
                    String str =response.body().string();
                    JSONObject jsonObject = new JSONObject(str);

                    Message courseDiscussion = new Message();
                    courseDiscussion.what = 70007;
                    courseDiscussion.obj = jsonObject;
                    Log.i("DiscussionRequest",str);
                    mcourseIndexHandler.sendMessage(courseDiscussion);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            //请求失败时回调
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Toast.makeText(context,"课程讨论请求失败\n请检测网络后刷新",Toast.LENGTH_SHORT).show();
            }
        });
    }
}





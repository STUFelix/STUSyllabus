package com.example.daidaijie.syllabusapplication.mystu;


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

public  class  CookiesRequest {

    private Context context;
    private String cookiesUserName;
    private String cookiesPassword;
    private Handler cookiesHandler;
    private SwipeRefreshLayout refreshLayout;

    CookiesRequest(){

    }

    CookiesRequest(String cookiesUserName, String cookiesPassword, Handler cookiesHandler,Context context,SwipeRefreshLayout refreshLayout) {
        this.cookiesUserName = cookiesUserName;
        this.cookiesPassword = cookiesPassword;
        this.cookiesHandler = cookiesHandler;
        this.context = context;
        this.refreshLayout = refreshLayout;
    }

    public void getCookies() {

        //步骤4:创建Retrofit对象

        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl("https://class.stuapps.com") // 设置 网络请求 Url

                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)

                .build();


        // 步骤5:创建 网络请求接口 的实例

        Getdata_Interface cookiesRequest = retrofit.create(Getdata_Interface.class);

        Call<ResponseBody> call = cookiesRequest.getCookies(cookiesUserName,cookiesPassword);


        //步骤6:发送网络请求(异步)

        call.enqueue(new Callback<ResponseBody>() {

            //请求成功时回调

            @Override

            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                // 请求处理,输出结果

                try {

                    String str =response.body().string();
                    JSONObject jsonObject = new JSONObject(str);
                    String tcookies =jsonObject.getString("Cookie");

                    Message cookiesmsg = new Message();

                    /*Bundle data = new Bundle();
                    data.putString("Cookie",tcookies);
                    cookiesmsg.setData(data);*/

                    cookiesmsg.what =10001;
                    cookiesmsg.obj=tcookies;
                    cookiesHandler.sendMessage(cookiesmsg);


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            //请求失败时回调
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Toast.makeText(context,"Cookies请求失败！\n请检查网络后重新刷新",Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
            }
        });
    }
}

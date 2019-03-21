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

public  class  CookiesRequest  {

    private String cookiesUserName;
    private String cookiesPassword;
    private Handler mainactivity_cookiesHandler;
    private Handler mystumainactivity_cookiesHandler;
    private Context context;
    private SwipeRefreshLayout swipeRefreshLayout;

    public CookiesRequest(){

    }

    public CookiesRequest(Handler mystumainactivity_cookiesHandler,String cookiesUserName, String cookiesPassword,Context context ,SwipeRefreshLayout swipeRefreshLayout){
        this.cookiesUserName = cookiesUserName;
        this.cookiesPassword = cookiesPassword;
        this.mystumainactivity_cookiesHandler = mystumainactivity_cookiesHandler;
        this.context = context;
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    public CookiesRequest(String cookiesUserName,String cookiesPassword,Handler mainactivity_cookiesHandler ){
        this.cookiesUserName = cookiesUserName;
        this.cookiesPassword = cookiesPassword;
        this.mainactivity_cookiesHandler = mainactivity_cookiesHandler;
    }

    public void getCookies() {

        Getdata_Interface retrofit =new RetrofitCreate().getRetrofit();

        Call<ResponseBody> call = retrofit.getCookies(cookiesUserName,cookiesPassword);

        call.enqueue(new Callback<ResponseBody>() {


            @Override

            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                try {
                    if(response.body()!=null){
                    String str =response.body().string();
                    JSONObject jsonObject = new JSONObject(str);
                    String tcookies =jsonObject.getString("Cookie");

                    if (mainactivity_cookiesHandler  !=null && mystumainactivity_cookiesHandler ==null){
                        Message message = Message.obtain();
                        message.obj = tcookies;
                        message.what = 10002;
                        mainactivity_cookiesHandler.sendMessage(message);
                    }else {
                        Message message = Message.obtain();
                        message.obj = tcookies;
                        message.what = 10001;
                        mystumainactivity_cookiesHandler.sendMessage(message); }
                    }else {
                        if(swipeRefreshLayout!=null)    swipeRefreshLayout.setRefreshing(false);
                        if (context != null)   Toast.makeText(context,"Error：请求失败",Toast.LENGTH_SHORT).show();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

                if(swipeRefreshLayout!=null)    swipeRefreshLayout.setRefreshing(false);
                if (context != null)   Toast.makeText(context,"请求失败，请检测网络后刷新",Toast.LENGTH_SHORT).show();

            }
        });
    }


}

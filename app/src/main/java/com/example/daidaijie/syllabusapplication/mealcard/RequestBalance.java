package com.example.daidaijie.syllabusapplication.mealcard;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;

import retrofit2.Callback;
import retrofit2.Response;

public class RequestBalance {

    private String username;
    private String password;

    private String mCookie;
    private String mBalance;
    private String str;

    private Handler mHandler;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Context context;

    RequestBalance(String username, String password, Handler mHandler, SwipeRefreshLayout swipeRefreshLayout,Context context){
        this.username = username;
        this.password = password;
        this.mHandler = mHandler;
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.context = context;
    }


    public void getBalance(){
    RequestUrl requestUrl =new RetrofitCreate().getRetrofit();

    Call<YKTBean> call = requestUrl.getBalance(username,password);

        call.enqueue(new Callback<YKTBean>() {

        @Override
        public void onResponse(Call<YKTBean> call , Response<YKTBean> response) {
            mCookie = response.headers().get("Cookie");
            mBalance = response.body().getBalance();
            str = mBalance +"#"+mCookie;

            Message message =Message.obtain();
            message.obj =str;
            message.what =10001;
            mHandler.sendMessage(message);
        }

        @Override
        public void onFailure(Call<YKTBean> call, Throwable throwable) {
            Log.i("RequestBalance","error :"+throwable);
            Toast.makeText(context,"数据请求失败！\n请检查网络后重新刷新",Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
        }
        });

    }
}

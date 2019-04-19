package com.example.daidaijie.syllabusapplication.mealcard;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestDetail {
    private String username;
    private String password;
    private String cookie;
    private String days;
    private Handler mHandle;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Context context;

    private List<Map<String,String>> DList = new ArrayList<Map<String,String>>();

    RequestDetail(){

    }

    RequestDetail(String cookie,String username,String password,String days,Handler handler,SwipeRefreshLayout swipeRefreshLayout, Context context){
        this.cookie = cookie;
        this.username = username;
        this.password = password;
        this.days = days;
        this.mHandle =handler;
        this.swipeRefreshLayout =swipeRefreshLayout;
        this.context = context;
    }

    public  void getDetail(){
        RequestUrl requestUrl =new RetrofitCreate().getRetrofit();

        Call<TheBean> call = requestUrl.getDetail(cookie,username,password,days);

        call.enqueue(new Callback<TheBean>() {

            @Override
            public void onResponse(Call<TheBean> call , Response<TheBean> response) {
                int code =response.code();
                if (response.body()!=null && code!=400) {
                    TheBean bean = response.body();
                    toParseBean(bean);
                    Message message = Message.obtain();
                    message.obj = DList;
                    message.what = 20001;
                    mHandle.sendMessage(message);
                }else {
                    Toast.makeText(context,"Error：请求失败",Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<TheBean> call, Throwable throwable) {
                throwable.printStackTrace();
                Toast.makeText(context,"数据请求失败！\n请检查网络后重新刷新",Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }

        });

    }

    public void toParseBean(TheBean theBean){
        for (int i=0;i<theBean.getLength();i++){
            try {
                Map<String,String> map =new HashMap<>();
                String date = theBean.getDetail().get(i).getDate();
                String time = theBean.getDetail().get(i).getTime();
                String flow = theBean.getDetail().get(i).getFlow();
                String kind = theBean.getDetail().get(i).getKind();
                String name = theBean.getDetail().get(i).getName();

                map.put("date",date);
                map.put("time",time);
                map.put("flow",flow);
                map.put("kind",kind);
                map.put("name",name);
                DList.add(map);
            }catch (Exception e){
                Toast.makeText(context,"Error：异常",Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
                e.printStackTrace();
            }

        }
    }
}


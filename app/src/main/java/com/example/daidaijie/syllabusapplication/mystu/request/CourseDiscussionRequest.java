package com.example.daidaijie.syllabusapplication.mystu.request;

import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.Toast;
import com.example.daidaijie.syllabusapplication.mystu.CourseFilesBean;
import com.example.daidaijie.syllabusapplication.mystu.CourseInfoActivity;
import com.example.daidaijie.syllabusapplication.mystu.DiscussionBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public  class CourseDiscussionRequest {


    private Context mcontext;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String Cookie;
    private int pageSize;
    private Handler handler;
    private static List<DiscussionBean> discussionBean;
    public static CourseFilesBean getCourseFilesBean(int position){
        return discussionBean.get(position).getCourse_files();
    }
    private static List<Map<String,String>> Clist =new ArrayList<Map<String, String>>();
    public static List<Map<String,String>> getClist(){
        return  Clist;
    }
    private static List<Map<String,String>> Mlist =new ArrayList<Map<String, String>>();
    public static List<Map<String,String>> getMlist(){
        return  Mlist;
    }
    private int pageNo;
    private int pageNoTemp;

    public CourseDiscussionRequest(){

    }

    public CourseDiscussionRequest(String Cookie, int pageSize, int pageNo, Handler handler,Context mcontext,SwipeRefreshLayout swipeRefreshLayout,int pageNoTemp) {
        this.Cookie = Cookie;
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        this.handler = handler;
        this.mcontext =mcontext;
        this.swipeRefreshLayout =swipeRefreshLayout;
        this.pageNoTemp =pageNoTemp;
    }

    public void getCourseDiscussion() {

        Getdata_Interface retrofit =new RetrofitCreate().getRetrofit();

        Call<ResponseBody> call = retrofit.getCourseIndex(Cookie,pageSize,pageNo);

        call.enqueue(new Callback<ResponseBody>() {


            @Override

            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int code = response.code();
                try {
                        if(response!=null&&code!=400){
                            CourseInfoActivity.pageNoTemp=pageNo;
                            Log.i("CourseDiscussionRequest","CourseInfoActivity.pageNoTemp"+CourseInfoActivity.pageNoTemp);
                            String json = response.body().string();
                            toParse(json);
                            if (Clist.size()!=0) {
                                handler.sendEmptyMessage(20001);
                            }
                        }else {
                             Toast.makeText(mcontext,"Error：请求失败",Toast.LENGTH_SHORT).show();
                            swipeRefreshLayout.setRefreshing(false);
                            CourseInfoActivity.pageNo=pageNoTemp;
                            Log.i("CourseDiscussionRequest","CourseInfoActivity.pageNo"+CourseInfoActivity.pageNo);
                        }
                    } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(mcontext,"Error：异常",Toast.LENGTH_SHORT).show();
                    CourseInfoActivity.pageNo=pageNoTemp;
                    Log.i("CourseDiscussionRequest","CourseInfoActivity.pageNo"+CourseInfoActivity.pageNo);
                    }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Log.i("CourseDiscussionRequest", "onFailure: "+throwable);
                Toast.makeText(mcontext,"信息列表请求失败\n请检测网络后刷新",Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void toParse (String json){
        if(Clist.size()!=0)Clist.clear();
        if(Mlist.size()!=0)Mlist.clear();
        Gson gson =new Gson();
        discussionBean = gson.fromJson(json,new TypeToken<List<DiscussionBean>>(){}.getType());
        for (int i=0;i<discussionBean.size();i++) {
            CourseFilesBean courseFilesBean = discussionBean.get(i).getCourse_files();
            String mcontent = discussionBean.get(i).getContent();
            String course_name = discussionBean.get(i).getCourse_name();
            String teacher_name = discussionBean.get(i).getTeacher_name();
            Map<String,String> Cmap = new HashMap<String, String>();
            Cmap.put("content",mcontent);
            Cmap.put("course_name",course_name);
            Cmap.put("teacher_name",teacher_name);
            int length =courseFilesBean.getLength();
            Cmap.put("course_files_length",String.valueOf(length));
            Clist.add(Cmap);
            for (int j=0;j<length;j++){
                String filename = discussionBean.get(i).getCourse_files().getCourse_files_details().get(j).getFile_name();
                String fileurl = discussionBean.get(i).getCourse_files().getCourse_files_details().get(j).getFile_url();
                Map<String,String> Mmap = new HashMap<String, String>();
                Mmap.put("file_name",filename);
                Mmap.put("file_url",fileurl);
                Mlist.add(Mmap);
            }
        }
    }


    public static String getContent(int position) {
        if(Clist.size()>position){
        return Clist.get(position).get("content");
        }return "";
    }

    public static String getCourseName(int position){
        if(Clist.size()>position){
            return Clist.get(position).get("course_name");
        }else {
            return "";
        }
    }

    public static String getPeopleName(int position){
        if (Clist.size()>position){
            return Clist.get(position).get("teacher_name");
        }else {
            return "";
        }
    }

    public static int getCourseFilesLength(int position){
        if (Clist.size()>position){
            return Integer.parseInt(Clist.get(position).get("course_files_length"));
        }else {
            return 0;
        }
    }

    private  static long lastClickTime;
    public static boolean isFastDoubleClick(){
        long time =System.currentTimeMillis();
        long timeD =time-lastClickTime;
        if(0<timeD&&timeD<1500){/**1500毫秒*/
            return true;
        }
        lastClickTime =time;
        return false;
    }

}
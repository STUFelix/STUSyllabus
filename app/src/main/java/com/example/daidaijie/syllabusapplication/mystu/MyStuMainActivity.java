package com.example.daidaijie.syllabusapplication.mystu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import com.example.daidaijie.syllabusapplication.App;
import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.base.BaseActivity;
import com.example.daidaijie.syllabusapplication.bean.Semester;
import com.example.daidaijie.syllabusapplication.bean.UserLogin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.realm.Realm;
import io.realm.RealmResults;


public class MyStuMainActivity extends BaseActivity {


    /**Created by STUFelix
     * 2018.12
     * */

    @BindView(R.id.titleTextView)
    TextView mTitleTextView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private SwipeRefreshLayout refreshLayout;


    private String cookies ="";
    private String year = "";
    private int season = 1;
    private String cookiesUserName = "";
    private String cookiesPassword ="";

    private ListView listView;
    private CourseListAdapter mc_adapter;


    private int PcourseNum = -1;

    //接受异步信息Cookie
    private Handler cookiesHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 10001){
                cookies=msg.obj.toString();

                /**通过这个方法把handleMessage的数据传递到外部
                 * 并在整个app中可以通过
                 * */
                App app =(App)getApplication();
                app.setTCookie(cookies);

                //对课程列表进行请求，即第一个activity界面的数据请求
                CourseListRequest mCourseListRequest=new CourseListRequest(cookies,year,season,mcourseListHandler,MyStuMainActivity.this);
                mCourseListRequest.getCourseList();
            }
        }
    };

    //接受异步信息courseList
    private Handler mcourseListHandler =new Handler(){
        @Override
        public void handleMessage(Message msg){
            if (msg.what ==20002){
                if(msg.obj != null) {
                    String mcourseobj = msg.obj.toString();

                    toParseJson(mcourseobj);//解析返回的courseList 的json数据

                    toRequestAdapter();//将数据关联到adapter，并在其中嵌套活动的跳转
                }
                /**
                else{
                  还是没有解决若本学期无课程，课程无数据，如何提醒用户的问题。
                    forHint();//无用，哪怕这学期无课程，这个方法也没有被调用，可能说明都没进行sendMessage();
                }*/
            }
        }
    };

    /**
    *全局变量Plist，
    *作为toRequestAdapter方法的参数
    * */
    private List<Map<String,String>> Plist = new ArrayList<Map<String, String>>();

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        listView = (ListView) findViewById(R.id.mystu_course_listview);
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);

        /**
         * 有一个问题 如何在没网络的情况，导致网络请求失败后，然后手动操作连上了网，如何实现自动的再次网络请求，而无需手动退出，再进一次界面进行网络请求
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {//设置刷新监听器
         @Override
         public void onRefresh() {
         refreshLayout.setRefreshing(true);
        CookiesRequest mCookiesRequest = new CookiesRequest(cookiesUserName,cookiesPassword,cookiesHandler,MyStuMainActivity.this);
        mCookiesRequest.getCookies();
    }
});
         */
        setupTitleBar(mToolbar);

        getUserInfo();//获取用户账号、密码、学年与学期
        toGetCookie();//获取Cookie 然后自动请求课程列表
    }

    protected int getContentView(){
        return R.layout.mystu_courselist;
    }

/** 获取用户账号、密码、学年与学期
 **/
    public void getUserInfo(){
        Realm xRealm = Realm.getDefaultInstance();
        UserLogin cookiesUserLogin = xRealm.where(UserLogin.class).findFirst();
        cookiesUserName = cookiesUserLogin.getUsername();
        cookiesPassword = cookiesUserLogin.getPassword();
        RealmResults<Semester> semesters =xRealm.where(Semester.class).findAll();
        for(Semester semester:semesters){
            if(semester.isCurrent()==true){
                year = semester.getYearString();
                season = semester.getSeason();
            }
        }
    }

    public void toGetCookie(){
        refreshLayout.setEnabled(true);
        refreshLayout.setRefreshing(true);
        /*需要通过handler拿到用户Cookie*/
        CookiesRequest mCookiesRequest = new CookiesRequest(cookiesUserName,cookiesPassword,cookiesHandler,MyStuMainActivity.this);
        mCookiesRequest.getCookies();
    }

    /**调用此函数
     * 将数据保存在
     * 全局变量Plist**/
    private void toParseJson(String json){
        try {
            JSONObject mcourse_jsonobject = new JSONObject(json);
            PcourseNum =mcourse_jsonobject.getInt("courseNum");//无论后台给的数据是string或int，getInt方法源码有强制类型转换parseInt();
            JSONArray courses_array =  mcourse_jsonobject.getJSONArray("courses");

            for(int i=0; i<PcourseNum;i++){
                Map<String,String> map = new HashMap<String,String>();//list储存多个同名（同keyset）map必须注意的问题
                JSONObject course = courses_array.getJSONObject(i);
                String PcourseLink = course.getString("courseLink");
                String  PcourseLinkId = course.getString("courseLinkId");
                String PcourseName =course.getString("courseName");
                map.put("course_name",PcourseName);
                map.put("courseLinkId",""+PcourseLinkId);
                map.put("courseLink",PcourseLink);
                Plist.add(map);
            }

            if(PcourseNum == 0){
                forHint();
            }

            refreshLayout.setRefreshing(false);
            refreshLayout.setEnabled(false);

            /**如果本学期没有mystu，进行toast显示提示用户
             * **/

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void toRequestAdapter(){

        mc_adapter = new CourseListAdapter(this,    Plist);

        listView.setAdapter(mc_adapter);

        /**
         * 利用接口回调重写onClick方法来具体实现对应的点击事件
         *
         * 返回的参数有：
         * @param view_name 返回对应按钮的功能，如："course_resources"表示选择下载课件、
         *                  "course_work"表示选择查看作业
         *
         * @param position 返回对应的索引值（从0开始）
         *
         * @param linkid 返回对应的“linkid”（用于后续操作）
         *
         */
        mc_adapter.setClickListener(new CourseListAdapter.ClickListener()
        {
            @Override
            public void onclick(String view_name, int position, String courseLink , String courseLinkId)
            {
                if(view_name.equals("course_work")){
                    Intent  homework_intent = new Intent(MyStuMainActivity.this,CourseWorkMainActivity.class);
                    homework_intent.putExtra("courseLinkId",courseLinkId);
                    startActivity(homework_intent);

                }else if(view_name.equals("course_resources")){
                    Intent courseware_intent = new Intent(MyStuMainActivity.this,CourseWareActivity.class);
                    courseware_intent.putExtra("courseLinkId", courseLinkId);
                    startActivity(courseware_intent);
                }
            }
        });
    }
    private  void forHint(){
        AlertDialog.Builder normalDialog
                = new AlertDialog.Builder(this)
                .setTitle("  -温馨提示")
                .setMessage("\n\n本学期暂无课程\n")
                .setPositiveButton("好吧~", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        normalDialog.create().show();
    }
}



/**
 * 这个activity显示前，进行了两次请求 一次为cookie 一次为CourseList；
 * 若想优化activity启动速度 可以把cookie的请求塞到进入课表的第一个主界面去
 * */
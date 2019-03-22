package com.example.daidaijie.syllabusapplication.mystu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.daidaijie.syllabusapplication.App;
import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.base.BaseActivity;
import com.example.daidaijie.syllabusapplication.bean.Semester;
import com.example.daidaijie.syllabusapplication.bean.UserLogin;
import com.example.daidaijie.syllabusapplication.mystu.request.CookiesRequest;
import com.example.daidaijie.syllabusapplication.mystu.request.CourseListRequest;
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
    @BindView(R.id.mystu_course_listview)
    ListView listView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    private String cookies ="";
    private String year = "";
    private int season = 1;
    private String cookiesUserName = "";
    private String cookiesPassword ="";
    private CourseListAdapter mc_adapter;
    private int PcourseNum = -1;

    //接受异步信息Cookie
    private Handler mystumainactivity_cookiesHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 10001){
                cookies=msg.obj.toString();

                 //通过这个方法把handleMessage的数据传递到外部并在整个app中可以通过
                App app =(App)getApplication();
                app.setTCookie(cookies);

                if ("".equals(cookies)||cookies==null){
                    CookiesRequest mCookiesRequest = new CookiesRequest(mystumainactivity_cookiesHandler,cookiesUserName,cookiesPassword,MyStuMainActivity.this,refreshLayout);
                    mCookiesRequest.getCookies();
                }else {
                    CourseListRequest mCourseListRequest=new CourseListRequest(cookies,year,season,mcourseListHandler,MyStuMainActivity.this,refreshLayout);
                    mCourseListRequest.getCourseList();
                }
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
        getUserInfo();//获取用户账号、密码、学年与学期
        mInit();
        toRequest();
    }

    protected int getContentView(){
        return R.layout.mystu_courselist;
    }

    private void mInit() {
        refreshLayout.setEnabled(true);
        refreshLayout.setRefreshing(true);
        refreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        setupTitleBar(mToolbar);
        App app = (App) getApplication();
        cookies = app.getTCookie();
    }

    private void  toRequest(){
        if ("".equals(cookies)||cookies==null){
            CookiesRequest mCookiesRequest = new CookiesRequest(mystumainactivity_cookiesHandler,cookiesUserName,cookiesPassword,MyStuMainActivity.this,refreshLayout);
            mCookiesRequest.getCookies();
        }else {
            CourseListRequest mCourseListRequest=new CourseListRequest(cookies,year,season,mcourseListHandler,MyStuMainActivity.this,refreshLayout);
            mCourseListRequest.getCourseList();
        }

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if ("".equals(cookies)||cookies==null){
                    CookiesRequest mCookiesRequest = new CookiesRequest(mystumainactivity_cookiesHandler,cookiesUserName,cookiesPassword,MyStuMainActivity.this,refreshLayout);
                    mCookiesRequest.getCookies();
                }else {
                    CourseListRequest mCourseListRequest=new CourseListRequest(cookies,year,season,mcourseListHandler,MyStuMainActivity.this,refreshLayout);
                    mCourseListRequest.getCourseList();
                }
            }
        });
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

            if(PcourseNum != 0){
                refreshLayout.setRefreshing(false);
                refreshLayout.setEnabled(false);
            }else {
                refreshLayout.setRefreshing(false);
                forHint();
            }

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
        Toast.makeText(this,"本学期暂无课程",Toast.LENGTH_LONG).show();
    }

    /**防止handle内存泄漏*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mystumainactivity_cookiesHandler.removeCallbacksAndMessages(null);
        mcourseListHandler.removeCallbacksAndMessages(null);
    }

}

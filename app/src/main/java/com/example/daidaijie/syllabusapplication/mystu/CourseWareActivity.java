package com.example.daidaijie.syllabusapplication.mystu;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import com.example.daidaijie.syllabusapplication.App;
import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.base.BaseActivity;
import com.example.daidaijie.syllabusapplication.mystu.request.CourseWareRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class CourseWareActivity extends BaseActivity {
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.courseware_list_View)
    ListView listView;
    @BindView(R.id.courseware_click)
    FloatingActionButton floatingActionButton;

    private String Cookie;
    List<Map<String,Object>> dataList =new ArrayList<>();
    private Handler coursewareHandler =new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what == 50005){
                if(msg.obj != null){
                    String courseware = msg.obj.toString();
                    doneSimpleAdatper(courseware);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupTitleBar(mToolbar);
        refreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getParameter();//数据请求
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getParameter();
            }
        });
    }
    @Override
    protected int getContentView() {
        return R.layout.mystu_courseware;
    }

    private void getParameter(){
        refreshLayout.setEnabled(true);
        refreshLayout.setRefreshing(true);

        String course_linkid=getIntent().getStringExtra("courseLinkId");
        /**获得Cookie
         * */
        App app =(App)getApplication();
        Cookie = app.getTCookie();
        /**
         *workware数据请求
         */
        CourseWareRequest courseWareRequest = new CourseWareRequest(Cookie,course_linkid,coursewareHandler,CourseWareActivity.this,refreshLayout);
        courseWareRequest.getCourseWare();

    }

    private void doneSimpleAdatper(String jsondata){


        System.out.println(jsondata);//若课件为空，这里的jsondata竟然不为空？神奇~

        CourseWareDataWork dataWork = new CourseWareDataWork();
        dataWork.setData(jsondata);
        dataList = dataWork.getData();

        if(CourseWareDataWork.getWareNum() != 0){
            refreshLayout.setEnabled(false);
            refreshLayout.setRefreshing(false);
        }else {
            refreshLayout.setRefreshing(false);
            forHint();
        }

        SimpleAdapter simpleAdapter =new SimpleAdapter(
                this,
                dataList,
                R.layout.mystu_coursewareitem,
                new String[]{"fileImg","fileName"},
                new int[]{R.id.courseware_ig_kind,R.id.courseware_tv_name});

        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Map<String, String> infoMap = (Map<String, String>) parent.getItemAtPosition(position);
                String fileLink=infoMap.get("fileLink");
                String fileName=infoMap.get("fileName");
                Toast.makeText(CourseWareActivity.this,
                        "fileName :"+fileName+"\n"+"fileLink :"+fileLink,
                        Toast.LENGTH_SHORT).show();
                download(fileLink);
            }
        });
    }

    private void download(String fileLink){
        /*将下载链接使用浏览器打开，把下载任务交给浏览器，让浏览器调用系统下载器去下载*/
        /*但用户需要频繁的输入账号密码，很是麻烦*/
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(fileLink));
        startActivity(intent);
    }

    private  void forHint(){
        refreshLayout.setRefreshing(false);
        refreshLayout.setEnabled(false);
        Toast.makeText(this,"本学期暂无课件",Toast.LENGTH_LONG).show();
    }

    /**防止handle内存泄漏*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        coursewareHandler.removeCallbacksAndMessages(null);
    }
}
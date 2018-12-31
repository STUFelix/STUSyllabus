package com.example.daidaijie.syllabusapplication.mystu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.daidaijie.syllabusapplication.App;
import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class CourseWareActivity extends BaseActivity {
    List<Map<String,Object>> dataList =new ArrayList<>();
    ListView listView;
    private String Cookie;
    private SwipeRefreshLayout refreshLayout;

    @BindView(R.id.titleTextView)
    TextView mTitleTextView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;


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
        listView= (ListView)findViewById(R.id.courseware_list_View);
        setupTitleBar(mToolbar);
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        refreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );

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
                        "fileLink :"+fileLink+"\n"+"fileName :"+fileName,
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

        AlertDialog.Builder normalDialog
                = new AlertDialog.Builder(this)
                .setTitle("  -温馨提示")
                .setMessage("\n\n本学期暂无课件\n")
                .setPositiveButton("好吧~", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        CourseWareActivity.this.finish();
                    }
                });
        normalDialog.create().show();
    }
}
package com.example.daidaijie.syllabusapplication.mystu;


import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import android.view.View;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import android.widget.Toast;


import com.example.daidaijie.syllabusapplication.App;
import com.example.daidaijie.syllabusapplication.R;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CourseWareActivity extends AppCompatActivity {
    List<Map<String,Object>> dataList =new ArrayList<>();;
    ListView listView;
    private String Cookie;


    private Handler coursewareHandler =new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what == 50005){
                String courseware = msg.obj.toString();

                doneSimpleAdatper(courseware);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mystu_courseware);
        listView= (ListView)findViewById(R.id.courseware_list_View);

        getParameter();//数据请求
    }

    private void getParameter(){
        String course_linkid=getIntent().getStringExtra("courseLinkId");
        /**获得Cookie
         * */
        App app =(App)getApplication();
        Cookie = app.getTCookie();
        /**
         *workware数据请求
         */
        CourseWareRequest courseWareRequest = new CourseWareRequest(Cookie,course_linkid,coursewareHandler,CourseWareActivity.this);
        courseWareRequest.getCourseWare();

    }

    private void doneSimpleAdatper(String jsondata){

        CourseWareDataWork dataWork = new CourseWareDataWork();
        dataWork.setData(jsondata);
        dataList = dataWork.getData();
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
                        "你单击的position是"+position+"!"+"fileLink :"+fileLink+"fileName :"+fileName,
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
}

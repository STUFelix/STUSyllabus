package com.example.daidaijie.syllabusapplication.mystu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.base.BaseActivity;
import com.example.daidaijie.syllabusapplication.mystu.request.CourseDiscussionRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class CourseInfoDownloadActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.courseware_list_View)
    ListView listView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.courseware_click)
    FloatingActionButton floatingActionButton;

    private CourseFilesBean courseFilesBean;
    private int position;
    private List<Map<String,String>> list= new ArrayList<Map<String,String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        ListAdapter listAdapter =new SimpleAdapter(this,
                                                            list,
                                                            R.layout.mystu_coursewareitem,
                                                            new String[]{"filename","filelink"},
                                                            new int[]{R.id.courseware_tv_name,R.id.courseware_ig_kind});
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String filename = list.get(i).get("filename");
                String filelink = list.get(i).get("filelink");
                Toast.makeText(CourseInfoDownloadActivity.this,
                        "filename :"+filename+"\n"+"filelink :"+filelink,
                        Toast.LENGTH_SHORT).show();
                downloadUrl(filelink);
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.mystu_courseware;
    }

    private void init(){
        setupTitleBar(mToolbar);
        refreshLayout.setEnabled(false);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (list.size()!=0) list.clear();
        Intent intent = getIntent();
        position =intent.getIntExtra("position",0);
        courseFilesBean =CourseDiscussionRequest.getCourseFilesBean(position);
        for(int i=0;i<courseFilesBean.getLength();i++){
            Map<String,String> map =new HashMap<String, String>();
            Log.i("DownLoadActivity", "onCreate: "+courseFilesBean.getCourse_files_details().get(i).getFile_name()+"#"+courseFilesBean.getCourse_files_details().get(i).getFile_url());
            map.put("filename",courseFilesBean.getCourse_files_details().get(i).getFile_name());
            map.put("filelink",courseFilesBean.getCourse_files_details().get(i).getFile_url());
            list.add(map);
        }
    }

    private void downloadUrl(String fileLink){
        /*将下载链接使用浏览器打开，把下载任务交给浏览器，让浏览器调用系统下载器去下载*/
        /*但用户需要频繁的输入账号密码，很是麻烦*/
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(fileLink));
        startActivity(intent);
    }
}
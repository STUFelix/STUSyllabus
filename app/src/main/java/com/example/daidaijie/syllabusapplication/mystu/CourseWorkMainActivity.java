package com.example.daidaijie.syllabusapplication.mystu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;


import com.example.daidaijie.syllabusapplication.App;
import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.base.BaseActivity;

import butterknife.BindView;


public class CourseWorkMainActivity extends BaseActivity {

    @BindView(R.id.titleTextView)
    TextView mTitleTextView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private boolean avoidMultiplyRequestBug = true;

    private SwipeRefreshLayout refreshLayout;

    private RecyclerView mRvTestList;
    private String Cookie;

    private Handler worklistHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 30003) {
                String work_list = msg.obj.toString();
                //work_list数据的解析
                CourseWorkUtil.toParseJson_Title(work_list);

                if(CourseWorkUtil.getWorklistNum() != 0){
                    toRequestAdapter();//将数据关联到adapter 并设置点击事件 并进行CourseWorkDetails网络请求
                    refreshLayout.setRefreshing(false);
                    refreshLayout.setEnabled(false);
                }else {
                    forHint();
                }

            }
        }
    };

    private int myPosition = 0;
    private Handler workdetailsHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 40004) {
                String work_details = msg.obj.toString();
                CourseWorkUtil.toParseJson_Content(work_details);
                workDetailsDialog(myPosition);
            }
        }
    };

    private CourseWorkAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupTitleBar(mToolbar);
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        getParameter_andUse();//进行CourseWorkList数据网络请求
    }
    protected int getContentView(){
        return R.layout.mystu_coursework;
    }


    private void getParameter_andUse() {
        refreshLayout.setEnabled(true);
        refreshLayout.setRefreshing(true);

        String course_linkid = getIntent().getStringExtra("courseLinkId");

        App app = (App) getApplication();
        Cookie = app.getTCookie();
        /**
         *worklist数据请求
         **/
        CourseWorkListRequest courseWorkListRequest = new CourseWorkListRequest(Cookie, course_linkid, worklistHandler,CourseWorkMainActivity.this);
        courseWorkListRequest.getWorkList();
    }


    private void toRequestAdapter() {

        mRvTestList = (RecyclerView) findViewById(R.id.course_rv_testlist);
        mRvTestList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mAdapter = new CourseWorkAdapter(this);
        mRvTestList.setAdapter(mAdapter);

        /**点击对应的recycleView获取position
         * 然后再把position作为
         * CourseWorkUtil.getAssignLinkId(position)
         * 的参数
         * 获得请求作业详情所需的Assign_LinkId。
         * */

        mAdapter.setOnItemClickListener(new CourseWorkAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                try {
                    if(avoidMultiplyRequestBug){
                    CourseWorkDetailsRequest courseWorkDetailsRequest = new CourseWorkDetailsRequest(Cookie, CourseWorkUtil.getAssignLinkId(position), workdetailsHandler,CourseWorkMainActivity.this);
                    courseWorkDetailsRequest.getWorkList();
                    myPosition= position;

                    avoidMultiplyRequestBug = false;
                    }
                    /**点击对应的item，会进行相应的数据请求 根据position （所以这里的position值必须被传递）
                    * */
                } catch (IndexOutOfBoundsException e) {
                    System.out.println ("数据越界"+"请求失败，参数错误或本无作业");
                }
            }
        });

    }

    private  void workDetailsDialog(int position ){
        avoidMultiplyRequestBug = true;
        AlertDialog.Builder normalDialog
                = new AlertDialog.Builder(this)
                .setTitle(CourseWorkUtil.getName(position)+"   -作业详情")
                .setMessage(CourseWorkUtil.getDialogMessage())
                .setPositiveButton("了解", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        normalDialog.create().show();
    }

    private  void forHint(){
        refreshLayout.setRefreshing(false);
        refreshLayout.setEnabled(false);

        AlertDialog.Builder normalDialog
                = new AlertDialog.Builder(this)
                .setTitle("  -温馨提示")
                .setMessage("\n\n本学期暂无作业\n")
                .setPositiveButton("好吧~", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        normalDialog.create().show();
    }

    /**
    //打算做循环请求 即使是通过不同的参数，也就是一下子把作业详情数据请求完成，但是这样行不通

    int Wnumber=CourseWorkUtil.getWorklistNum();//这个是一共有多少个作业

     for(int i=0;i<Wnumber;i++){

     String assign_linkid =CourseWorkUtil.getAssignLinkId(i);//这是获得作业详情的link

     CourseWorkDetailsRequest courseWorkDetailsRequest =new CourseWorkDetailsRequest(Cookie,assign_linkid,workdetailsHandler);
     courseWorkDetailsRequest.getWorkList();//对作业详情进行请求

     }
     **/
}
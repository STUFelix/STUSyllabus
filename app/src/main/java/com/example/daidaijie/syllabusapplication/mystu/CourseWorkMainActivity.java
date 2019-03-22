package com.example.daidaijie.syllabusapplication.mystu;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.daidaijie.syllabusapplication.App;
import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.base.BaseActivity;
import com.example.daidaijie.syllabusapplication.mystu.request.CourseWorkDetailsRequest;
import com.example.daidaijie.syllabusapplication.mystu.request.CourseWorkListRequest;
import butterknife.BindView;


public class CourseWorkMainActivity extends BaseActivity {

    @BindView(R.id.titleTextView)
    TextView mTitleTextView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.course_rv_testlist)
    RecyclerView mRvTestList;
    private String Cookie;
    private boolean avoidMultiplyRequestBug = true;

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
                    avoidMultiplyRequestBug = true;
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
        refreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        getParameter_andUse();//进行CourseWorkList数据网络请求
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getParameter_andUse();
            }
        });
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
        CourseWorkListRequest courseWorkListRequest = new CourseWorkListRequest(Cookie, course_linkid, worklistHandler,CourseWorkMainActivity.this,refreshLayout);
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
                    CourseWorkDetailsRequest courseWorkDetailsRequest = new CourseWorkDetailsRequest(Cookie, CourseWorkUtil.getAssignLinkId(position), workdetailsHandler,CourseWorkMainActivity.this,refreshLayout);
                    courseWorkDetailsRequest.getWorkList();
                    myPosition= position;

                    refreshLayout.setRefreshing(true);
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
        refreshLayout.setRefreshing(false);
        avoidMultiplyRequestBug = true;
        Intent intent =new Intent(this,CourseWorkDetailsActivity.class);
        intent.putExtra("position",position);
        startActivity(intent);

    }

    private  void forHint(){
        refreshLayout.setRefreshing(false);
        Toast.makeText(this,"本学期暂无作业",Toast.LENGTH_LONG).show();
    }

    /**防止handle内存泄漏*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        worklistHandler.removeCallbacksAndMessages(null);
        workdetailsHandler.removeCallbacksAndMessages(null);
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
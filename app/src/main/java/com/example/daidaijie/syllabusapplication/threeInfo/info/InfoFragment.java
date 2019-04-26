package com.example.daidaijie.syllabusapplication.threeInfo.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.base.BaseFragment;
import com.example.daidaijie.syllabusapplication.threeInfo.bean.InfoBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.http.Url;

/**
 * Created by 16zhchen on 2018/10/21.
 */

public class InfoFragment extends BaseFragment {
    public static final String TABLAYOUT_FRAGMENR = "tab_fragment";
    private TextView txt;
    private int type;

    @BindView(R.id.info_recycleView)
    RecyclerView mRecycleView;
    InfoItemAdapter mAdapter;


    public static InfoFragment newInstance(int type){
        InfoFragment fragment = new InfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TABLAYOUT_FRAGMENR,type);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        if(getArguments()!=null){
            type = (int) getArguments().getSerializable(TABLAYOUT_FRAGMENR);
        }
        List<InfoBean> list;
        mAdapter = new InfoItemAdapter(new ArrayList<InfoBean>());
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mAdapter.setOnItemClickListener(new InfoItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (view.getId()){
                    //监听事件
                    case R.id.thumbUpLinearLayout:
                        Toast.makeText(getContext(),"点赞",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.commentLinearLayout:
                        Toast.makeText(getContext(),"评论跳转",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(getContext(),"信息体",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        initView(getView());
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_info;
    }


    protected void initView(View view){
        //txt = (TextView) view.findViewById(R.id.textView3);
        InfoBean i1 = new InfoBean("标题1","内容1",1,new ArrayList<Url>(),10,10);
        InfoBean i2 = new InfoBean("标题2","内容2",2,new ArrayList<Url>(),20,20);
        InfoBean i3 = new InfoBean("标题3","内容3",3,new ArrayList<Url>(),30,30);
        InfoBean i4 = new InfoBean("标题4","内容4",4,new ArrayList<Url>(),40,40);
        List<InfoBean> list = new ArrayList<>();
        switch (type) {
            case 1:
                list.clear();
                list.add(i1);
                mAdapter.updateData(list);
                break;
            case 2:
                list.clear();
                list.add(i2);
                mAdapter.updateData(list);
                break;
            case 3:
                list.clear();
                list.add(i3);
                mAdapter.updateData(list);
                break;
            case 4:
                list.clear();
                list.add(i4);
                mAdapter.updateData(list);
                break;
        }

    }
}

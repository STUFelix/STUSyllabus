package com.example.daidaijie.syllabusapplication.threeInfo.main;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.base.BaseActivity;
import com.example.daidaijie.syllabusapplication.threeInfo.TabAdapter;
import com.example.daidaijie.syllabusapplication.threeInfo.Utils;
import com.example.daidaijie.syllabusapplication.threeInfo.info.InfoFragment;
import com.example.daidaijie.syllabusapplication.user.UserComponent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class InfoMainActivity extends BaseActivity implements View.OnClickListener,InfoMainContract.view{

    public static int BLOCK = 1;
    public static final String[] InfoTabTitle = new String[]{"生活", "兼职", "研究", "实习"};
    public static final String[] FindTabTitle = new String[]{"失物", "寻物"};
    public static final String[] LoveTabTitle = new String[]{"按时间", "按热度"};

    @BindView(R.id.info_tabLayout)
    TabLayout tab;
    @BindView(R.id.info_viewpager)
    ViewPager viewPager;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private TabAdapter adapter;

    @Inject
    InfoMainPresenter mPresenter;

    private int res[] = {R.id.circle_menu_list,R.id.circle_menu_add,R.id.circle_menu_user};
    private ArrayList<ImageView> imageViews = new ArrayList<>();
    private boolean isOpen = false;  //是否展开的flag

    private static final String TAG = "InfoMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupTitleBar(mToolbar);
        Intent intent = getIntent();
        BLOCK = intent.getIntExtra("BLOCKTYPE",0);
        Log.d(TAG, "onCreate: "+BLOCK);
        DaggerInfoMainComponent.builder()
                .userComponent(UserComponent.buildInstance(mAppComponent))
                .build().inject(this);


        initViews();
        initMenu();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_info_main;
    }

    private void initViews(){

        List<Fragment> fragments = new ArrayList<>();
        adapter = new TabAdapter(getSupportFragmentManager(),fragments);
        switch (BLOCK){
            case 1:{
                for(int i=0;i<InfoTabTitle.length;i++){
                    fragments.add(InfoFragment.newInstance(i+1));
                }
                break;
            }
            case 2:{
//                for(int i=0;i<FindTabTitle.length;i++){
//                    fragments.add(FindFragment.newInstance(i+1));
//                }
                break;
            }
            case 3:{
//                for(int i=0;i<FindTabTitle.length;i++){
//                    fragments.add(LoveFragment.newInstance(i+1));
//                }
                break;
            }
        }

        viewPager.setAdapter(adapter);
        tab.setupWithViewPager(viewPager);

        tab.setTabMode(TabLayout.MODE_FIXED);
    }
    private void initMenu(){
        for (int i = 0; i < res.length; i++) {
            ImageView imageView = (ImageView) findViewById(res[i]);
            imageView.setOnClickListener(this);
            imageViews.add(imageView);

        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.circle_menu_list:
                if(isOpen)
                    showExitAnim(100);
                else
                    showEnterAnim(100);
                break;
            case R.id.circle_menu_user:
                Toast.makeText(this,mPresenter.getUserInfo().getNickname(),Toast.LENGTH_SHORT).show();
                break;
            case R.id.circle_menu_add:
                Toast.makeText(this,"add",Toast.LENGTH_SHORT).show();
                break;
        }
    }
    private void showEnterAnim(int dp){
        for(int i=1;i<res.length;i++){
            AnimatorSet set = new AnimatorSet();
            double x = -Math.cos(0.5/(res.length-2)*(i-1)*Math.PI)* Utils.dip2px(this,dp)*0.7;
            double y = -Math.sin(0.5/(res.length-2)*(i-1)*Math.PI)* Utils.dip2px(this,dp)*0.7;
            set.playTogether(
                    ObjectAnimator.ofFloat(imageViews.get(i),"translationX",(float)x),
                    ObjectAnimator.ofFloat(imageViews.get(i),"translationY",(float)y),
                    ObjectAnimator.ofFloat(imageViews.get(i),"alpha",0,1).setDuration(2000)
            );
            //set.setInterpolator(new BounceInterpolator());
            set.setDuration(300).setStartDelay(100*i);
            set.start();
            isOpen = true;
        }
    }
    public void showExitAnim(int dp){

        for(int i=1;i<res.length;i++){
            AnimatorSet set = new AnimatorSet();
            set.playTogether(
                    ObjectAnimator.ofFloat(imageViews.get(i),"translationY",(float)0) ,
                    ObjectAnimator.ofFloat(imageViews.get(i),"translationX",(float)0),
                    ObjectAnimator.ofFloat(imageViews.get(i),"alpha",1,0).setDuration(2000)
            );
            set.setDuration(300).setStartDelay(100*i);
            set.start();
            isOpen = false;
        }
    }
    public int getBLOCK() {
        return BLOCK;
    }

    public void setBLOCK(int BLOCK) {
        this.BLOCK = BLOCK;
    }
}

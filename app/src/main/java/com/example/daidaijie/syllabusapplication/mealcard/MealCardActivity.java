package com.example.daidaijie.syllabusapplication.mealcard;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.base.BaseActivity;
import com.example.daidaijie.syllabusapplication.bean.UserLogin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class MealCardActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener{

    @BindView(R.id.card_listView)
    ListView flow_listView;
    @BindView(R.id.mealcard_TV_balance)
    TextView tv_balance;
    @BindView(R.id.mealcard_TV_todayConsume)
    TextView tv_today_consumer;
    @BindView(R.id.mealcard_TV_consumeDetails_icon)
    TextView tv_consume_details_icon;
    @BindView(R.id.mealcard_TV_countCosts)
    TextView tv_count_cost;
    @BindView(R.id.mealcard_rg)
    RadioGroup time_rg;
    @BindView(R.id.mealcard_rb_1)
    RadioButton button1;
    @BindView(R.id.mealcard_rb_2)
    RadioButton button2;
    @BindView(R.id.mealcard_rb_3)
    RadioButton button3;
    @BindView(R.id.mealcard_rb_4)
    RadioButton button4;
    @BindView(R.id.mealcard_swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private String days ="3";//默认值为"3"
    private String username ="";
    private String password= "";
    private String mCookie;
    private String mBalance;

    private RequestBalance requestBalance;
    private RequestDetail requestDetail;
    private List<Map<String,String>> list_map = new ArrayList<Map<String,String>>();

    private String[] key ={"date","time","flow","name"};
    private int[] item ={R.id.mealcard_item_date,R.id.mealcard_item_time,R.id.mealcard_item_flow,R.id.mealcard_item_name};
    private double[] count_today_cost = new double[15];
    private String date;

    /**通过重写onDestroy方法解除message-handler-activity引用链避免内存泄漏*/
    private  Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case 10001:
                    String[] temp = msg.obj.toString().split("#");
                    mBalance = temp[0]; mCookie =temp[1];
                    tv_balance.setText(mBalance);
                    tv_balance.setTextSize(60);
                    requestDetail = new RequestDetail(mCookie,username,password,days,handler,swipeRefreshLayout,MealCardActivity.this);
                    requestDetail.getDetail();
                    break;
                case 20001:
                    list_map =(List<Map<String,String>>) msg.obj;
                    countCost(list_map);

                    SimpleAdapter simpleAdapter =new SimpleAdapter(MealCardActivity.this,list_map,R.layout.mealcard_list_item,key,item);
                    flow_listView.setAdapter(simpleAdapter);
                    swipeRefreshLayout.setRefreshing(false);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();

        requestBalance = new RequestBalance(username,password,handler,swipeRefreshLayout,MealCardActivity.this);
        requestBalance.getBalance();
    }

    @Override
    protected int getContentView() {
        return R.layout.mealcard_main;
    }


    private void  init(){

        setupTitleBar(mToolbar);

        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                requestBalance = new RequestBalance(username,password,handler,swipeRefreshLayout,MealCardActivity.this);
                requestBalance.getBalance();
            }
        });


        /**拿到账号密码*/
        Realm xRealm = Realm.getDefaultInstance();
        UserLogin userLogin =xRealm.where(UserLogin.class).findFirst();
        username = userLogin.getUsername();
        password = userLogin.getPassword();

        /**View对应的设置*/
        Drawable drawable_balance = getResources().getDrawable(R.drawable.mealcard_wallet);
        drawable_balance.setBounds(0,0,120,120);
        tv_balance.setCompoundDrawables(drawable_balance,null,null,null);

        Drawable drawable_consume_details =getResources().getDrawable(R.drawable.mealcard_time);
        drawable_consume_details.setBounds(0,0,35,35);
        tv_consume_details_icon.setCompoundDrawables(null,null,drawable_consume_details,null);

        time_rg = (RadioGroup) findViewById(R.id.mealcard_rg);
        button1 = (RadioButton) findViewById(R.id.mealcard_rb_1);
        button2 = (RadioButton) findViewById(R.id.mealcard_rb_2);
        button3 = (RadioButton) findViewById(R.id.mealcard_rb_3);
        button4 = (RadioButton) findViewById(R.id.mealcard_rb_4);
        time_rg.setOnCheckedChangeListener(this);

        /**拿到系统当前年月日*/
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd");
        Date date_temp =new Date(System.currentTimeMillis());
        date = simpleDateFormat.format(date_temp);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        button1.setChecked(false);
        button2.setChecked(false);
        button3.setChecked(false);
        button4.setChecked(false);
        switch (checkedId){
            case R.id.mealcard_rb_1:
                button1.setChecked(true);
                days = "3";
                swipeRefreshLayout.setRefreshing(true);
                requestDetail = new RequestDetail(mCookie,username,password,days,handler,swipeRefreshLayout,MealCardActivity.this);
                requestDetail.getDetail();
                Toast.makeText(this,"近三日消费详情",Toast.LENGTH_SHORT).show();
                break;
            case R.id.mealcard_rb_2:
                button2.setChecked(true);
                days = "7";
                swipeRefreshLayout.setRefreshing(true);
                requestDetail = new RequestDetail(mCookie,username,password,days,handler,swipeRefreshLayout,MealCardActivity.this);
                requestDetail.getDetail();
                Toast.makeText(this,"一周内消费详情",Toast.LENGTH_SHORT).show();
                break;
            case R.id.mealcard_rb_3:
                button3.setChecked(true);
                days = "31";
                swipeRefreshLayout.setRefreshing(true);
                requestDetail = new RequestDetail(mCookie,username,password,days,handler,swipeRefreshLayout,MealCardActivity.this);
                requestDetail.getDetail();
                Toast.makeText(this,"一月内消费详情",Toast.LENGTH_SHORT).show();
                break;
            case R.id.mealcard_rb_4:
                button4.setChecked(true);
                days = "90";
                swipeRefreshLayout.setRefreshing(true);
                requestDetail = new RequestDetail(mCookie,username,password,days,handler,swipeRefreshLayout,MealCardActivity.this);
                requestDetail.getDetail();
                Toast.makeText(this,"近三月消费详情",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    /**计算今日消费和一段时间内支出总计并setTextView()*/
    private void countCost(List<Map<String,String>> list_map){

        double count_cost = 0;double count_cost_temp = 0;double count_today_cost_amount=0;String count_today_string="";
        Iterator<Map<String,String>> iterable =  list_map.iterator();
        int today_i = 0;
        while (iterable.hasNext()){
            Map<String,String> iterable_map =iterable.next();
            count_cost_temp = Double.parseDouble(iterable_map.get("flow"));
            if( count_cost_temp < 0) {count_cost +=count_cost_temp;}
            if(count_cost_temp < 0 && date.equals(iterable_map.get("date"))){
                count_today_cost[today_i] =Math.abs(count_cost_temp);
                today_i++;
            }
        }
        for (int i=today_i-1; i>=0; i--){
            count_today_cost_amount = count_today_cost_amount+ count_today_cost[i];
            if(i==today_i-1){
                count_today_string = ""+count_today_cost[i];
                continue;
            }
            count_today_string = count_today_string+"+"+count_today_cost[i];
        }

        tv_today_consumer.setText("今日消费："+count_today_string+" = "+String.format("%.1f",count_today_cost_amount)+" 元");
        tv_today_consumer.setTextSize(15);
        tv_count_cost.setText("支出总计：\t"+String.format("%.1f",count_cost)+"  元");
        tv_count_cost.setTextSize(20);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }


}
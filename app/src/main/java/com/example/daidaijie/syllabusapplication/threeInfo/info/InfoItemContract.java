package com.example.daidaijie.syllabusapplication.threeInfo.info;

import com.example.daidaijie.syllabusapplication.base.BasePresenter;
import com.example.daidaijie.syllabusapplication.base.BaseView;

/**
 * Created by 16zhchen on 2018/11/25.
 */

public class InfoItemContract {
    interface  presenter extends BasePresenter,InfoItemAdapter.OnLikeCallBack{
        void loadData();
    }
    interface view extends BaseView<presenter>{
        void showMsg(String msg);
    }
}

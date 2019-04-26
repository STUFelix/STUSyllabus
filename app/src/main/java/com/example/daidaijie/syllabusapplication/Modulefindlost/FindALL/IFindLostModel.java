package com.example.daidaijie.syllabusapplication.Modulefindlost.FindALL;

import com.example.daidaijie.syllabusapplication.Modulefindlost.bean.FindBean;

import java.util.List;

import rx.Observable;

/**
 * Created by daidaijie on 2016/10/21.
 */

public interface IFindLostModel {

    Observable<List<FindBean>> getFindLostListFromNet(int kind, int page_index);

    Observable<List<FindBean>> loadFindLostListFromNet(int kind,int page_index);

}

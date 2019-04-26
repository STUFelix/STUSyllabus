package com.example.daidaijie.syllabusapplication.ModuleInfo.tempPackage;

import com.example.daidaijie.syllabusapplication.ModuleInfo.bean.Info4Bean;
import com.example.daidaijie.syllabusapplication.bean.ThumbUpReturn;

import java.util.List;

import rx.Observable;

/**
 * Created by 16zhchen on 2018/12/14.
 */

public interface IInfoModel {
    Observable<List<Info4Bean>> getInfo();
    Observable<List<Info4Bean>> getOnePageInfo(int pageIndex);
    Observable<ThumbUpReturn> like(int position);
    Observable<ThumbUpReturn> unlike(int position);
}

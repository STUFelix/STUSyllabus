package com.example.daidaijie.syllabusapplication.Modulefindlost.personal;

import com.example.daidaijie.syllabusapplication.Modulefindlost.bean.PersonalPost;

import java.util.List;

import rx.Observable;

/**
 * Created by daidaijie on 2016/10/21.
 */

public interface IPersonalModel {

    Observable<List<PersonalPost>> getPostFromNet(int page_index);
    Observable<List<PersonalPost>> loadPostFromNet(int page_index);

    Observable<List<PersonalPost>> deletePost(int position);

    PersonalPost getFindLostByPosition(int position);
}

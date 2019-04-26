package com.example.daidaijie.syllabusapplication.recommendation.searchList;

import com.example.daidaijie.syllabusapplication.AppComponent;
import com.example.daidaijie.syllabusapplication.di.scope.PerActivity;

import dagger.Component;

/**
 * Created by 16zhchen on 2018/10/4.
 */
@PerActivity
@Component(dependencies = AppComponent.class,modules = SearchListModule.class)
public interface SearchListComponnent {
    void inject(SearchListActivity searchListActivity);
}

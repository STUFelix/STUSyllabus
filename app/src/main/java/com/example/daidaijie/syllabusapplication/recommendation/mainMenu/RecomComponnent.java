package com.example.daidaijie.syllabusapplication.recommendation.mainMenu;

import com.example.daidaijie.syllabusapplication.AppComponent;
import com.example.daidaijie.syllabusapplication.di.scope.PerActivity;

import dagger.Component;

/**
 * Created by 16zhchen on 2018/10/4.
 */
@PerActivity
@Component(dependencies = AppComponent.class,modules = RecomModule.class)
public interface RecomComponnent {
    void inject(RecomActivity recomActivity);
}

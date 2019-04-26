package com.example.daidaijie.syllabusapplication.Modulefindlost.FindALL.postContent;

import com.example.daidaijie.syllabusapplication.di.scope.PerActivity;
import com.example.daidaijie.syllabusapplication.Modulefindlost.FindALL.FindLostComponent;

import dagger.Component;

/**
 * Created by daidaijie on 2016/10/21.
 */

@PerActivity
@Component(dependencies = FindLostComponent.class, modules = PostContentModule.class)
public interface PostContentComponent {

    void inject(PostContentActivity postContentActivity);
}

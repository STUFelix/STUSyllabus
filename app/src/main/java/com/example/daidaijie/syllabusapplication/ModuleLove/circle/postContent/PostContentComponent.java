package com.example.daidaijie.syllabusapplication.ModuleLove.circle.postContent;

import com.example.daidaijie.syllabusapplication.ModuleLove.circle.StuCircleModelComponent;
import com.example.daidaijie.syllabusapplication.di.scope.PerActivity;

import dagger.Component;

/**
 * Created by daidaijie on 2016/10/21.
 */

@PerActivity
@Component(dependencies = StuCircleModelComponent.class, modules = PostContentModule.class)
public interface PostContentComponent {

    void inject(PostContentActivity postContentActivity);
}

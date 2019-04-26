package com.example.daidaijie.syllabusapplication.ModuleLove.personal.posts;

import com.example.daidaijie.syllabusapplication.ModuleLove.personal.StuCircleModelComponent;
import com.example.daidaijie.syllabusapplication.di.scope.PerFragment;

import dagger.Component;

/**
 * Created by daidaijie on 2016/10/21.
 */

@PerFragment
@Component(dependencies = StuCircleModelComponent.class, modules = PostsModule.class)
public interface PostsComponent {
    void inject(PostsFragment stuCircleFragment);
}

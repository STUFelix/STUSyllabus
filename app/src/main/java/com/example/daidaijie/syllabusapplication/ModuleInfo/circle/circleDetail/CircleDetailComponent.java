package com.example.daidaijie.syllabusapplication.ModuleInfo.circle.circleDetail;

import com.example.daidaijie.syllabusapplication.ModuleInfo.circle.StuCircleModelComponent;
import com.example.daidaijie.syllabusapplication.di.scope.PerActivity;


import dagger.Component;

/**
 * Created by daidaijie on 2016/10/21.
 */

@PerActivity
@Component(dependencies = StuCircleModelComponent.class, modules = CircleDetailModule.class)
public interface CircleDetailComponent {

    void inject(CircleDetailActivity circleDetailActivity);
}

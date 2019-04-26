package com.example.daidaijie.syllabusapplication.ModuleInfo.personal.comments;

import com.example.daidaijie.syllabusapplication.ModuleInfo.personal.StuCircleModelComponent;
import com.example.daidaijie.syllabusapplication.di.scope.PerFragment;

import dagger.Component;

/**
 * Created by daidaijie on 2016/10/21.
 */

@PerFragment
@Component(dependencies = StuCircleModelComponent.class, modules = CommnetsModule.class)
public interface CommnetsComponent {
    void inject(CommnetsFragment stuCircleFragment);
}
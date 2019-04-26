package com.example.daidaijie.syllabusapplication.Modulefindlost.FindALL.mainmenu;

import com.example.daidaijie.syllabusapplication.di.scope.PerFragment;

import dagger.Component;

/**
 * Created by daidaijie on 2016/10/21.
 */

@PerFragment
@Component(dependencies = com.example.daidaijie.syllabusapplication.Modulefindlost.FindALL.FindLostComponent.class, modules = FindLostModule.class)
public interface FindLostComponent {

    void inject(FindLostFragment findLostFragment);
}

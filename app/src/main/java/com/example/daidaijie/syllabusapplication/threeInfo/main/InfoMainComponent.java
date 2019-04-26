package com.example.daidaijie.syllabusapplication.threeInfo.main;


import com.example.daidaijie.syllabusapplication.di.scope.PerFragment;
import com.example.daidaijie.syllabusapplication.di.scope.PerModule;
import com.example.daidaijie.syllabusapplication.di.scope.PerUser;
import com.example.daidaijie.syllabusapplication.user.UserComponent;

import dagger.Component;

/**
 * Created by 16zhchen on 2018/10/28.
 */

@PerFragment
@Component(dependencies = UserComponent.class)
public interface InfoMainComponent {
    void inject(InfoMainActivity infoMainActivity);
}

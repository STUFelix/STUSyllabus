package com.example.daidaijie.syllabusapplication.threeInfo;

import com.example.daidaijie.syllabusapplication.AppComponent;
import com.example.daidaijie.syllabusapplication.di.scope.PerModule;
import com.example.daidaijie.syllabusapplication.user.UserComponent;

import dagger.Component;

/**
 * Created by 16zhchen on 2018/11/25.
 */

@PerModule
@Component(dependencies = UserComponent.class,modules = TinfoModelModule.class)
public abstract class TinfoModelComponent {
    private static TinfoModelComponent INSTANCE;
    public static TinfoModelComponent getInstance(AppComponent appComponent){
        if(INSTANCE == null){
//            DaggerInfoMainComponent.builder()
//                    .userComponent(UserComponent.buildInstance(appComponent))
//                    .build();
        }
        return INSTANCE;
    }
}

package com.example.daidaijie.syllabusapplication.ModuleInfo.tempPackage;

import com.example.daidaijie.syllabusapplication.di.scope.PerFragment;
import com.example.daidaijie.syllabusapplication.user.UserComponent;

import dagger.Component;

/**
 * Created by 16zhchen on 2018/12/14.
 */
@PerFragment
@Component(dependencies = UserComponent.class,modules = Info4Module.class)
public interface Info4Componnent {
    void inject(Info4Fragment fragment);
}

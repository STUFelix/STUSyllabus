package com.example.daidaijie.syllabusapplication.ModuleInfo.personal.comments;

import com.example.daidaijie.syllabusapplication.di.scope.PerFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by daidaijie on 2016/10/21.
 */

@Module
public class CommnetsModule {

    CommnetsContract.view mView;

    public CommnetsModule(CommnetsContract.view view) {
        mView = view;
    }

    @Provides
    @PerFragment
    CommnetsContract.view provideView() {
        return mView;
    }
}

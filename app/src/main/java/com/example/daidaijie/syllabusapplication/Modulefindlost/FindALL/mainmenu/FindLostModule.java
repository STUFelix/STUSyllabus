package com.example.daidaijie.syllabusapplication.Modulefindlost.FindALL.mainmenu;

import com.example.daidaijie.syllabusapplication.di.scope.PerFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by daidaijie on 2016/10/21.
 */

@Module
public class FindLostModule {

    FindLostContract.view mView;

    public FindLostModule(FindLostContract.view view) {
        mView = view;
    }

    @Provides
    @PerFragment
    FindLostContract.view provideView() {
        return mView;
    }
}

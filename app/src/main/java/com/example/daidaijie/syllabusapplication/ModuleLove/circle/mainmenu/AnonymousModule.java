package com.example.daidaijie.syllabusapplication.ModuleLove.circle.mainmenu;

import com.example.daidaijie.syllabusapplication.di.scope.PerFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by daidaijie on 2016/10/21.
 */

@Module
public class AnonymousModule {

    AnonymousContract.view mView;

    public AnonymousModule(AnonymousContract.view view) {
        mView = view;
    }

    @Provides
    @PerFragment
    AnonymousContract.view provideView() {
        return mView;
    }
}

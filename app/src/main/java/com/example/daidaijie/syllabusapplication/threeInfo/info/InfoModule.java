package com.example.daidaijie.syllabusapplication.threeInfo.info;

import com.example.daidaijie.syllabusapplication.di.scope.PerFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 16zhchen on 2018/10/28.
 */
@Module
public class InfoModule {
    InfoItemContract.view mView;
    public InfoModule(InfoItemContract.view view){
        mView = view;
    }

    @Provides
    @PerFragment
    InfoItemContract.view provideView(){
        return mView;
    }
}

package com.example.daidaijie.syllabusapplication.threeInfo;

import com.example.daidaijie.syllabusapplication.di.qualifier.user.LoginUser;
import com.example.daidaijie.syllabusapplication.di.scope.PerModule;
import com.example.daidaijie.syllabusapplication.user.IUserModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 16zhchen on 2018/11/25.
 */

@Module
public class TinfoModelModule {
    @PerModule
    @Provides
    ITinfoModel privideITinfoModel(@LoginUser IUserModel userModel){
        return new TinfoModel(userModel);
    }
}

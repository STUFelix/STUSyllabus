package com.example.daidaijie.syllabusapplication.Modulefindlost.FindALL;

import com.example.daidaijie.syllabusapplication.di.qualifier.retrofitQualifier.TestRetrofit;
import com.example.daidaijie.syllabusapplication.di.qualifier.user.LoginUser;
import com.example.daidaijie.syllabusapplication.di.scope.PerModule;
import com.example.daidaijie.syllabusapplication.Modulefindlost.api.FIndApi;
import com.example.daidaijie.syllabusapplication.user.IUserModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by daidaijie on 2016/10/21.
 */

@Module
public class FindLostModelModule {


    @PerModule
    @Provides
    IFindLostModel provideISchoolCircleModel(@LoginUser IUserModel userModel,
                                             @TestRetrofit Retrofit retrofit) {
        return new FindLostModel(retrofit.create(FIndApi.class), userModel);
    }
}

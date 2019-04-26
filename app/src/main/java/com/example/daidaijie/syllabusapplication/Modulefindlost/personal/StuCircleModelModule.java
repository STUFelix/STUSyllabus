package com.example.daidaijie.syllabusapplication.Modulefindlost.personal;

import com.example.daidaijie.syllabusapplication.di.qualifier.retrofitQualifier.TestRetrofit;
import com.example.daidaijie.syllabusapplication.di.qualifier.user.LoginUser;
import com.example.daidaijie.syllabusapplication.di.scope.PerModule;
import com.example.daidaijie.syllabusapplication.Modulefindlost.api.PersonalApi;
import com.example.daidaijie.syllabusapplication.user.IUserModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by daidaijie on 2016/10/21.
 */

@Module
public class StuCircleModelModule {


    @PerModule
    @Provides
    IPersonalModel provideISchoolCircleModel(@LoginUser IUserModel userModel,
                                             @TestRetrofit Retrofit retrofit) {
        return new PersonalModel(retrofit.create(PersonalApi.class), userModel);
    }
}

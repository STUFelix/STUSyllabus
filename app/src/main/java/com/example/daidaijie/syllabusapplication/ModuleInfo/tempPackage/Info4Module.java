package com.example.daidaijie.syllabusapplication.ModuleInfo.tempPackage;

import com.example.daidaijie.syllabusapplication.ModuleInfo.bean.InfoApi;
import com.example.daidaijie.syllabusapplication.di.qualifier.retrofitQualifier.TestRetrofit;
import com.example.daidaijie.syllabusapplication.di.qualifier.user.LoginUser;
import com.example.daidaijie.syllabusapplication.di.scope.PerFragment;
import com.example.daidaijie.syllabusapplication.user.IUserModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by 16zhchen on 2018/12/14.
 */
@Module
public class Info4Module {
    private Info4Contract.view mView;
    public Info4Module(Info4Contract.view view){
        this.mView = view;
    }

    @PerFragment
    @Provides
    Info4Contract.view provideView(){
        return mView;
    }

    @PerFragment
    @Provides
    IInfoModel provideInfoModel(@TestRetrofit Retrofit retrofit, @LoginUser IUserModel iUserModel){
        return new InfoModel(retrofit.create(InfoApi.class),iUserModel);
    }
}

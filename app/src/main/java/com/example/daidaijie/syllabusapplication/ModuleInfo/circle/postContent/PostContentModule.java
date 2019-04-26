package com.example.daidaijie.syllabusapplication.ModuleInfo.circle.postContent;

import com.example.daidaijie.syllabusapplication.di.qualifier.retrofitQualifier.TestRetrofit;
import com.example.daidaijie.syllabusapplication.di.qualifier.user.LoginUser;
import com.example.daidaijie.syllabusapplication.di.scope.PerActivity;
import com.example.daidaijie.syllabusapplication.user.IUserModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by daidaijie on 2016/10/21.
 */

@Module
public class PostContentModule {

    PostContentContract.view mView;

    public PostContentModule(PostContentContract.view view) {
        mView = view;
    }

    @PerActivity
    @Provides
    PostContentContract.view provideView() {
        return mView;
    }

    @PerActivity
    @Provides
    IPostContentModel providePostContentModel(@LoginUser IUserModel userModel,
                                              @TestRetrofit Retrofit retrofit) {
        return new PostContentModel(userModel, retrofit.create(PushPostApi.class));
    }
}
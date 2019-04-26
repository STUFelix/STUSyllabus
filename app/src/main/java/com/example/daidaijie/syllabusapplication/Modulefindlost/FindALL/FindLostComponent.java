package com.example.daidaijie.syllabusapplication.Modulefindlost.FindALL;

import com.example.daidaijie.syllabusapplication.AppComponent;
import com.example.daidaijie.syllabusapplication.di.qualifier.retrofitQualifier.SchoolRetrofit;
import com.example.daidaijie.syllabusapplication.di.qualifier.retrofitQualifier.TestRetrofit;
import com.example.daidaijie.syllabusapplication.di.qualifier.user.LoginUser;
import com.example.daidaijie.syllabusapplication.di.scope.PerModule;
import com.example.daidaijie.syllabusapplication.user.IUserModel;
import com.example.daidaijie.syllabusapplication.user.UserComponent;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by daidaijie on 2016/10/21.
 */

@PerModule
@Component(dependencies = UserComponent.class, modules = FindLostModelModule.class)
public abstract class FindLostComponent {

    private static FindLostComponent INSTANCE;

    public static FindLostComponent getInstance(AppComponent appComponent) {
        if (INSTANCE == null) {
            INSTANCE = DaggerFindLostComponent.builder()
                    .userComponent(UserComponent.buildInstance(appComponent))
                    .findLostModelModule(new FindLostModelModule())
                    .build();
        }
        return INSTANCE;
    }

    public static void destory() {
        INSTANCE = null;
    }

    public abstract IFindLostModel getSchoolCircleModel();

    @SchoolRetrofit
    public abstract Retrofit getSchoolRetrofit();

    @TestRetrofit
    public abstract Retrofit getTestRetrofit();

    @LoginUser
    public abstract IUserModel getUserModel();
}

package com.example.daidaijie.syllabusapplication.syllabus.LessonEvaluation;

import com.example.daidaijie.syllabusapplication.di.qualifier.retrofitQualifier.SchoolRetrofit;
import com.example.daidaijie.syllabusapplication.di.qualifier.retrofitQualifier.TestRetrofit;
import com.example.daidaijie.syllabusapplication.di.qualifier.user.LoginUser;
import com.example.daidaijie.syllabusapplication.di.scope.PerActivity;
import com.example.daidaijie.syllabusapplication.syllabus.ISyllabusModel;
import com.example.daidaijie.syllabusapplication.user.IUserModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by 16zhchen on 2018/9/22.
 */
@Module
public class LessonEvalModule {
    private LessonEvalContract.view mView;

    public LessonEvalModule(LessonEvalContract.view view){
        mView = view;
    }

    @PerActivity
    @Provides
    LessonEvalContract.view provideView(){
        return mView;
    }

//    @PerActivity
//    @Provides
//    ILessonEvalModel provideLessonEvalModel(@SchoolRetrofit Retrofit retrofit,
//                                            @LoginUser IUserModel iUserModel){
//        return new LessonEvalModel(retrofit.create(EvalApi.class),
//                iUserModel);
//    }
    @PerActivity
    @Provides
    ILessonEvalModel provideLessonEvalModel(@SchoolRetrofit Retrofit retrofit,
                                            ISyllabusModel syllabusModel){
        return new LessonEvalModel(retrofit.create(EvalApi.class), syllabusModel);
    }
}

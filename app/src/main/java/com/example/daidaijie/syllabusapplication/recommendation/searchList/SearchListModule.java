package com.example.daidaijie.syllabusapplication.recommendation.searchList;

import com.example.daidaijie.syllabusapplication.di.qualifier.retrofitQualifier.SchoolRetrofit;
import com.example.daidaijie.syllabusapplication.di.qualifier.retrofitQualifier.TestRetrofit;
import com.example.daidaijie.syllabusapplication.di.scope.PerActivity;
import com.example.daidaijie.syllabusapplication.recommendation.IRecomModel;
import com.example.daidaijie.syllabusapplication.recommendation.RecomModel;
import com.example.daidaijie.syllabusapplication.recommendation.recomApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by 16zhchen on 2018/10/4.
 */
@Module
public class SearchListModule {
    private SearchListContract.view mView;
    public SearchListModule(SearchListContract.view view){
        mView = view;
    }

    @PerActivity
    @Provides
    SearchListContract.view provideView(){
        return  mView;
    }

    @PerActivity
    @Provides
    IRecomModel provideRecomModel(@SchoolRetrofit Retrofit retrofit){
        return new RecomModel(retrofit.create(recomApi.class));
    }
}

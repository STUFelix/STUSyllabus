package com.example.daidaijie.syllabusapplication.todo.mainMenu;

import com.example.daidaijie.syllabusapplication.di.qualifier.retrofitQualifier.SchoolRetrofit;
import com.example.daidaijie.syllabusapplication.di.qualifier.user.LoginUser;
import com.example.daidaijie.syllabusapplication.di.scope.PerFragment;
import com.example.daidaijie.syllabusapplication.todo.ITaskModel;
import com.example.daidaijie.syllabusapplication.todo.TaskModel;
import com.example.daidaijie.syllabusapplication.todo.TodoApi;
import com.example.daidaijie.syllabusapplication.user.IUserModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by 16zhchen on 2018/9/16.
 */
@Module
public class TaskListModule {
    private  TaskListContract.view mView;
    public TaskListModule(TaskListContract.view view){
        mView = view;
    }

    @PerFragment
    @Provides
    TaskListContract.view provideView(){
        return mView;
    }

    @PerFragment
    @Provides
    ITaskModel provideTaskModel(@SchoolRetrofit Retrofit retrofit,
                                @LoginUser IUserModel iUserModel){
        return new TaskModel(retrofit.create(TodoApi.class),iUserModel);
    }
}

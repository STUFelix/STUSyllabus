package com.example.daidaijie.syllabusapplication.ModuleInfo.personal.posts;

import com.example.daidaijie.syllabusapplication.di.scope.PerFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by daidaijie on 2016/10/21.
 */

@Module
public class PostsModule {

    PostsContract.view mView;

    public PostsModule(PostsContract.view view) {
        mView = view;
    }

    @Provides
    @PerFragment
    PostsContract.view provideView() {
        return mView;
    }
}

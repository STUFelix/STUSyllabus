package com.example.daidaijie.syllabusapplication.todo.taskDetail;

import com.example.daidaijie.syllabusapplication.di.scope.PerFragment;
import com.example.daidaijie.syllabusapplication.user.UserComponent;

import dagger.Component;

/**
 * Created by 16zhchen on 2018/9/16.
 */
@PerFragment
@Component(dependencies = UserComponent.class,modules = TaskDetailModule.class)
public interface TaskDetailComponent {
    void inject(TaskDetailFragment taskDetailFragment);
}

package com.example.daidaijie.syllabusapplication.todo.mainMenu;

import com.example.daidaijie.syllabusapplication.di.scope.PerFragment;
import com.example.daidaijie.syllabusapplication.officeAutomation.mainMenu.OfficeAutomationFragment;
import com.example.daidaijie.syllabusapplication.todo.addOrEditTask.TaskAEFragment;
import com.example.daidaijie.syllabusapplication.user.UserComponent;

import dagger.Component;

@PerFragment
@Component(dependencies = UserComponent.class,modules = TaskListModule.class)
public interface TaskListComponent {
    void inject(TaskListFragment taskListFragment);
}

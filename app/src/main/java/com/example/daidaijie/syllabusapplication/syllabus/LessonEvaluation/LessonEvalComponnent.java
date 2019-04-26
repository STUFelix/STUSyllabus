package com.example.daidaijie.syllabusapplication.syllabus.LessonEvaluation;

import com.example.daidaijie.syllabusapplication.AppComponent;
import com.example.daidaijie.syllabusapplication.di.scope.PerActivity;
import com.example.daidaijie.syllabusapplication.di.scope.PerModule;
import com.example.daidaijie.syllabusapplication.syllabus.SyllabusComponent;
import com.example.daidaijie.syllabusapplication.user.UserComponent;

import dagger.Component;

/**
 * Created by 16zhchen on 2018/9/22.
 */

@PerActivity
@Component(dependencies = SyllabusComponent.class, modules = LessonEvalModule.class)
public interface LessonEvalComponnent {
    void inject(LessonEvalActivity lessonEvalActivity);

}

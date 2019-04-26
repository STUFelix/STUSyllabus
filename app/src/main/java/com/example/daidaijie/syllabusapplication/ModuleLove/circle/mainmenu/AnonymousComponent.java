package com.example.daidaijie.syllabusapplication.ModuleLove.circle.mainmenu;

import com.example.daidaijie.syllabusapplication.ModuleLove.circle.StuCircleModelComponent;
import com.example.daidaijie.syllabusapplication.di.scope.PerFragment;

import dagger.Component;

/**
 * Created by daidaijie on 2016/10/21.
 */

@PerFragment
@Component(dependencies = StuCircleModelComponent.class, modules = AnonymousModule.class)
public interface AnonymousComponent {

    void inject(AnonymousFragment anonymousFragment);
}

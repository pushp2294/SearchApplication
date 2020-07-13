package com.intrepid.searchapplication.di.componenets;

import com.intrepid.searchapplication.activities.WikiSearchActivity;
import com.intrepid.searchapplication.di.modules.ApplicationModule;
import com.intrepid.searchapplication.di.modules.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { ApplicationModule.class, NetworkModule.class })
public interface ApplicationComponent {

    void inject(WikiSearchActivity wikiSearchActivity);
}

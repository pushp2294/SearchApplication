package com.intrepid.searchapplication;

import android.app.Application;

import com.intrepid.searchapplication.db.WikiDatabaseService;
import com.intrepid.searchapplication.di.componenets.ApplicationComponent;
import com.intrepid.searchapplication.di.componenets.DaggerApplicationComponent;
import com.intrepid.searchapplication.di.modules.ApplicationModule;
import com.intrepid.searchapplication.di.modules.NetworkModule;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class WikiSearchApplication extends Application {

    private static String DB_NAME = "WikiPage";
    private static long DB_SCHEMA = 1;
    ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                .networkModule(new NetworkModule())
                .applicationModule(new ApplicationModule(this))
                .build();
        initRealm();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    private void initRealm() {
        Realm.init(this);
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder()
                .schemaVersion(DB_SCHEMA)
                .name(DB_NAME)
                .deleteRealmIfMigrationNeeded()
                .build());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if(!Realm.getDefaultInstance().isClosed())
        WikiDatabaseService.getInstance().closeRealm();
    }
}

package com.intrepid.searchapplication.di.modules;

import com.intrepid.searchapplication.network.WikiSearchApiInterface;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
@Module
public class NetworkModule {


    @Provides
    @Singleton
    @Named("baseUrl")
    String provideBaseUrl(){
        return "https://en.wikipedia.org/";
    }
    @Singleton
    @Provides
    Retrofit provideRetrofit(@Named("baseUrl") String baseUrl) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    @Singleton
    @Provides
    WikiSearchApiInterface provideNewsSearchApiInterface(Retrofit retrofit) {
        return retrofit.create(WikiSearchApiInterface.class);
    }
}

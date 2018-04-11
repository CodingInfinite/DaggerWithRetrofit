package com.spartons.daggerwithretrofit.modules;

import android.content.Context;

import com.spartons.daggerwithretrofit.qualifier.ApplicationContextQualifier;
import com.spartons.daggerwithretrofit.scopes.CustomApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ahsen Saeed on 12/4/2017.
 */
@Module
public class ApplicationContextModule {

    private final Context context;

    public ApplicationContextModule(Context context) {
        this.context = context.getApplicationContext();
    }

    @Provides
    @CustomApplicationScope
    @ApplicationContextQualifier
    Context getContext() {
        return context;
    }
}

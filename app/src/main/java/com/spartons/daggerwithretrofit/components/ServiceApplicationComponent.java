package com.spartons.daggerwithretrofit.components;

import com.spartons.daggerwithretrofit.MainActivity;
import com.spartons.daggerwithretrofit.backend.ServiceUtil;
import com.spartons.daggerwithretrofit.modules.PicassoModule;
import com.spartons.daggerwithretrofit.modules.ServiceUtilModule;
import com.spartons.daggerwithretrofit.scopes.CustomApplicationScope;
import com.squareup.picasso.Picasso;


import dagger.Component;

/**
 * Created by Ahsen Saeed on 12/4/2017.
 */

@CustomApplicationScope
@Component(modules = {ServiceUtilModule.class, PicassoModule.class})
public interface ServiceApplicationComponent {

    Picasso getPicasso();

    ServiceUtil getServiceUtil();

    void inject(MainActivity mainActivity);
}

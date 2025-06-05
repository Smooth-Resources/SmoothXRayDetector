package com.smoothresources.smoothxraydetector.module;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.smoothresources.smoothxraydetector.service.UserService;

public class UserModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(UserService.class).in(Singleton.class);
    }
}

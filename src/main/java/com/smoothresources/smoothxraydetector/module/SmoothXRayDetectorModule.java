package com.smoothresources.smoothxraydetector.module;

import com.google.inject.AbstractModule;
import com.smoothresources.smoothxraydetector.SmoothXRayDetector;

public class SmoothXRayDetectorModule extends AbstractModule {

    private final SmoothXRayDetector plugin;

    public SmoothXRayDetectorModule(SmoothXRayDetector plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void configure() {
        bind(SmoothXRayDetector.class).toInstance(plugin);
    }
}

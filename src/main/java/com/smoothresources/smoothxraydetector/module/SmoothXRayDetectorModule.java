package com.smoothresources.smoothxraydetector.module;

import com.google.inject.AbstractModule;
import com.smoothresources.smoothbase.common.task.TaskManager;
import com.smoothresources.smoothxraydetector.SmoothXRayDetector;

public class SmoothXRayDetectorModule extends AbstractModule {

    private final SmoothXRayDetector plugin;
    private final TaskManager taskManager;

    public SmoothXRayDetectorModule(SmoothXRayDetector plugin, TaskManager taskManager) {
        this.plugin = plugin;
        this.taskManager = taskManager;
    }

    @Override
    protected void configure() {
        bind(SmoothXRayDetector.class).toInstance(plugin);
        bind(TaskManager.class).toInstance(taskManager);
    }
}

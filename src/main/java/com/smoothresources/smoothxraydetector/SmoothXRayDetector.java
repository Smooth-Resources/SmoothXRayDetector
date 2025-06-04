package com.smoothresources.smoothxraydetector;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.smoothresources.smoothbase.common.file.YAMLFile;
import com.smoothresources.smoothbase.paper.file.PaperYAMLFile;
import com.smoothresources.smoothxraydetector.module.ConfigurationModule;
import com.smoothresources.smoothxraydetector.module.SmoothXRayDetectorModule;
import org.bukkit.plugin.java.JavaPlugin;

public final class SmoothXRayDetector extends JavaPlugin {

    private static Injector injector;

    @Override
    public void onEnable() {
        // Plugin startup logic
        YAMLFile config = new PaperYAMLFile(this, "config");
        YAMLFile messages = new PaperYAMLFile(this, "messages");

        injector = Guice.createInjector(
                new SmoothXRayDetectorModule(this),
                new ConfigurationModule(config, messages)
        );
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Injector getInjector() {
        return injector;
    }
}

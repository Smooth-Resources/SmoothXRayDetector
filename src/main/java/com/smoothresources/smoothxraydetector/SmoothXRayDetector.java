package com.smoothresources.smoothxraydetector;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.smoothresources.smoothbase.common.file.YAMLFile;
import com.smoothresources.smoothbase.paper.file.PaperYAMLFile;
import com.smoothresources.smoothbase.paper.task.PaperTaskManager;
import com.smoothresources.smoothxraydetector.listener.BlockBreakListener;
import com.smoothresources.smoothxraydetector.listener.PlayerJoinListener;
import com.smoothresources.smoothxraydetector.listener.PlayerQuitListener;
import com.smoothresources.smoothxraydetector.module.ConfigurationModule;
import com.smoothresources.smoothxraydetector.module.SmoothXRayDetectorModule;
import com.smoothresources.smoothxraydetector.module.UserModule;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SmoothXRayDetector extends JavaPlugin {

    private static Injector injector;

    @Override
    public void onEnable() {
        // Plugin startup logic
        YAMLFile config = new PaperYAMLFile(this, "config");
        YAMLFile messages = new PaperYAMLFile(this, "messages");

        injector = Guice.createInjector(
                new SmoothXRayDetectorModule(this, new PaperTaskManager(this)),
                new ConfigurationModule(config, messages),
                new UserModule()
        );

        BlockBreakListener blockBreakListener = injector.getInstance(BlockBreakListener.class);
        PlayerJoinListener playerJoinListener = injector.getInstance(PlayerJoinListener.class);
        PlayerQuitListener playerQuitListener = injector.getInstance(PlayerQuitListener.class);

        Bukkit.getPluginManager().registerEvents(blockBreakListener, this);
        Bukkit.getPluginManager().registerEvents(playerJoinListener, this);
        Bukkit.getPluginManager().registerEvents(playerQuitListener, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Injector getInjector() {
        return injector;
    }
}

package com.smoothresources.smoothxraydetector.module;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.smoothresources.smoothbase.common.file.YAMLFile;
import com.smoothresources.smoothbase.paper.file.PaperYAMLFile;

public class ConfigurationModule extends AbstractModule {
    private final YAMLFile config;
    private final YAMLFile messages;

    public ConfigurationModule(YAMLFile config, YAMLFile messages) {
        this.config = config;
        this.messages = messages;
    }

    @Override
    protected void configure() {
        bind(YAMLFile.class).annotatedWith(Names.named("config")).toInstance(config);
        bind(PaperYAMLFile.class).annotatedWith(Names.named("config")).toInstance((PaperYAMLFile) config);
        bind(YAMLFile.class).annotatedWith(Names.named("messages")).toInstance(messages);
    }
}

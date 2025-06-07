package com.smoothresources.smoothxraydetector.command;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.smoothresources.smoothbase.common.command.Command;
import com.smoothresources.smoothbase.common.file.YAMLFile;
import com.smoothresources.smoothbase.common.placeholder.PlaceholderBuilder;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public abstract class DefaultCommand extends Command {

    @Inject @Named("messages")
    private YAMLFile messages;

    @Override
    public @NotNull Component getNotPlayerErrorMessage() {
        return messages.getComponent("global", "not-a-player");
    }

    @Override
    public @NotNull Component getPermissionErrorMessage() {
        return messages.getComponent("global", "no-permission");
    }

    @Override
    public @NotNull Component getUsageErrorMessage() {
        HashMap<String, List<String>> placeholders = new PlaceholderBuilder()
                .add("%usage%", getUsage())
                .build();

        return messages.getComponent(placeholders, "global", "wrong-usage");
    }
}

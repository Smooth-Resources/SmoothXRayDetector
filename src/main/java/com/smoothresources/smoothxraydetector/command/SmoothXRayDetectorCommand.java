package com.smoothresources.smoothxraydetector.command;


import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.smoothresources.smoothbase.common.command.Command;
import com.smoothresources.smoothbase.common.command.CommandSender;
import com.smoothresources.smoothbase.common.file.YAMLFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SmoothXRayDetectorCommand extends Command {

    @Inject @Named("config")
    private YAMLFile config;
    @Inject @Named("messages")
    private YAMLFile messages;

    @Override
    public @NotNull String getName() {
        return "smoothxraydetector";
    }

    @Override
    public @NotNull List<String> getAliases() {
        return List.of("xraydetector");
    }

    @Override
    public @Nullable String getPermission() {
        return "smoothxraydetector.command.reload";
    }

    @Override
    public boolean checkArgs() {
        return true;
    }

    @Override
    public int getArgsLength() {
        return 1;
    }

    @Override
    public @NotNull String getUsage() {
        return "/xraydetector reload";
    }

    @Override
    public boolean mustBePlayer() {
        return false;
    }

    @Override
    public boolean executeAsync() {
        return false;
    }

    @Override
    public void registerSubcommands() {

    }

    @Override
    public void onCommand(@NotNull CommandSender sender, @NotNull String[] args) {
        config.reload();
        messages.reload();

        sender.sendMessage(messages.getComponent("command", "reload", "success"));
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length == 1) {
            return List.of("reload");
        }

        return null;
    }
}

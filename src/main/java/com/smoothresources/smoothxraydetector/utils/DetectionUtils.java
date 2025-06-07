package com.smoothresources.smoothxraydetector.utils;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.smoothresources.smoothbase.common.file.YAMLFile;
import com.smoothresources.smoothbase.common.placeholder.PlaceholderBuilder;
import com.smoothresources.smoothbase.common.placeholder.PlaceholderReplacer;
import com.smoothresources.smoothbase.common.webhook.DiscordWebHook;
import com.smoothresources.smoothbase.paper.file.PaperYAMLFile;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class DetectionUtils {

    @Inject @Named("config")
    private PaperYAMLFile config;
    @Inject @Named("messages")
    private YAMLFile messages;

    public Optional<String> getDetectionIdByMaterialAndWorld(Material material, World world) {
        Set<Object> detections = config.getNode("detections").childrenMap().keySet();
        for (Object detectionKey : detections) {
            String detection = detectionKey.toString();


            List<Material> detectionMaterials = config.getMaterialList("detections", detection, "materials");
            if (!detectionMaterials.contains(material)) continue;

            World detectionWorld = Bukkit.getWorld(config.getString("detections", detection, "world"));
            if (detectionWorld == null || detectionWorld.getUID() != world.getUID()) continue;

            return Optional.of(detection);
        }

        return Optional.empty();
    }

    public int getMinBlockBetweenLastByDetection(String detection) {
        return config.getInt("detections", detection, "requirements", "min-blocks-between-last");
    }

    public int getMaxYByDetection(String detection) {
        return config.getInt("detections", detection, "requirements", "max-y");
    }

    public int getMinYByDetection(String detection) {
        return config.getInt("detections", detection, "requirements", "min-y");
    }

    public int getAverageMinSamplesByDetection(String detection) {
        return config.getInt("detections", detection, "average-calculation", "min-samples");
    }

    public int getAverageMaxSamplesByDetection(String detection) {
        return config.getInt("detections", detection, "average-calculation", "max-samples");
    }

    public double getAverageMinSecondsByDetection(String detection) {
        return config.getDouble("detections", detection, "average-calculation", "min-seconds");
    }

    public void sendXRayDetectedWebhook(Player player, String detection, double averageSeconds) {
        if (!config.getBoolean("detections", detection, "on-detection", "send-discord-webhook")) return;

        HashMap<String, List<String>> placeholders = new PlaceholderBuilder()
                .add("%player%", player.getName())
                .add("%detection%", detection)
                .add("%average%", String.format("%.2f", averageSeconds))
                .build();

        DiscordWebHook webHook = new DiscordWebHook(config.getString("discord-webhook", "xray-detection", "url"));
        webHook.setAvatarUrl(config.getString("discord-webhook", "xray-detection", "avatar"));
        webHook.setUsername(config.getString("discord-webhook", "xray-detection", "username"));
        webHook.setTts(false);
        webHook.addEmbed(new DiscordWebHook.EmbedObject()
                .setTitle(config.getString(placeholders, "discord-webhook", "xray-detection", "title"))
                .setDescription(config.getString(placeholders, "discord-webhook", "xray-detection", "description"))
                .setColor(new Color(
                        config.getInt("discord-webhook", "xray-detection", "color", "r"),
                        config.getInt("discord-webhook", "xray-detection", "color", "g"),
                        config.getInt("discord-webhook", "xray-detection", "color", "b")
                )));


        try {
            webHook.execute();
        } catch (IOException e) {
            System.err.println("Exception sending XRay discord webhook.");
            e.printStackTrace();
        }
    }

    public void executeDetectionCommands(Player player, String detection, double averageSeconds) {
        HashMap<String, List<String>> placeholders = new PlaceholderBuilder()
                .add("%player%", player.getName())
                .add("%detection%", detection)
                .add("%average%", String.format("%.2f", averageSeconds))
                .build();

        config.getStringList(placeholders, "detections", detection, "on-detection", "commands").forEach(command -> {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        });
    }

    public void sendDetectionStaffNotification(Player player, String detection, double averageSeconds) {
        if (!config.getBoolean("detections", detection, "on-detection", "staff-notification")) return;

        HashMap<String, List<String>> placeholders = new PlaceholderBuilder()
                .add("%player%", player.getName())
                .add("%detection%", detection)
                .add("%average%", String.format("%.2f", averageSeconds))
                .build();

        Component staffNotification = messages.getComponent(placeholders, "detection", "staff-notification");
        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            if (!onlinePlayer.hasPermission("smoothxraydetector.detection.notify")) return;

            onlinePlayer.sendMessage(staffNotification);
        });
    }
}

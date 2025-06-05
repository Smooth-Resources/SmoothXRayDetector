package com.smoothresources.smoothxraydetector.listener;

import com.google.inject.Inject;
import com.smoothresources.smoothxraydetector.entity.User;
import com.smoothresources.smoothxraydetector.service.UserService;
import com.smoothresources.smoothxraydetector.utils.DetectionUtils;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    @Inject
    private UserService userService;
    @Inject
    private DetectionUtils detectionUtils;

    @EventHandler(ignoreCancelled = true)
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Material material = block.getType();
        Location location = block.getLocation();

        String detection = detectionUtils.getDetectionIdByMaterialAndWorld(material, block.getWorld()).orElse(null);
        if (detection == null) return;

        int maxY = detectionUtils.getMaxYByDetection(detection);
        if (location.getBlockY() > maxY) return;

        int minY = detectionUtils.getMinYByDetection(detection);
        if (location.getBlockY() < minY) return;

        User user = userService.getUserByUUID(player.getUniqueId()).orElseThrow();
        int minBlocksBetweenLast = detectionUtils.getMinBlockBetweenLastByDetection(detection);
        if (user.getDistanceBetweenLastSampleByBlock(block) < minBlocksBetweenLast) return;

        int maxSamples = detectionUtils.getAverageMaxSamplesByDetection(detection);
        user.addSample(block, maxSamples);

        int minSamples = detectionUtils.getAverageMinSamplesByDetection(detection);
        if (user.getSamplesAmountByMaterial(material) < minSamples) return;

        double averageSecondsBetweenSamples = user.getAverageSecondsBetweenSamples(material);
        double minAverageSeconds = detectionUtils.getAverageMinSecondsByDetection(detection);

        if (averageSecondsBetweenSamples >= minAverageSeconds) return;

        player.sendMessage("[DEBUG] Límite detectado.");
    }
}

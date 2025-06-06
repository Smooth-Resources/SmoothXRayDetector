package com.smoothresources.smoothxraydetector.utils;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.smoothresources.smoothbase.paper.file.PaperYAMLFile;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class DetectionUtils {

    @Inject @Named("config")
    private PaperYAMLFile config;

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
}

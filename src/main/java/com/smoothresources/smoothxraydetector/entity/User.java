package com.smoothresources.smoothxraydetector.entity;

import com.google.gson.annotations.SerializedName;
import org.bukkit.Location;
import org.bukkit.Material;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class User {

    @SerializedName("_id")
    private final UUID id;
    private final HashMap<Material, List<Record>> records;
    private final HashMap<Material, Integer> warnings;

    public User(UUID id) {
        this.id = id;
        this.records = new HashMap<>();
        this.warnings = new HashMap<>();
    }

    public UUID getId() {
        return id;
    }

    public HashMap<Material, List<Record>> getRecords() {
        return records;
    }

    public HashMap<Material, Integer> getWarnings() {
        return warnings;
    }

    public void addRecord(Material material, Record record) {
        records.computeIfAbsent(material, k -> new ArrayList<>()).add(record);
    }

    public List<Record> getRecordsByMaterial(Material material) {
        return records.getOrDefault(material, new ArrayList<>());
    }

    public void addWarning(Material material) {
        warnings.put(material, warnings.getOrDefault(material, 0) + 1);
    }

    public int getWarningsByMaterial(Material material) {
        return warnings.getOrDefault(material, 0);
    }

    public double getDistanceBetweenLastRecordByMaterial(Material material, Location location) {
        List<Record> materialRecords = getRecordsByMaterial(material);
        if (materialRecords.size() < 2) {
            return Integer.MAX_VALUE; // Not enough records to calculate distance
        }

        Record lastRecord = materialRecords.get(materialRecords.size() - 1);
        if (lastRecord.getLocation().getWorld() != location.getWorld()) {
            return Integer.MAX_VALUE;
        }

        return lastRecord.getLocation().distance(location);
    }

    public double getAverageSecondsBetweenRecords(Material material) {
        List<Record> materialRecords = getRecordsByMaterial(material);
        if (materialRecords.size() < 2) {
            return Integer.MAX_VALUE;
        }

        long totalSeconds = 0;
        for (int i = 1; i < materialRecords.size(); i++) {
            long seconds = Duration.between(materialRecords.get(i).getTime(), materialRecords.get(i - 1).getTime()).toSeconds();
            totalSeconds += seconds;
        }

        return (double) totalSeconds / (materialRecords.size() - 1);
    }
}

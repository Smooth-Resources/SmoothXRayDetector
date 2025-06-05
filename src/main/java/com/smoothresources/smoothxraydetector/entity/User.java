package com.smoothresources.smoothxraydetector.entity;

import org.bukkit.Material;
import org.bukkit.block.Block;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User {

    private final HashMap<Material, List<Sample>> samples;

    public User() {
        this.samples = new HashMap<>();
    }

    public HashMap<Material, List<Sample>> getSamples() {
        return samples;
    }

    public int getSamplesAmountByMaterial(Material material) {
        return getSamplesByMaterial(material).size();
    }

    public void addSample(Block block, int maxSamples) {
        Sample sample = new Sample(block.getLocation());
        this.samples.computeIfAbsent(block.getType(), k -> new ArrayList<>()).add(sample);

        List<Sample> samples = getSamplesByMaterial(block.getType());
        if (samples.size() <= maxSamples) return;

        samples.remove(0);
    }

    public List<Sample> getSamplesByMaterial(Material material) {
        return samples.getOrDefault(material, new ArrayList<>());
    }

    public double getDistanceBetweenLastSampleByBlock(Block block) {
        List<Sample> materialSamples = getSamplesByMaterial(block.getType());
        if (materialSamples.size() < 2) {
            return Integer.MAX_VALUE; // Not enough samples to calculate distance
        }

        Sample lastSample = materialSamples.get(materialSamples.size() - 1);
        if (lastSample.getLocation().getWorld() != block.getWorld()) {
            return Integer.MAX_VALUE;
        }

        return lastSample.getLocation().distance(block.getLocation());
    }

    public double getAverageSecondsBetweenSamples(Material material) {
        List<Sample> materialSamples = getSamplesByMaterial(material);
        if (materialSamples.size() < 2) {
            return Integer.MAX_VALUE;
        }

        long totalSeconds = 0;
        for (int i = 1; i < materialSamples.size(); i++) {
            long seconds = Duration.between(materialSamples.get(i).getTime(), materialSamples.get(i - 1).getTime()).toSeconds();
            totalSeconds += seconds;
        }

        return (double) totalSeconds / (materialSamples.size() - 1);
    }
}

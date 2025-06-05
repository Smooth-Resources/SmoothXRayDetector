package com.smoothresources.smoothxraydetector.entity;

import org.bukkit.Location;

import java.time.Instant;

public class Sample {

    private final Instant time;
    private final Location location;

    public Sample(Instant time, Location location) {
        this.time = time;
        this.location = location;
    }

    public Sample(Location location) {
        this.time = Instant.now();
        this.location = location;
    }

    public Instant getTime() {
        return time;
    }

    public Location getLocation() {
        return location;
    }
}

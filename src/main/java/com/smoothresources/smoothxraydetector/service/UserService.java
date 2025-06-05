package com.smoothresources.smoothxraydetector.service;

import com.smoothresources.smoothxraydetector.entity.User;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class UserService {

    private final HashMap<UUID, User> users = new HashMap<>();

    public void handleJoin(Player player) {
        users.put(player.getUniqueId(), new User());
    }

    public void handleQuit(Player player) {
        users.remove(player.getUniqueId());
    }

    public Optional<User> getUserByUUID(UUID uuid) {
        return Optional.of(users.get(uuid));
    }
}

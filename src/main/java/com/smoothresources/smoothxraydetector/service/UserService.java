package com.smoothresources.smoothxraydetector.service;

import com.google.inject.Inject;
import com.smoothresources.smoothbase.common.task.TaskManager;
import com.smoothresources.smoothxraydetector.SmoothXRayDetector;
import com.smoothresources.smoothxraydetector.entity.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class UserService {

    @Inject
    private SmoothXRayDetector plugin;
    @Inject
    private TaskManager taskManager;

    private final HashMap<UUID, User> users = new HashMap<>();

    public void handleJoin(Player player) {
        if (users.containsKey(player.getUniqueId())) return;

        users.put(player.getUniqueId(), new User());
    }

    public void handleQuit(UUID uuid) {
        taskManager.runTaskLaterAsync(() -> {
            if (Bukkit.getPlayer(uuid) != null) return;

            users.remove(uuid);
        }, 1000 * 3600);
    }

    public Optional<User> getUserByUUID(UUID uuid) {
        return Optional.of(users.get(uuid));
    }
}

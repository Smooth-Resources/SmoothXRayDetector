package com.smoothresources.smoothxraydetector.listener;

import com.google.inject.Inject;
import com.smoothresources.smoothxraydetector.service.UserService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @Inject
    private UserService userService;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        userService.handleJoin(event.getPlayer());
    }
}

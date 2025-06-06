package com.smoothresources.smoothxraydetector.listener;

import com.google.inject.Inject;
import com.smoothresources.smoothxraydetector.service.UserService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @Inject
    private UserService userService;

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        userService.handleQuit(event.getPlayer());
    }
}

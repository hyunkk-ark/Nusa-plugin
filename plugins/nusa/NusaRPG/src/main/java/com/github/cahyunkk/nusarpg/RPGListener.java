package com.github.cahyunkk.nusarpg;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class RPGListener implements Listener {

    private final NusaRPG plugin;

    public RPGListener(NusaRPG plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Inisialisasi data RPG untuk pemain baru
        plugin.getStatsManager().setStat(event.getPlayer().getUniqueId(), "level", 1);
        plugin.getJobSystem().assignJob(event.getPlayer().getUniqueId(), "Adventurer");
    }
}

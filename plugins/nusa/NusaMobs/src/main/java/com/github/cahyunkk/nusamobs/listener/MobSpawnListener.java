package com.github.cahyunkk.nusamobs.listener;

import com.github.cahyunkk.nusamobs.NusaMobs;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class MobSpawnListener implements Listener {
    private final NusaMobs plugin;
    public MobSpawnListener(NusaMobs p) { this.plugin = p; }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent e) {
        // Allow natural spawning — custom mobs handled by SpawnManager
    }
}

package com.github.cahyunkk.nusaoptimize;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;

public class OptimizeListener implements Listener {

    private final NusaOptimize plugin;

    public OptimizeListener(NusaOptimize plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onServerLoad(ServerLoadEvent event) {
        plugin.getOptimizeManager().optimizeChunks();
        plugin.getOptimizeManager().optimizeEntities();
        if (plugin.getConfig().getBoolean("optimization.auto-gc", true)) {
            plugin.getOptimizeManager().triggerGC();
        }
    }
}

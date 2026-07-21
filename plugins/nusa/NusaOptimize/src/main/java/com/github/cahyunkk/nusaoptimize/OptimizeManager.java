package com.github.cahyunkk.nusaoptimize;

public class OptimizeManager {

    private final NusaOptimize plugin;

    public OptimizeManager(NusaOptimize plugin) {
        this.plugin = plugin;
    }

    public void triggerGC() {
        System.gc();
        plugin.getLogger().info("GC triggered automatically.");
    }

    public void optimizeEntities() {
        int limit = plugin.getConfig().getInt("optimization.entity-limit", 500);
        plugin.getLogger().info("Entity optimization: limit set to " + limit);
    }

    public void optimizeChunks() {
        boolean enabled = plugin.getConfig().getBoolean("optimization.chunk-optimize", true);
        plugin.getLogger().info("Chunk optimization: " + (enabled ? "enabled" : "disabled"));
    }
}

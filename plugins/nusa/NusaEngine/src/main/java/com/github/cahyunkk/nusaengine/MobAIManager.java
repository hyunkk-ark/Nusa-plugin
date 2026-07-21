package com.github.cahyunkk.nusaengine;

public class MobAIManager {

    private final NusaEngine plugin;

    public MobAIManager(NusaEngine plugin) {
        this.plugin = plugin;
    }

    public void setBehaviorMode(String mobId, String mode) {
        plugin.getConfig().set("mobs." + mobId + ".behavior", mode);
    }

    public String getBehaviorMode(String mobId) {
        return plugin.getConfig().getString("mobs." + mobId + ".behavior", "default");
    }
}

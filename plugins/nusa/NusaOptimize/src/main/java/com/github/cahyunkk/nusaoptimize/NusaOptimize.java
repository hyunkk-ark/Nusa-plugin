package com.github.cahyunkk.nusaoptimize;

import org.bukkit.plugin.java.JavaPlugin;

public class NusaOptimize extends JavaPlugin {

    private static NusaOptimize instance;
    private OptimizeManager optimizeManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        this.optimizeManager = new OptimizeManager(this);
        getCommand("nusaoptimize").setExecutor(new OptimizeCommand(this));
        getServer().getPluginManager().registerEvents(new OptimizeListener(this), this);

        getLogger().info("NusaOptimize v" + getDescription().getVersion() + " enabled.");
        getLogger().info("Anti-lag: automatic entity & chunk optimization active.");
        getLogger().info("Bedrock support: automatic resource pack (no manual config needed).");
        if (getServer().getPluginManager().getPlugin("NusaEngine") != null) {
            getLogger().info("NusaEngine integration active — engine optimization.");
        }
        if (getServer().getPluginManager().getPlugin("NusaSkills") != null) {
            getLogger().info("NusaSkills integration active.");
        }
        if (getServer().getPluginManager().getPlugin("NusaRPG") != null) {
            getLogger().info("NusaRPG integration active.");
        }
        if (getServer().getPluginManager().getPlugin("NusaMobs") != null) {
            getLogger().info("NusaMobs integration active.");
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("NusaOptimize disabled.");
    }

    public static NusaOptimize getInstance() {
        return instance;
    }

    public OptimizeManager getOptimizeManager() {
        return optimizeManager;
    }
}

package com.github.cahyunkk.nusaengine;

import org.bukkit.plugin.java.JavaPlugin;

public class NusaEngine extends JavaPlugin {

    private static NusaEngine instance;
    private MobStatsManager statsManager;
    private MobAIManager aiManager;
    private EngineResourcePack resourcePack;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        this.statsManager = new MobStatsManager(this);
        this.aiManager = new MobAIManager(this);
        this.resourcePack = new EngineResourcePack(this);

        getLogger().info("NusaEngine v" + getDescription().getVersion() + " enabled.");
        getLogger().info("Bedrock support: automatic resource pack generation (no manual Bedrock config needed).");
        resourcePack.generate();
        if (getServer().getPluginManager().getPlugin("NusaMobs") != null) {
            getLogger().info("NusaMobs detected — engine integration active.");
        }
        if (getServer().getPluginManager().getPlugin("NusaSkills") != null) {
            getLogger().info("NusaSkills detected — skill engine active.");
        }
        if (getServer().getPluginManager().getPlugin("NusaRPG") != null) {
            getLogger().info("NusaRPG detected — RPG engine integration active.");
        }
        if (getServer().getPluginManager().getPlugin("NusaOptimize") != null) {
            getLogger().info("NusaOptimize detected — optimization engine active.");
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("NusaEngine disabled.");
    }

    public static NusaEngine getInstance() {
        return instance;
    }

    public MobStatsManager getStatsManager() {
        return statsManager;
    }

    public MobAIManager getAIManager() {
        return aiManager;
    }

    public EngineResourcePack getResourcePack() {
        return resourcePack;
    }
}

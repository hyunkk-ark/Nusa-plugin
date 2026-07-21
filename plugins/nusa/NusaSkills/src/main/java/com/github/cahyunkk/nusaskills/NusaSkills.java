package com.github.cahyunkk.nusaskills;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class NusaSkills extends JavaPlugin {

    private static NusaSkills instance;
    private SkillManager skillManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        this.skillManager = new SkillManager(this);

        getCommand("nusaskills").setExecutor(new SkillsCommand(this));
        getServer().getPluginManager().registerEvents(new SkillListener(this), this);

        if (Bukkit.getPluginManager().getPlugin("NusaItems") != null) {
            getLogger().info("[NusaSkills] NusaItems detected — integration active.");
        }
        if (Bukkit.getPluginManager().getPlugin("NusaMobs") != null) {
            getLogger().info("[NusaSkills] NusaMobs detected — integration active.");
        }
        if (Bukkit.getPluginManager().getPlugin("NusaRPG") != null) {
            getLogger().info("[NusaSkills] NusaRPG detected — skill integration active.");
        }
        if (Bukkit.getPluginManager().getPlugin("NusaEngine") != null) {
            getLogger().info("[NusaSkills] NusaEngine detected — engine integration active.");
        }

        getLogger().info("NusaSkills v" + getDescription().getVersion() + " enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("NusaSkills disabled.");
    }

    public static NusaSkills getInstance() {
        return instance;
    }

    public SkillManager getSkillManager() {
        return skillManager;
    }
}

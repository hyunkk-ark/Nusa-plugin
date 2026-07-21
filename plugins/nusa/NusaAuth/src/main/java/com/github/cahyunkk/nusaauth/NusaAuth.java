package com.github.cahyunkk.nusaauth;

import org.bukkit.plugin.java.JavaPlugin;

public class NusaAuth extends JavaPlugin {

    private static NusaAuth instance;
    private AuthManager authManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        this.authManager = new AuthManager(this);
        getCommand("nusaauth").setExecutor(new AuthCommand(this));
        getServer().getPluginManager().registerEvents(new AuthListener(this), this);

        getLogger().info("NusaAuth v" + getDescription().getVersion() + " enabled.");
        getLogger().info("Auth: automatic session tracking.");
        getLogger().info("Bedrock support: automatic resource pack (no manual config needed).");
        if (getServer().getPluginManager().getPlugin("NusaRPG") != null) {
            getLogger().info("NusaRPG integration active — auth required for RPG.");
        }
        if (getServer().getPluginManager().getPlugin("NusaSkills") != null) {
            getLogger().info("NusaSkills integration active.");
        }
        if (getServer().getPluginManager().getPlugin("NusaItems") != null) {
            getLogger().info("NusaItems integration active.");
        }
        if (getServer().getPluginManager().getPlugin("NusaMobs") != null) {
            getLogger().info("NusaMobs integration active.");
        }
        if (getServer().getPluginManager().getPlugin("NusaEngine") != null) {
            getLogger().info("NusaEngine integration active.");
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("NusaAuth disabled.");
    }

    public static NusaAuth getInstance() {
        return instance;
    }

    public AuthManager getAuthManager() {
        return authManager;
    }
}

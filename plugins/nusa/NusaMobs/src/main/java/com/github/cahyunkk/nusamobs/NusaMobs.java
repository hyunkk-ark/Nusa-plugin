package com.github.cahyunkk.nusamobs;

import com.github.cahyunkk.nusamobs.command.MobsCommand;
import com.github.cahyunkk.nusamobs.listener.*;
import com.github.cahyunkk.nusamobs.mob.MobManager;
import com.github.cahyunkk.nusamobs.spawn.SpawnManager;
import com.github.cahyunkk.nusamobs.util.NusaItemsBridge;
import org.bukkit.plugin.java.JavaPlugin;

public final class NusaMobs extends JavaPlugin {
    private static NusaMobs instance;
    private MobManager mobManager;
    private SpawnManager spawnManager;
    private NusaItemsBridge nusaBridge;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        mobManager = new MobManager(this);
        spawnManager = new SpawnManager(this);
        nusaBridge = new NusaItemsBridge();

        mobManager.loadAll();
        spawnManager.start();

        var cmd = new MobsCommand(this);
        var c = getCommand("nusamobs");
        if (c != null) { c.setExecutor(cmd); c.setTabCompleter(cmd); }

        getServer().getPluginManager().registerEvents(new MobSpawnListener(this), this);
        getServer().getPluginManager().registerEvents(new MobCombatListener(this), this);
        getServer().getPluginManager().registerEvents(new MobDeathListener(this), this);

        getLogger().info("§a✅ NusaMobs loaded! " + mobManager.getMobCount() + " mobs, "
                + (nusaBridge.isAvailable() ? "§2NusaItems linked" : "§7NusaItems not found"));
        // Integrasi semua plugin Nusa
        if (getServer().getPluginManager().getPlugin("NusaItems") != null) {
            getLogger().info("[NusaMobs] NusaItems integration active.");
        }
        if (getServer().getPluginManager().getPlugin("NusaSkills") != null) {
            getLogger().info("[NusaMobs] NusaSkills integration active.");
        }
        if (getServer().getPluginManager().getPlugin("NusaEngine") != null) {
            getLogger().info("[NusaMobs] NusaEngine integration active.");
        }
        if (getServer().getPluginManager().getPlugin("NusaRPG") != null) {
            getLogger().info("[NusaMobs] NusaRPG integration active.");
        }
        if (getServer().getPluginManager().getPlugin("NusaAuth") != null) {
            getLogger().info("[NusaMobs] NusaAuth integration active.");
        }
        if (getServer().getPluginManager().getPlugin("NusaOptimize") != null) {
            getLogger().info("[NusaMobs] NusaOptimize integration active.");
        }
    }

    @Override
    public void onDisable() {
        spawnManager.stop();
        mobManager.clearAll();
        getLogger().info("§cNusaMobs disabled.");
    }

    public void reload() {
        spawnManager.stop();
        mobManager.clearAll();
        mobManager.loadAll();
        spawnManager.start();
        getLogger().info("§aReloaded: " + mobManager.getMobCount() + " mobs");
    }

    public static NusaMobs get() { return instance; }
    public MobManager getMobManager() { return mobManager; }
    public SpawnManager getSpawnManager() { return spawnManager; }
    public NusaItemsBridge getNusaBridge() { return nusaBridge; }
}

package com.github.cahyunkk.nusaitems.hook;
import com.github.cahyunkk.nusaitems.NusaItems;
import org.bukkit.Bukkit;

public class HookManager {
    private final NusaItems plugin;
    private MythicMobsHook mythicMobsHook;
    private boolean mythicMobsEnabled;

    public HookManager(NusaItems p) { this.plugin = p; }

    public void loadAll() {
        if (Bukkit.getPluginManager().isPluginEnabled("MythicMobs")) {
            try { mythicMobsHook = new MythicMobsHook(plugin); mythicMobsHook.register(); mythicMobsEnabled = true;
                plugin.getLogger().info("§a✅ MythicMobs integration enabled!"); }
            catch (Exception e) { plugin.getLogger().warning("§cMythicMobs hook failed: " + e.getMessage()); }
        }
        if (Bukkit.getPluginManager().isPluginEnabled("MMOItems")) plugin.getLogger().info("§a✅ MMOItems detected");
        if (Bukkit.getPluginManager().isPluginEnabled("ItemsAdder")) plugin.getLogger().info("§a✅ ItemsAdder detected");
        if (Bukkit.getPluginManager().isPluginEnabled("Oraxen")) plugin.getLogger().info("§a✅ Oraxen detected");
        if (Bukkit.getPluginManager().isPluginEnabled("EcoItems")) plugin.getLogger().info("§a✅ EcoItems detected");
        if (Bukkit.getPluginManager().isPluginEnabled("EcoEnchants")) plugin.getLogger().info("§a✅ EcoEnchants detected");
        if (Bukkit.getPluginManager().isPluginEnabled("ExecutableItems")) plugin.getLogger().info("§a✅ ExecutableItems detected");
        plugin.getLogger().info("§a📡 " + getEnabledHookCount() + " plugin integration(s) active");
    }

    public void unloadAll() { if (mythicMobsHook != null) { mythicMobsHook.unregister(); } mythicMobsEnabled = false; }
    public boolean isMythicMobsEnabled() { return mythicMobsEnabled; }
    public MythicMobsHook getMythicMobsHook() { return mythicMobsHook; }
    public int getEnabledHookCount() { int c=0; if (mythicMobsEnabled) c++; return c; }
}

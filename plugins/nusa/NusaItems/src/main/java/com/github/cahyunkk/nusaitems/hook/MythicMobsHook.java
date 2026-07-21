package com.github.cahyunkk.nusaitems.hook;
import com.github.cahyunkk.nusaitems.NusaItems;
import com.github.cahyunkk.nusaitems.model.CustomItem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.inventory.ItemStack;
import java.util.Map;

public class MythicMobsHook implements Listener {
    private final NusaItems plugin;
    public MythicMobsHook(NusaItems p) { this.plugin = p; }

    public void register() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        try {
            Class<?> mb = Class.forName("io.lumine.mythic.bukkit.MythicBukkit");
            Object inst = mb.getMethod("inst").invoke(null);
            inst.getClass().getMethod("getItemManager").invoke(inst);
            plugin.getLogger().info("§a  ↳ MythicMobs API hooked — 'nusaitems:id' in MM configs!");
        } catch (ClassNotFoundException e) {
            plugin.getLogger().info("§e  ↳ MythicMobs legacy mode — use /nusaitems give in MM skills");
        } catch (Exception e) {
            plugin.getLogger().warning("§e  ↳ MM API: " + e.getMessage());
        }
    }

    public void unregister() { HandlerList.unregisterAll(this); }

    @EventHandler
    public void onPluginEnable(PluginEnableEvent e) {
        if (e.getPlugin().getName().equals("MythicMobs")) register();
    }

    public ItemStack resolveItem(String id) { return plugin.getItemRegistry().buildItem(id); }

    public ItemStack resolveNamespaced(String ns) {
        if (ns == null) return new ItemStack(Material.AIR);
        return resolveItem(ns.replace("nusaitems:", "").replace("ni:", "").trim());
    }

    public boolean isNusaItemsReference(String ref) {
        return ref != null && (ref.startsWith("nusaitems:") || ref.startsWith("ni:"));
    }

    public Map<String, CustomItem> getAllItems() { return plugin.getItemRegistry().getRegisteredItems(); }
}

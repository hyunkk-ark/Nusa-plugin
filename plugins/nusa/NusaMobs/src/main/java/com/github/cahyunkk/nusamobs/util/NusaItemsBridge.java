package com.github.cahyunkk.nusamobs.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class NusaItemsBridge {
    private final boolean available;
    private Plugin nusaPlugin;

    public NusaItemsBridge() {
        nusaPlugin = Bukkit.getPluginManager().getPlugin("NusaItems");
        available = nusaPlugin != null && nusaPlugin.isEnabled();
    }

    public boolean isAvailable() { return available; }

    public ItemStack getItem(String id) {
        if (!available) return resolveVanilla(id);
        try {
            Class<?> api = Class.forName("com.github.cahyunkk.nusaitems.NusaItemsAPI");
            Object inst = api.getMethod("getInstance").invoke(null);
            if (inst != null) {
                Object item = api.getMethod("getItem", String.class).invoke(inst, id);
                if (item instanceof ItemStack is && is.getType() != Material.AIR) return is;
            }
        } catch (Exception ignored) {}
        return resolveVanilla(id);
    }

    private ItemStack resolveVanilla(String id) {
        try { return new ItemStack(Material.valueOf(id.toUpperCase())); }
        catch (Exception e) { return new ItemStack(Material.AIR); }
    }
}

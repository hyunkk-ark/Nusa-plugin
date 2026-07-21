package com.github.cahyunkk.nusaitems;
import com.github.cahyunkk.nusaitems.model.CustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Collection;

public final class NusaItemsAPI {
    private static NusaItems plugin;
    private NusaItemsAPI() {}
    static void init(NusaItems p) { plugin = p; }
    static void disable() { plugin = null; }

    public static NusaItemsAPI getInstance() { return plugin != null ? new NusaItemsAPI() : null; }
    public boolean isEnabled() { return plugin != null && plugin.isEnabled(); }
    public ItemStack getItem(String id) {
        if (!isEnabled()) return new ItemStack(Material.AIR);
        ItemStack item = plugin.getItemRegistry().buildItem(id);
        return item != null ? item : new ItemStack(Material.AIR);
    }
    public CustomItem getCustomItem(String id) { return isEnabled() ? plugin.getItemRegistry().getItem(id) : null; }
    public Collection<String> getItemIds() { return isEnabled() ? plugin.getItemRegistry().getItemIds() : java.util.List.of(); }
    public int getItemCount() { return isEnabled() ? plugin.getItemRegistry().getItemCount() : 0; }
    public boolean isNusaItem(ItemStack stack) { return isEnabled() && plugin.getItemRegistry().getItemId(stack) != null; }
    public String getItemId(ItemStack stack) { return isEnabled() ? plugin.getItemRegistry().getItemId(stack) : null; }
    public JavaPlugin getPlugin() { return plugin; }
}

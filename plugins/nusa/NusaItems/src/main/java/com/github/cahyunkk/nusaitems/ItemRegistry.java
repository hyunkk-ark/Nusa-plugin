package com.github.cahyunkk.nusaitems;
import com.github.cahyunkk.nusaitems.model.CustomItem;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import java.util.*;

public class ItemRegistry {
    private final NusaItems plugin;
    private final Map<String, CustomItem> items = new LinkedHashMap<>();
    private final NamespacedKey itemKey;

    public ItemRegistry(NusaItems p) { this.plugin = p; this.itemKey = new NamespacedKey(p, "nusaitems_id"); }

    public void registerAll() {
        plugin.getServer().clearRecipes(); items.clear();
        for (CustomItem item : plugin.getConfigLoader().getItems().values()) items.put(item.getId(), item);
        plugin.getLogger().info("Registered " + items.size() + " custom items");
    }

    public ItemStack buildItem(String id) {
        CustomItem ci = items.get(id);
        if (ci == null) return null;
        ItemStack stack = ci.build();
        var meta = stack.getItemMeta();
        if (meta != null) { meta.getPersistentDataContainer().set(itemKey, PersistentDataType.STRING, id); stack.setItemMeta(meta); }
        return stack;
    }

    public String getItemId(ItemStack stack) {
        if (stack == null || !stack.hasItemMeta()) return null;
        return stack.getItemMeta().getPersistentDataContainer().get(itemKey, PersistentDataType.STRING);
    }

    public CustomItem getItem(String id) { return items.get(id); }
    public int getItemCount() { return items.size(); }
    public Collection<String> getItemIds() { return items.keySet(); }
    public Map<String, CustomItem> getRegisteredItems() { return items; }
}

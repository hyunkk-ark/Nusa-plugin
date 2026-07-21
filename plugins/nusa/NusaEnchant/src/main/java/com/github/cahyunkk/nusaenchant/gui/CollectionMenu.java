package com.github.cahyunkk.nusaenchant.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CollectionMenu extends Menu {

    public CollectionMenu(Player player) {
        super(player);
    }

    @Override
    public String getTitle() {
        return ChatColor.YELLOW + "✦ My Enchant Collection ✦";
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public void setupItems() {
        // Placeholder slots for collected enchants
        for (int i = 0; i < 45; i++) {
            setItem(i, Material.BOOK, "§7Empty Slot", "§8You have not collected", "§8any enchant yet");
        }

        setItem(49, Material.ARROW, "§cBack", "§7Return to main menu");
    }

    private void setItem(int slot, Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        if (lore.length > 0) meta.setLore(java.util.Arrays.asList(lore));
        item.setItemMeta(meta);
        inventory.setItem(slot, item);
    }

    @Override
    public void handleClick(InventoryClickEvent event) {
        event.setCancelled(true);
        if (event.getSlot() == 49) {
            new MainMenu(player).open();
        }
    }
}
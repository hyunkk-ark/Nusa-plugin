package com.github.cahyunkk.nusaenchant.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CombineMenu extends Menu {

    public CombineMenu(Player player) {
        super(player);
    }

    @Override
    public String getTitle() {
        return ChatColor.AQUA + "✦ Combine Enchants ✦";
    }

    @Override
    public int getSize() {
        return 27;
    }

    @Override
    public void setupItems() {
        setItem(11, Material.ENCHANTED_BOOK, "§aPlace First Book", "§7Drag enchant book here");
        setItem(13, Material.ANVIL, "§bCombine", "§7Click to combine");
        setItem(15, Material.ENCHANTED_BOOK, "§aPlace Second Book", "§7Drag enchant book here");

        setItem(22, Material.ARROW, "§cBack", "§7Return to main menu");
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
        if (event.getSlot() == 22) {
            new MainMenu(player).open();
        }
    }
}
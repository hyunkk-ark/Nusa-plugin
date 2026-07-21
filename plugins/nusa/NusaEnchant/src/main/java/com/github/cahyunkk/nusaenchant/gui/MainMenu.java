package com.github.cahyunkk.nusaenchant.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class MainMenu extends Menu {

    public MainMenu(Player player) {
        super(player);
    }

    @Override
    public String getTitle() {
        return ChatColor.DARK_PURPLE + "✦ NusaEnchant ✦";
    }

    @Override
    public int getSize() {
        return 27;
    }

    @Override
    public void setupItems() {
        // Enchant Book
        setItem(10, Material.ENCHANTED_BOOK, "§aEnchant Book", "§7Browse all available enchants");

        // Combine
        setItem(12, Material.ANVIL, "§bCombine Enchants", "§7Combine multiple enchants");

        // Forge
        setItem(14, Material.SMITHING_TABLE, "§6Forge", "§7Forge powerful enchants");

        // Collection
        setItem(16, Material.BOOK, "§eMy Collection", "§7View your enchants");

        // Close
        setItem(22, Material.BARRIER, "§cClose", "§7Close this menu");
    }

    private void setItem(int slot, Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        inventory.setItem(slot, item);
    }

    @Override
    public void handleClick(InventoryClickEvent event) {
        event.setCancelled(true);
        Player p = (Player) event.getWhoClicked();

        switch (event.getSlot()) {
            case 10:
                new EnchantBookMenu(p).open();
                break;
            case 12:
                new CombineMenu(p).open();
                break;
            case 14:
                p.sendMessage("§6Opening Forge...");
                break;
            case 16:
                p.sendMessage("§eOpening Collection...");
                break;
            case 22:
                p.closeInventory();
                break;
        }
    }
}
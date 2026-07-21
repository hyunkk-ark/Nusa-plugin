package com.github.cahyunkk.nusaenchant.gui;

import com.github.cahyunkk.nusaenchant.NusaEnchant;
import com.github.cahyunkk.nusaenchant.enchant.Enchant;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class EnchantBookMenu extends Menu {

    private final NusaEnchant plugin;

    public EnchantBookMenu(Player player) {
        super(player);
        this.plugin = NusaEnchant.getInstance();
    }

    @Override
    public String getTitle() {
        return ChatColor.DARK_PURPLE + "✦ Enchant Book ✦";
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public void setupItems() {
        int slot = 0;
        for (Enchant enchant : plugin.getEnchantRegistry().getAllEnchants().values()) {
            if (slot >= 45) break;

            ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', enchant.getDisplayName()));

            List<String> lore = new ArrayList<>();
            lore.addAll(enchant.getDescription().stream()
                    .map(s -> ChatColor.translateAlternateColorCodes('&', s))
                    .toList());
            lore.add("");
            lore.add("§7Category: §f" + enchant.getCategory());
            lore.add("§7Rarity: §f" + enchant.getRarity());
            lore.add("§7Max Level: §f" + enchant.getMaxLevel());
            lore.add("");
            lore.add("§eClick to preview");

            meta.setLore(lore);
            item.setItemMeta(meta);
            inventory.setItem(slot++, item);
        }

        // Back button
        ItemStack back = new ItemStack(Material.ARROW);
        ItemMeta backMeta = back.getItemMeta();
        backMeta.setDisplayName("§cBack");
        back.setItemMeta(backMeta);
        inventory.setItem(49, back);
    }

    @Override
    public void handleClick(InventoryClickEvent event) {
        event.setCancelled(true);
        if (event.getCurrentItem() == null) return;

        if (event.getSlot() == 49) {
            new MainMenu(player).open();
        }
    }
}
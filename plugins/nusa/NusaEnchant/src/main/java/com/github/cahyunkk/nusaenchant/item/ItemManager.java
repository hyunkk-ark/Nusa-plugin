package com.github.cahyunkk.nusaenchant.item;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemManager {

    public static ItemStack createEnchantBook(String enchantId, int level) {
        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = book.getItemMeta();
        meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Enchant Book");
        meta.setLore(Arrays.asList(
                "§7Enchant: §f" + enchantId,
                "§7Level: §f" + level,
                "",
                "§eRight-click on item to apply"
        ));
        book.setItemMeta(meta);
        return book;
    }

    public static ItemStack createDust(String type, int amount) {
        ItemStack dust = new ItemStack(Material.GLOWSTONE_DUST, amount);
        ItemMeta meta = dust.getItemMeta();
        meta.setDisplayName(ChatColor.YELLOW + type + " Dust");
        meta.setLore(Arrays.asList(
                "§7Used for enchanting",
                "§7or upgrading enchants"
        ));
        dust.setItemMeta(meta);
        return dust;
    }

    public static ItemStack createCrystal(String type) {
        ItemStack crystal = new ItemStack(Material.AMETHYST_SHARD);
        ItemMeta meta = crystal.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + type + " Crystal");
        meta.setLore(Arrays.asList(
                "§7Rare crystal used for",
                "§7high-tier enchanting"
        ));
        crystal.setItemMeta(meta);
        return crystal;
    }
}
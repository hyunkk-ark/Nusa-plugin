package com.github.cahyunkk.nusaenchant.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ForgeMenu extends Menu {

    public ForgeMenu(Player player) {
        super(player);
    }

    @Override
    public String getTitle() {
        return ChatColor.GOLD + "✦ Enchant Forge ✦";
    }

    @Override
    public int getSize() {
        return 36;
    }

    @Override
    public void setupItems() {
        setItem(10, Material.ANVIL, "§6Forge Enchant", "§7Combine materials to create", "§7powerful enchants");
        setItem(12, Material.ENCHANTED_BOOK, "§aUpgrade Level", "§7Increase enchant level");
        setItem(14, Material.NETHERITE_INGOT, "§cReroll Rarity", "§7Change enchant rarity");
        setItem(16, Material.LAVA_BUCKET, "§4Destroy Enchant", "§7Remove enchant from item");

        setItem(31, Material.ARROW, "§cBack", "§7Return to main menu");
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
        if (event.getSlot() == 31) {
            new MainMenu(player).open();
        }
    }
}
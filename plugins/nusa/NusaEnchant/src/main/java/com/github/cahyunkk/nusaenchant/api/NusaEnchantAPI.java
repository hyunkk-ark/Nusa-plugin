package com.github.cahyunkk.nusaenchant.api;

import com.github.cahyunkk.nusaenchant.enchant.Enchant;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface NusaEnchantAPI {

    boolean registerEnchant(Enchant enchant);
    
    Enchant getEnchant(String id);
    
    boolean applyEnchant(ItemStack item, String enchantId, int level);
    
    boolean hasEnchant(ItemStack item, String enchantId);
    
    int getEnchantLevel(ItemStack item, String enchantId);
    
    void triggerEnchant(Player player, String triggerType, Object... data);
}
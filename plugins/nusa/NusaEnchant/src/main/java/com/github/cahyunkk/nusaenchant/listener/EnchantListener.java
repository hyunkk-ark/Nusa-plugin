package com.github.cahyunkk.nusaenchant.listener;

import com.github.cahyunkk.nusaenchant.NusaEnchant;
import com.github.cahyunkk.nusaenchant.enchant.Enchant;
import com.github.cahyunkk.nusaenchant.effect.Effect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.NamespacedKey;

import java.util.List;

public class EnchantListener implements Listener {

    private final NusaEnchant plugin;

    public EnchantListener(NusaEnchant plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) return;

        ItemStack item = player.getInventory().getItemInMainHand();
        triggerEnchants(item, "ATTACK", player, event);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        triggerEnchants(item, "BREAK", player, event);
    }

    private void triggerEnchants(ItemStack item, String triggerType, Player player, org.bukkit.event.Event event) {
        if (item == null || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        // Cek semua enchant di item (simulasi dengan lore)
        if (meta.hasLore()) {
            List<String> lore = meta.getLore();
            for (String line : lore) {
                if (line.contains("§") && line.toLowerCase().contains("enchant")) {
                    // Contoh sederhana: jika ada kata "fire" di lore
                    if (line.toLowerCase().contains("fire") || line.toLowerCase().contains("blazing")) {
                        plugin.getEffectEngine().executeEffect("FIRE", player, event, 1);
                    }
                    if (line.toLowerCase().contains("lifesteal")) {
                        plugin.getEffectEngine().executeEffect("LIFESTEAL", player, event, 1);
                    }
                    if (line.toLowerCase().contains("vein")) {
                        plugin.getEffectEngine().executeEffect("VEIN_MINER", player, event, 1);
                    }
                }
            }
        }
    }
}
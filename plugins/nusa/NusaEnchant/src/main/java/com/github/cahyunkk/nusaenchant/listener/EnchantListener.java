package com.github.cahyunkk.nusaenchant.listener;

import com.github.cahyunkk.nusaenchant.NusaEnchant;
import com.github.cahyunkk.nusaenchant.enchant.Enchant;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class EnchantListener implements Listener {

    private final NusaEnchant plugin;

    public EnchantListener(NusaEnchant plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) return;

        ItemStack item = player.getInventory().getItemInMainHand();
        checkAndTrigger(item, "ATTACK", player, event);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        checkAndTrigger(item, "BREAK", player, event);
    }

    private void checkAndTrigger(ItemStack item, String trigger, Player player, org.bukkit.event.Event event) {
        // TODO: Parse item meta for enchantments
        // For now, this is a placeholder
        plugin.getTriggerEngine().fireTrigger(trigger, event);
    }
}
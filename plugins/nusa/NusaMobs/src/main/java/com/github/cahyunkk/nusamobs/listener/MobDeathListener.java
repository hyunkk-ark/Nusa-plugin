package com.github.cahyunkk.nusamobs.listener;

import com.github.cahyunkk.nusamobs.NusaMobs;
import com.github.cahyunkk.nusamobs.mob.CustomMob;
import com.github.cahyunkk.nusamobs.util.Chat;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class MobDeathListener implements Listener {
    private final NusaMobs plugin;
    public MobDeathListener(NusaMobs p) { this.plugin = p; }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        if (!(e.getEntity() instanceof LivingEntity le)) return;
        String id = plugin.getMobManager().getSpawnedId(le);
        if (id == null) return;
        CustomMob mob = plugin.getMobManager().getMob(id);
        if (mob == null) return;

        // Vanilla drops cleared, only custom
        e.getDrops().clear();
        for (var drop : mob.getDrops()) {
            if (Math.random() > drop.getChance()) continue;
            int amt = drop.getMin() + (int)(Math.random() * (drop.getMax() - drop.getMin() + 1));
            ItemStack item = plugin.getNusaBridge().getItem(drop.getItemId());
            if (item.getType() == org.bukkit.Material.AIR) continue;
            item.setAmount(Math.min(amt, 64));
            e.getDrops().add(item.clone());
        }

        // XP boost
        e.setDroppedExp(mob.getLevel() * 10);

        // Death message
        Player killer = e.getEntity().getKiller();
        if (killer != null) {
            killer.sendMessage(Chat.color("&e⚔ &7Killed &f" + mob.getName() + " &7[Lv." + mob.getLevel() + "]"));
        }

        plugin.getMobManager().unmark(le);
    }
}

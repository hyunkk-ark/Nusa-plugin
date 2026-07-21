package com.github.cahyunkk.nusamobs.listener;

import com.github.cahyunkk.nusamobs.NusaMobs;
import com.github.cahyunkk.nusamobs.mob.CustomMob;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;

public class MobCombatListener implements Listener {
    private final NusaMobs plugin;
    public MobCombatListener(NusaMobs p) { this.plugin = p; }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof LivingEntity le && plugin.getMobManager().isCustomMob(le)) {
            CustomMob mob = plugin.getMobManager().getMob(plugin.getMobManager().getSpawnedId(le));
            if (mob != null) e.setDamage(mob.getDamage() * (1 + mob.getLevel() * 0.3));
        }
    }

    @EventHandler
    public void onTarget(EntityTargetEvent e) {
        if (e.getEntity() instanceof LivingEntity le && plugin.getMobManager().isCustomMob(le)) {
            if (e.getTarget() == null) return;
            CustomMob mob = plugin.getMobManager().getMob(plugin.getMobManager().getSpawnedId(le));
            if (mob != null && le.getAttribute(Attribute.FOLLOW_RANGE) != null) {
                le.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(40 + mob.getLevel() * 5);
            }
        }
    }
}

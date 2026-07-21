package com.github.cahyunkk.nusaenchant.effect.impl;

import com.github.cahyunkk.nusaenchant.effect.Effect;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class FireEffect implements Effect {

    @Override
    public void execute(Player player, Event event, int level) {
        if (event instanceof EntityDamageByEntityEvent damageEvent) {
            if (damageEvent.getEntity() instanceof LivingEntity target) {
                int duration = level * 4; // 4 detik per level
                target.setFireTicks(duration * 20);
                player.sendMessage("§c[Enchant] Target terbakar selama " + duration + " detik!");
            }
        }
    }

    @Override
    public String getName() {
        return "FIRE";
    }
}
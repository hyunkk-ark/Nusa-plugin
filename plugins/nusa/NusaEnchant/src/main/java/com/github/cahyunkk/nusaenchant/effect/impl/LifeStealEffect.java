package com.github.cahyunkk.nusaenchant.effect.impl;

import com.github.cahyunkk.nusaenchant.effect.Effect;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class LifeStealEffect implements Effect {

    @Override
    public void execute(Player player, Event event, int level) {
        if (event instanceof EntityDamageByEntityEvent damageEvent) {
            double damage = damageEvent.getFinalDamage();
            double heal = damage * (0.15 * level); // 15% per level

            double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            double newHealth = Math.min(player.getHealth() + heal, maxHealth);
            player.setHealth(newHealth);

            player.sendMessage("§a[Enchant] Life Steal: +" + String.format("%.1f", heal) + " HP");
        }
    }

    @Override
    public String getName() {
        return "LIFESTEAL";
    }
}
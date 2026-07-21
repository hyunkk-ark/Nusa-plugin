package com.github.cahyunkk.nusaenchant.effect;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public interface Effect {
    void execute(Player player, Event event, int level);
    String getName();
}
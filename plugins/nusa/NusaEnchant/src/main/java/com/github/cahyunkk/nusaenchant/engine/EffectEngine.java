package com.github.cahyunkk.nusaenchant.engine;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class EffectEngine {

    private final Map<String, BiConsumer<Player, Event>> effects = new HashMap<>();

    public void registerEffect(String name, BiConsumer<Player, Event> effect) {
        effects.put(name.toUpperCase(), effect);
    }

    public void executeEffect(String name, Player player, Event event) {
        BiConsumer<Player, Event> effect = effects.get(name.toUpperCase());
        if (effect != null) {
            effect.accept(player, event);
        }
    }

    public boolean hasEffect(String name) {
        return effects.containsKey(name.toUpperCase());
    }
}
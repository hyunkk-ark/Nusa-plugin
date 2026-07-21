package com.github.cahyunkk.nusaenchant.engine;

import com.github.cahyunkk.nusaenchant.effect.Effect;
import com.github.cahyunkk.nusaenchant.effect.impl.FireEffect;
import com.github.cahyunkk.nusaenchant.effect.impl.LifeStealEffect;
import com.github.cahyunkk.nusaenchant.effect.impl.VeinMinerEffect;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.HashMap;
import java.util.Map;

public class EffectEngine {

    private final Map<String, Effect> effects = new HashMap<>();

    public EffectEngine() {
        registerEffect(new FireEffect());
        registerEffect(new LifeStealEffect());
        registerEffect(new VeinMinerEffect());
    }

    public void registerEffect(Effect effect) {
        effects.put(effect.getName().toUpperCase(), effect);
    }

    public void executeEffect(String name, Player player, Event event, int level) {
        Effect effect = effects.get(name.toUpperCase());
        if (effect != null) {
            effect.execute(player, event, level);
        }
    }

    public boolean hasEffect(String name) {
        return effects.containsKey(name.toUpperCase());
    }
}
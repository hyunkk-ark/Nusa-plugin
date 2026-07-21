package com.github.cahyunkk.nusaenchant.engine;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class TriggerEngine {

    private final Map<String, Consumer<Event>> triggers = new HashMap<>();

    public void registerTrigger(String type, Consumer<Event> action) {
        triggers.put(type.toUpperCase(), action);
    }

    public void fireTrigger(String type, Event event) {
        Consumer<Event> action = triggers.get(type.toUpperCase());
        if (action != null) {
            action.accept(event);
        }
    }
}
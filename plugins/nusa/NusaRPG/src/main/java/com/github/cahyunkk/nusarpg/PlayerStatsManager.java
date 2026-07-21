package com.github.cahyunkk.nusarpg;

import java.util.*;
import org.bukkit.entity.Player;

public class PlayerStatsManager {

    private final NusaRPG plugin;

    public PlayerStatsManager(NusaRPG plugin) {
        this.plugin = plugin;
    }

    public void setStat(UUID playerId, String stat, int value) {
        plugin.getConfig().set("players." + playerId + ".stats." + stat, value);
    }

    public int getStat(UUID playerId, String stat) {
        return plugin.getConfig().getInt("players." + playerId + ".stats." + stat, 0);
    }

    public void addExp(UUID playerId, int amount) {
        int current = plugin.getConfig().getInt("players." + playerId + ".exp", 0);
        plugin.getConfig().set("players." + playerId + ".exp", current + amount);
    }
}

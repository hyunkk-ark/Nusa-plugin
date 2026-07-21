package com.github.cahyunkk.nusaengine;

import java.util.*;

public class MobStatsManager {

    private final NusaEngine plugin;

    public MobStatsManager(NusaEngine plugin) {
        this.plugin = plugin;
    }

    public void registerMobStats(String mobId, double hp, double damage, double armor) {
        plugin.getConfig().set("mobs." + mobId + ".hp", hp);
        plugin.getConfig().set("mobs." + mobId + ".damage", damage);
        plugin.getConfig().set("mobs." + mobId + ".armor", armor);
    }

    public Map<String, Object> getStats(String mobId) {
        Map<String, Object> stats = new HashMap<>();
        stats.put("hp", plugin.getConfig().getDouble("mobs." + mobId + ".hp", 20));
        stats.put("damage", plugin.getConfig().getDouble("mobs." + mobId + ".damage", 5));
        stats.put("armor", plugin.getConfig().getDouble("mobs." + mobId + ".armor", 0));
        return stats;
    }
}

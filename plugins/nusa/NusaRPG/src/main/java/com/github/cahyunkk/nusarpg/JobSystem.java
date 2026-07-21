package com.github.cahyunkk.nusarpg;

import java.util.*;

public class JobSystem {

    private final NusaRPG plugin;

    public JobSystem(NusaRPG plugin) {
        this.plugin = plugin;
    }

    public void assignJob(UUID playerId, String jobName) {
        plugin.getConfig().set("players." + playerId + ".job", jobName);
    }

    public String getJob(UUID playerId) {
        return plugin.getConfig().getString("players." + playerId + ".job", "Adventurer");
    }

    public List<String> getAvailableJobs() {
        return Arrays.asList("Warrior", "Mage", "Archer", "Adventurer", "Blacksmith");
    }
}

package com.github.cahyunkk.nusaskills;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class SkillListener implements Listener {

    private final NusaSkills plugin;

    public SkillListener(NusaSkills plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Integration point: initialize skills for players when joining
        plugin.getSkillManager().registerSkill(event.getPlayer().getUniqueId(), "default", 1);
    }
}

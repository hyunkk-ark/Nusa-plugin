package com.github.cahyunkk.nusaskills;

import java.util.*;
import org.bukkit.entity.Player;

public class SkillManager {

    private final NusaSkills plugin;
    private final Map<UUID, SkillData> playerSkills = new HashMap<>();

    public SkillManager(NusaSkills plugin) {
        this.plugin = plugin;
    }

    public void registerSkill(UUID playerId, String skillName, int level) {
        playerSkills.computeIfAbsent(playerId, k -> new SkillData()).addSkill(skillName, level);
    }

    public int getSkillLevel(UUID playerId, String skillName) {
        SkillData data = playerSkills.get(playerId);
        return data == null ? 0 : data.getLevel(skillName);
    }

    public boolean hasSkill(UUID playerId, String skillName, int minLevel) {
        return getSkillLevel(playerId, skillName) >= minLevel;
    }

    public Set<String> getSkillNames(UUID playerId) {
        SkillData data = playerSkills.get(playerId);
        return data == null ? Collections.emptySet() : new HashSet<>(data.getSkills().keySet());
    }

    public static class SkillData {
        private final Map<String, Integer> skills = new HashMap<>();

        public void addSkill(String name, int level) {
            skills.put(name, Math.max(skills.getOrDefault(name, 0), level));
        }

        public int getLevel(String name) {
            return skills.getOrDefault(name, 0);
        }

        public Map<String, Integer> getSkills() {
            return skills;
        }
    }
}

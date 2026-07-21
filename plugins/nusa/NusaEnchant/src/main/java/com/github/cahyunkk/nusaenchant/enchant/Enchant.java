package com.github.cahyunkk.nusaenchant.enchant;

import java.util.List;

public class Enchant {

    private final String id;
    private final String displayName;
    private final List<String> description;
    private final String category;
    private final String rarity;
    private final int maxLevel;
    private final List<String> triggers;
    private final List<String> effects;

    public Enchant(String id, String displayName, List<String> description, String category, String rarity, int maxLevel, List<String> triggers, List<String> effects) {
        this.id = id;
        this.displayName = displayName;
        this.description = description;
        this.category = category;
        this.rarity = rarity;
        this.maxLevel = maxLevel;
        this.triggers = triggers;
        this.effects = effects;
    }

    public String getId() { return id; }
    public String getDisplayName() { return displayName; }
    public List<String> getDescription() { return description; }
    public String getCategory() { return category; }
    public String getRarity() { return rarity; }
    public int getMaxLevel() { return maxLevel; }
    public List<String> getTriggers() { return triggers; }
    public List<String> getEffects() { return effects; }
}
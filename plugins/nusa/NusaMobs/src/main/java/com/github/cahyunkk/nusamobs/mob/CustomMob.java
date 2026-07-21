package com.github.cahyunkk.nusamobs.mob;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import java.util.*;

public class CustomMob {
    private final String id, name;
    private final EntityType type;
    private final double health, damage, speed;
    private final int level;
    private final boolean baby, glowing, silent;
    private final String displayName;
    private final List<String> equipment;
    private final List<DropEntry> drops;
    private final List<String> skills;
    private final Map<String, Double> spawnSettings;

    public CustomMob(String id, String name, EntityType type, double health, double damage,
                     double speed, int level, boolean baby, boolean glowing, boolean silent,
                     String displayName, List<String> equipment, List<DropEntry> drops,
                     List<String> skills, Map<String, Double> spawnSettings) {
        this.id=id; this.name=name; this.type=type; this.health=health; this.damage=damage;
        this.speed=speed; this.level=level; this.baby=baby; this.glowing=glowing;
        this.silent=silent; this.displayName=displayName; this.equipment=equipment;
        this.drops=drops; this.skills=skills; this.spawnSettings=spawnSettings;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public EntityType getType() { return type; }
    public double getHealth() { return health; }
    public double getDamage() { return damage; }
    public double getSpeed() { return speed; }
    public int getLevel() { return level; }
    public boolean isBaby() { return baby; }
    public boolean isGlowing() { return glowing; }
    public boolean isSilent() { return silent; }
    public String getDisplayName() { return displayName; }
    public List<String> getEquipment() { return equipment; }
    public List<DropEntry> getDrops() { return drops; }
    public List<String> getSkills() { return skills; }
    public Map<String, Double> getSpawnSettings() { return spawnSettings; }

    public static class DropEntry {
        private final String itemId;   // "nusaitems:pedang_sakti" or "DIAMOND" etc
        private final int min, max;
        private final double chance;
        public DropEntry(String itemId, int min, int max, double chance) {
            this.itemId=itemId; this.min=min; this.max=max; this.chance=chance;
        }
        public String getItemId() { return itemId; }
        public int getMin() { return min; }
        public int getMax() { return max; }
        public double getChance() { return chance; }
    }
}

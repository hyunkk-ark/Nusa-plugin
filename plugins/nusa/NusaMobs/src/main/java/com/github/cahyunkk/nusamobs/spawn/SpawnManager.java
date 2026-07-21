package com.github.cahyunkk.nusamobs.spawn;

import com.github.cahyunkk.nusamobs.NusaMobs;
import com.github.cahyunkk.nusamobs.mob.CustomMob;
import com.github.cahyunkk.nusamobs.util.Chat;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class SpawnManager {
    private final NusaMobs plugin;
    private BukkitRunnable naturalSpawnTask;
    private boolean running;

    public SpawnManager(NusaMobs p) { this.plugin = p; }

    public void start() {
        running = true;
        naturalSpawnTask = new BukkitRunnable() {
            public void run() {
                if (!running) return;
                for (var w : Bukkit.getWorlds()) {
                    for (var p : w.getPlayers()) {
                        maybeSpawnNear(p);
                    }
                }
            }
        };
        naturalSpawnTask.runTaskTimer(plugin, 200L, 200L); // every 10s
    }

    public void stop() {
        running = false;
        if (naturalSpawnTask != null) naturalSpawnTask.cancel();
    }

    private void maybeSpawnNear(Player p) {
        for (CustomMob mob : plugin.getMobManager().getAll().values()) {
            var ss = mob.getSpawnSettings();
            if (ss.isEmpty()) continue;
            double chance = ss.getOrDefault("chance", 0.02);
            if (Math.random() > chance) continue;

            Location loc = p.getLocation().add(
                (Math.random() - 0.5) * 40, 0, (Math.random() - 0.5) * 40);
            loc = loc.getWorld().getHighestBlockAt(loc).getLocation().add(0, 1, 0);
            if (loc.getBlock().getLightLevel() > 7 && mob.getType().name().contains("ZOMBIE")) continue;
            spawnMob(mob, loc);
        }
    }

    public LivingEntity spawnMob(CustomMob mob, Location loc) {
        World w = loc.getWorld();
        LivingEntity entity = (LivingEntity) w.spawnEntity(loc, mob.getType());
        applyMob(entity, mob);
        return entity;
    }

    public void applyMob(LivingEntity e, CustomMob mob) {
        e.setCustomNameVisible(true);
        e.setCustomName(Chat.color(mob.getDisplayName()));
        e.getAttribute(Attribute.MAX_HEALTH).setBaseValue(mob.getHealth());
        e.setHealth(mob.getHealth());
        e.getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(mob.getSpeed());

        if (e instanceof Ageable a && mob.isBaby()) a.setBaby();
        if (e instanceof Creeper c && mob.getLevel() > 1) c.setPowered(true);
        e.setGlowing(mob.isGlowing());
        e.setSilent(mob.isSilent());

        if (mob.getLevel() > 1) {
            e.getAttribute(Attribute.MAX_HEALTH).setBaseValue(mob.getHealth() * (1 + mob.getLevel() * 0.5));
            e.setHealth(mob.getHealth() * (1 + mob.getLevel() * 0.5));
        }

        // Equipment
        EntityEquipment eq = e.getEquipment();
        if (eq != null && !mob.getEquipment().isEmpty()) {
            for (String equip : mob.getEquipment()) {
                String[] parts = equip.split(" ");
                if (parts.length < 2) continue;
                ItemStack item = plugin.getNusaBridge().getItem(parts[0]);
                if (item.getType() == Material.AIR) continue;
                String slot = parts[1].toLowerCase();
                switch (slot) {
                    case "hand","mainhand","0": eq.setItemInMainHand(item); break;
                    case "offhand","off","1": eq.setItemInOffHand(item); break;
                    case "head","helmet","4": eq.setHelmet(item); break;
                    case "chest","chestplate","3": eq.setChestplate(item); break;
                    case "legs","leggings","2": eq.setLeggings(item); break;
                    case "boots","feet","5": eq.setBoots(item); break;
                }
            }
        }

        // Skills
        for (String skill : mob.getSkills()) {
            applySkill(e, skill);
        }

        plugin.getMobManager().markSpawned(e, mob.getId());
    }

    private void applySkill(LivingEntity e, String skill) {
        String[] parts = skill.split(" ");
        String s = parts[0].toLowerCase();
        try {
            switch (s) {
                case "potion" -> {
                    if (parts.length >= 3)
                        e.addPotionEffect(new PotionEffect(
                            PotionEffectType.getByName(parts[1].toUpperCase()),
                            999999, Integer.parseInt(parts[2]) - 1));
                }
                case "speed" -> e.getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(Double.parseDouble(parts[1]));
                case "damage" -> e.getAttribute(Attribute.ATTACK_DAMAGE).setBaseValue(Double.parseDouble(parts[1]));
                case "armor" -> e.getAttribute(Attribute.ARMOR).setBaseValue(Double.parseDouble(parts[1]));
                case "knockback_resist" -> e.getAttribute(Attribute.KNOCKBACK_RESISTANCE).setBaseValue(Double.parseDouble(parts[1]));
                case "fire" -> e.setFireTicks(Integer.parseInt(parts[1]) * 20);
                case "invisible" -> e.setInvisible(true);
            }
        } catch (Exception ignored) {}
    }
}

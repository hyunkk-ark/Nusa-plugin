package com.github.cahyunkk.nusamobs.mob;

import com.github.cahyunkk.nusamobs.NusaMobs;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import java.util.*;

public class MobManager {
    private final NusaMobs plugin;
    private final Map<String, CustomMob> mobs = new LinkedHashMap<>();
    private final Map<java.util.UUID, String> activeMobs = new HashMap<>();

    public MobManager(NusaMobs p) { this.plugin = p; }

    public void loadAll() {
        mobs.clear();
        var cfg = plugin.getConfig();
        ConfigurationSection sec = cfg.getConfigurationSection("mobs");
        if (sec == null) {
            plugin.getLogger().warning("No 'mobs' section in config.yml!");
            return;
        }
        for (String key : sec.getKeys(false)) {
            ConfigurationSection s = sec.getConfigurationSection(key);
            if (s == null) continue;
            try {
                EntityType type = EntityType.valueOf(s.getString("type", "ZOMBIE").toUpperCase());
                List<CustomMob.DropEntry> drops = new ArrayList<>();
                for (String d : s.getStringList("drops")) {
                    String[] parts = d.split(" ");
                    if (parts.length >= 3) {
                        drops.add(new CustomMob.DropEntry(parts[0],
                                Integer.parseInt(parts[1]), Integer.parseInt(parts[2]),
                                parts.length >= 4 ? Double.parseDouble(parts[3]) : 1.0));
                    }
                }
                Map<String, Double> spawn = new LinkedHashMap<>();
                ConfigurationSection sp = s.getConfigurationSection("spawn");
                if (sp != null) for (String sk : sp.getKeys(false)) spawn.put(sk, sp.getDouble(sk));

                CustomMob mob = new CustomMob(
                    key, s.getString("name", key), type,
                    s.getDouble("health", 20), s.getDouble("damage", 2),
                    s.getDouble("speed", 0.25), s.getInt("level", 1),
                    s.getBoolean("baby", false), s.getBoolean("glowing", false),
                    s.getBoolean("silent", false),
                    s.getString("display-name", "&c" + key),
                    s.getStringList("equipment"), drops,
                    s.getStringList("skills"), spawn
                );
                mobs.put(key, mob);
                plugin.getLogger().info("  Loaded: " + key + " (" + type + ")");
            } catch (Exception e) {
                plugin.getLogger().severe("Failed mob '" + key + "': " + e.getMessage());
            }
        }
        plugin.getLogger().info("Loaded " + mobs.size() + " custom mobs");
    }

    public CustomMob getMob(String id) { return mobs.get(id); }
    public Map<String, CustomMob> getAll() { return mobs; }
    public int getMobCount() { return mobs.size(); }
    public Collection<String> getIds() { return mobs.keySet(); }

    public void markSpawned(LivingEntity e, String id) { activeMobs.put(e.getUniqueId(), id); }
    public String getSpawnedId(LivingEntity e) { return activeMobs.get(e.getUniqueId()); }
    public void unmark(LivingEntity e) { activeMobs.remove(e.getUniqueId()); }
    public boolean isCustomMob(LivingEntity e) { return activeMobs.containsKey(e.getUniqueId()); }

    public void clearAll() {
        for (java.util.UUID id : new ArrayList<>(activeMobs.keySet())) {
            var e = org.bukkit.Bukkit.getEntity(id);
            if (e != null) e.remove();
        }
        activeMobs.clear();
    }
}

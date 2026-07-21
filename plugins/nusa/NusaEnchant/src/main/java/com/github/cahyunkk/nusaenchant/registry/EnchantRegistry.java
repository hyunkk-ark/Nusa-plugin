package com.github.cahyunkk.nusaenchant.registry;

import com.github.cahyunkk.nusaenchant.enchant.Enchant;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class EnchantRegistry {

    private final Map<String, Enchant> enchants = new HashMap<>();
    private final JavaPlugin plugin;

    public EnchantRegistry(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void loadEnchants() {
        File enchantsFolder = new File(plugin.getDataFolder(), "enchants");
        if (!enchantsFolder.exists()) enchantsFolder.mkdirs();

        File[] files = enchantsFolder.listFiles((dir, name) -> name.endsWith(".yml"));
        if (files == null) return;

        for (File file : files) {
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            String id = config.getString("id");
            if (id == null) continue;

            Enchant enchant = new Enchant(
                    id,
                    config.getString("display-name", id),
                    config.getStringList("description"),
                    config.getString("category", "general"),
                    config.getString("rarity", "common"),
                    config.getInt("max-level", 1),
                    config.getStringList("triggers"),
                    config.getStringList("effects")
            );
            enchants.put(id.toLowerCase(), enchant);
            plugin.getLogger().info("Loaded enchant: " + id);
        }
    }

    public Enchant getEnchant(String id) {
        return enchants.get(id.toLowerCase());
    }

    public Map<String, Enchant> getAllEnchants() {
        return enchants;
    }
}
package com.github.cahyunkk.nusaitems;
import com.github.cahyunkk.nusaitems.model.CustomItem;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import java.io.File;
import java.util.*;

public class ConfigLoader {
    private final NusaItems plugin;
    private FileConfiguration config;
    private boolean autoGenerate, packServerEnabled, debug;
    private int packServerPort, packFormat;
    private String externalUrl, packDescription;
    private final Map<String, CustomItem> items = new LinkedHashMap<>();

    public ConfigLoader(NusaItems p) { this.plugin = p; }

    public void reload() {
        plugin.reloadConfig(); config = plugin.getConfig();
        new File(plugin.getDataFolder(), "textures").mkdirs();
        new File(plugin.getDataFolder(), "output").mkdirs();
        loadSettings(); loadItems();
    }

    private void loadSettings() {
        ConfigurationSection rp = config.getConfigurationSection("resource-pack");
        if (rp == null) rp = config.createSection("resource-pack");
        autoGenerate = rp.getBoolean("auto-generate", true);
        ConfigurationSection srv = rp.getConfigurationSection("built-in-server");
        if (srv == null) srv = rp.createSection("built-in-server");
        packServerEnabled = srv.getBoolean("enabled", true);
        packServerPort = srv.getInt("port", 8165);
        externalUrl = rp.getString("external-url", "");
        packDescription = rp.getString("description", "NusaItems Resources");
        packFormat = rp.getInt("pack-format", 34);
        debug = config.getBoolean("debug", false);
    }

    private void loadItems() {
        items.clear();
        ConfigurationSection sec = config.getConfigurationSection("items");
        if (sec == null) { plugin.getLogger().warning("No items section!"); return; }
        for (String key : sec.getKeys(false)) {
            ConfigurationSection s = sec.getConfigurationSection(key);
            if (s == null) continue;
            try {
                String id = s.getString("id", key);
                Material mat = Material.valueOf(s.getString("material", "STONE").toUpperCase());
                Map<Attribute, Double> attrs = new LinkedHashMap<>();
                ConfigurationSection as = s.getConfigurationSection("attributes");
                if (as != null) for (String ak : as.getKeys(false)) {
                    try { attrs.put(Attribute.valueOf(ak.toUpperCase()), as.getDouble(ak)); }
                    catch (IllegalArgumentException e) { plugin.getLogger().warning("Unknown attr: " + ak); }
                }
                items.put(id, new CustomItem(id, s.getString("name","&f"+id), mat,
                    s.getString("texture",id+".png"), s.getInt("custom-model-data",10000+new Random().nextInt(90000)),
                    s.getStringList("lore"), s.getBoolean("glow",false), s.getBoolean("unbreakable",false), attrs));
            } catch (Exception e) { plugin.getLogger().severe("Failed to load " + key + ": " + e.getMessage()); }
        }
        plugin.getLogger().info("Loaded " + items.size() + " custom items");
    }

    public Map<String, CustomItem> getItems() { return items; }
    public boolean isAutoGenerate() { return autoGenerate; }
    public boolean isPackServerEnabled() { return packServerEnabled; }
    public int getPackServerPort() { return packServerPort; }
    public String getExternalUrl() { return externalUrl; }
    public String getPackDescription() { return packDescription; }
    public int getPackFormat() { return packFormat; }
    public boolean isDebug() { return debug; }
    public String getPackUrl() {
        if (externalUrl != null && !externalUrl.isEmpty()) return externalUrl;
        String ip = plugin.getServer().getIp();
        if (ip == null || ip.isEmpty() || ip.equals("0.0.0.0")) ip = "127.0.0.1";
        return "http://" + ip + ":" + packServerPort + "/pack.zip";
    }
}

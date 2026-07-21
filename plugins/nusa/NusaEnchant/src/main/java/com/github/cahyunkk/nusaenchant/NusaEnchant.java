package com.github.cahyunkk.nusaenchant;

import com.github.cahyunkk.nusaenchant.engine.EffectEngine;
import com.github.cahyunkk.nusaenchant.engine.TriggerEngine;
import com.github.cahyunkk.nusaenchant.listener.EnchantListener;
import com.github.cahyunkk.nusaenchant.registry.EnchantRegistry;
import org.bukkit.plugin.java.JavaPlugin;

public class NusaEnchant extends JavaPlugin {

    private static NusaEnchant instance;
    private EnchantRegistry enchantRegistry;
    private TriggerEngine triggerEngine;
    private EffectEngine effectEngine;

    @Override
    public void onEnable() {
        instance = this;

        enchantRegistry = new EnchantRegistry(this);
        triggerEngine = new TriggerEngine();
        effectEngine = new EffectEngine();

        enchantRegistry.loadEnchants();

        // Register listeners
        getServer().getPluginManager().registerEvents(new EnchantListener(this), this);

        // Register command
        getCommand("nusaenchant").setExecutor(new com.github.cahyunkk.nusaenchant.command.NusaEnchantCommand(this));

        getLogger().info("NusaEnchant v" + getDescription().getVersion() + " enabled!");
        getLogger().info("Loaded " + enchantRegistry.getAllEnchants().size() + " enchants.");
    }

    @Override
    public void onDisable() {
        getLogger().info("NusaEnchant disabled.");
    }

    public static NusaEnchant getInstance() {
        return instance;
    }

    public EnchantRegistry getEnchantRegistry() {
        return enchantRegistry;
    }

    public TriggerEngine getTriggerEngine() {
        return triggerEngine;
    }

    public EffectEngine getEffectEngine() {
        return effectEngine;
    }
}
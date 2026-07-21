package com.github.cahyunkk.nusaenchant;

import org.bukkit.plugin.java.JavaPlugin;

public class NusaEnchant extends JavaPlugin {

    private static NusaEnchant instance;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("NusaEnchant v" + getDescription().getVersion() + " enabled!");
        
        // TODO: Initialize modules
        // EnchantRegistry, TriggerEngine, EffectEngine, etc.
    }

    @Override
    public void onDisable() {
        getLogger().info("NusaEnchant disabled.");
    }

    public static NusaEnchant getInstance() {
        return instance;
    }
}
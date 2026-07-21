package com.github.cahyunkk.nusaenchant.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class Menu implements InventoryHolder {

    protected Player player;
    protected Inventory inventory;

    public Menu(Player player) {
        this.player = player;
    }

    public abstract String getTitle();
    public abstract int getSize();
    public abstract void setupItems();

    public void open() {
        inventory = Bukkit.createInventory(this, getSize(), getTitle());
        setupItems();
        player.openInventory(inventory);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public abstract void handleClick(InventoryClickEvent event);
}
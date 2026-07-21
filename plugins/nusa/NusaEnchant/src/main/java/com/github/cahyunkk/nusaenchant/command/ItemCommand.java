package com.github.cahyunkk.nusaenchant.command;

import com.github.cahyunkk.nusaenchant.item.ItemManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ItemCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return true;

        if (args.length < 1) {
            player.sendMessage("§cUsage: /neitem <book|dust|crystal>");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "book":
                player.getInventory().addItem(ItemManager.createEnchantBook("fire_aspect", 1));
                player.sendMessage("§aGave you an Enchant Book!");
                break;
            case "dust":
                player.getInventory().addItem(ItemManager.createDust("Common", 16));
                player.sendMessage("§aGave you Common Dust!");
                break;
            case "crystal":
                player.getInventory().addItem(ItemManager.createCrystal("Legendary"));
                player.sendMessage("§aGave you a Legendary Crystal!");
                break;
            default:
                player.sendMessage("§cInvalid item type.");
        }
        return true;
    }
}
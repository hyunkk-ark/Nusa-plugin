package com.github.cahyunkk.nusaenchant.command;

import com.github.cahyunkk.nusaenchant.NusaEnchant;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NusaEnchantCommand implements CommandExecutor {

    private final NusaEnchant plugin;

    public NusaEnchantCommand(NusaEnchant plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage("§aNusaEnchant §7- Use §e/ne help");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "list":
                player.sendMessage("§aLoaded Enchants:");
                plugin.getEnchantRegistry().getAllEnchants().forEach((id, enchant) -> 
                    player.sendMessage("§7- " + enchant.getDisplayName() + " §8(" + id + ")"));
                break;
            case "help":
                player.sendMessage("§aNusaEnchant Commands:");
                player.sendMessage("§7/ne list §8- List all enchants");
                player.sendMessage("§7/ne menu §8- Open main menu");
                break;
            case "menu":
                new com.github.cahyunkk.nusaenchant.gui.MainMenu(player).open();
                break;
            default:
                player.sendMessage("§cUnknown command. Use §e/ne help");
        }
        return true;
    }
}
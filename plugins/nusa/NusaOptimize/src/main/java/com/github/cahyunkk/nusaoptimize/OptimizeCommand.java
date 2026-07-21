package com.github.cahyunkk.nusaoptimize;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class OptimizeCommand implements CommandExecutor {

    private final NusaOptimize plugin;

    public OptimizeCommand(NusaOptimize plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        sender.sendMessage("§6[NusaOptimize] §eAnti lag aktif. Config Java otomatis.");
        sender.sendMessage("§7Bedrock compatible — tanpa config manual Bedrock.");
        return true;
    }
}

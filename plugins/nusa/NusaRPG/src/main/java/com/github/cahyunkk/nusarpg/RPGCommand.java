package com.github.cahyunkk.nusarpg;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RPGCommand implements CommandExecutor {

    private final NusaRPG plugin;

    public RPGCommand(NusaRPG plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§6[NusaRPG] §e/nusa <stats|job|level>");
            return true;
        }

        if (sender instanceof Player p) {
            switch (args[0].toLowerCase()) {
                case "stats":
                    int lvl = plugin.getStatsManager().getStat(p.getUniqueId(), "level");
                    int exp = plugin.getConfig().getInt("players." + p.getUniqueId() + ".exp", 0);
                    sender.sendMessage("§6[NusaRPG] §eLevel: §f" + lvl + " §eEXP: §f" + exp);
                    break;
                case "job":
                    String job = plugin.getJobSystem().getJob(p.getUniqueId());
                    sender.sendMessage("§6[NusaRPG] §eJob: §f" + job);
                    break;
                default:
                    sender.sendMessage("§6[NusaRPG] §cGunakan: stats | job");
            }
        } else {
            sender.sendMessage("§6[NusaRPG] §cHanya pemain yang bisa menggunakan perintah ini.");
        }
        return true;
    }
}

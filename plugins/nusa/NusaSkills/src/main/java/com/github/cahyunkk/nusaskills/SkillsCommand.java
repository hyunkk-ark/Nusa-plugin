package com.github.cahyunkk.nusaskills;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SkillsCommand implements CommandExecutor {

    private final NusaSkills plugin;

    public SkillsCommand(NusaSkills plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§6[NusaSkills] §eCommands: /nusaskills list | info | reload");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload":
                plugin.reloadConfig();
                sender.sendMessage("§6[NusaSkills] §aConfig reloaded.");
                break;
            case "list":
                if (sender instanceof Player p) {
                    var skills = plugin.getSkillManager().getSkillNames(p.getUniqueId());
                    sender.sendMessage("§6[NusaSkills] §eYour skills: §f" + String.join(", ", skills));
                } else {
                    sender.sendMessage("§6[NusaSkills] §cOnly players can use list.");
                }
                break;
            case "info":
                sender.sendMessage("§6[NusaSkills] §fCompanion plugin for NusaItems & NusaMobs.");
                sender.sendMessage("§7Integration hooks available.");
                break;
            default:
                sender.sendMessage("§6[NusaSkills] §cUnknown subcommand. Use list/reload/info.");
        }
        return true;
    }
}

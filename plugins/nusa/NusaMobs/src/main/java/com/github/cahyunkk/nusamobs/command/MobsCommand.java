package com.github.cahyunkk.nusamobs.command;

import com.github.cahyunkk.nusamobs.NusaMobs;
import com.github.cahyunkk.nusamobs.mob.CustomMob;
import com.github.cahyunkk.nusamobs.util.Chat;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import java.util.*;
import java.util.stream.Collectors;

public class MobsCommand implements CommandExecutor, TabCompleter {
    private final NusaMobs plugin;
    public MobsCommand(NusaMobs p) { this.plugin = p; }

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (args.length == 0) { help(s); return true; }
        switch (args[0].toLowerCase()) {
            case "spawn" -> spawn(s, args);
            case "list" -> list(s);
            case "reload" -> { if (!perm(s)) return true; plugin.reload(); s.sendMessage(Chat.color("&a✅ Reloaded!"));
            }
            case "info" -> info(s);
            case "killall" -> { if (!perm(s)) return true; killAll(s); }
            case "giveegg" -> giveEgg(s, args);
            default -> help(s);
        }
        return true;
    }

    private void spawn(CommandSender s, String[] args) {
        if (args.length < 2) { s.sendMessage("§c/nusamobs spawn <mob> [amount] [world x y z]"); return; }
        CustomMob mob = plugin.getMobManager().getMob(args[1]);
        if (mob == null) { s.sendMessage("§cUnknown mob: " + args[1]); return; }
        int amt = 1;
        if (args.length >= 3) try { amt = Integer.parseInt(args[2]); } catch(Exception ex){}
        Location loc;
        if (args.length >= 6) {
            try {
                var w = Bukkit.getWorld(args[3]);
                if (w == null) { s.sendMessage("§cUnknown world!"); return; }
                loc = new Location(w, Double.parseDouble(args[4]), Double.parseDouble(args[5]), Double.parseDouble(args[6]));
            } catch(Exception e) { s.sendMessage("§cInvalid coords!"); return; }
        } else if (s instanceof Player p) {
            loc = p.getLocation();
        } else { s.sendMessage("§cConsole: specify world x y z!"); return; }

        for (int i = 0; i < Math.min(amt, 20); i++) {
            Location l = loc.clone().add((Math.random()-0.5)*2, 0, (Math.random()-0.5)*2);
            LivingEntity e = plugin.getSpawnManager().spawnMob(mob, l);
        }
        s.sendMessage(Chat.color("&a✅ Spawned &f" + amt + "x " + mob.getName()));
    }

    private void list(CommandSender s) {
        var mobs = plugin.getMobManager().getAll();
        if (mobs.isEmpty()) { s.sendMessage("§cNo custom mobs."); return; }
        s.sendMessage("§6═══ NusaMobs §7(" + mobs.size() + ") §6═══");
        for (CustomMob m : mobs.values()) {
            s.sendMessage(Component.text("  • ", NamedTextColor.DARK_GRAY)
                .append(Component.text(m.getId(), NamedTextColor.WHITE))
                .append(Component.text(" — ", NamedTextColor.GRAY))
                .append(Component.text(m.getName(), NamedTextColor.GOLD))
                .append(Component.text(" [Lv." + m.getLevel() + "]", NamedTextColor.RED))
                .clickEvent(ClickEvent.suggestCommand("/nusamobs spawn " + m.getId()))
                .hoverEvent(HoverEvent.showText(Component.text("Click to spawn"))));
        }
    }

    private void info(CommandSender s) {
        s.sendMessage("§6═══ NusaMobs Info §6═══");
        s.sendMessage("§7  Mobs loaded: §f" + plugin.getMobManager().getMobCount());
        s.sendMessage("§7  NusaItems: " + (plugin.getNusaBridge().isAvailable() ? "§a✅ Linked" : "§cNot found"));
        s.sendMessage("§7  Spawner: §f" + (plugin.getSpawnManager() != null ? "§aActive" : "§cInactive"));
    }

    private void killAll(CommandSender s) {
        int count = 0;
        for (var w : Bukkit.getWorlds()) {
            for (var e : w.getLivingEntities()) {
                if (plugin.getMobManager().isCustomMob(e)) { e.remove(); count++; }
            }
        }
        s.sendMessage(Chat.color("&a✅ Killed &f" + count + " &acustom mobs"));
    }

    private void giveEgg(CommandSender s, String[] args) {
        if (args.length < 2) { s.sendMessage("§c/nusamobs giveegg <mob> [player]"); return; }
        CustomMob mob = plugin.getMobManager().getMob(args[1]);
        if (mob == null) { s.sendMessage("§cUnknown mob!"); return; }
        Player target = args.length >= 3 ? Bukkit.getPlayer(args[2]) : (s instanceof Player p ? p : null);
        if (target == null) { s.sendMessage("§cPlayer not found!"); return; }

        var egg = new org.bukkit.inventory.ItemStack(org.bukkit.Material.valueOf(mob.getType().name() + "_SPAWN_EGG"));
        var meta = egg.getItemMeta();
        meta.setDisplayName(Chat.color("&e🐣 " + mob.getName() + " Egg"));
        meta.setLore(List.of(Chat.color("&7Spawn a " + mob.getName()), Chat.color("&7Level: &c" + mob.getLevel())));
        egg.setItemMeta(meta);
        target.getInventory().addItem(egg);
        s.sendMessage(Chat.color("&a✅ Gave egg to " + target.getName()));
    }

    private void help(CommandSender s) {
        s.sendMessage(Chat.color("&6═══ &eNusaMobs &6═══"));
        s.sendMessage(Chat.color("&f/nusamobs spawn <mob> [amt] &7— Spawn custom mobs"));
        s.sendMessage(Chat.color("&f/nusamobs list &7— List all mobs"));
        s.sendMessage(Chat.color("&f/nusamobs info &7— Plugin status"));
        s.sendMessage(Chat.color("&f/nusamobs reload &7— Reload config"));
        s.sendMessage(Chat.color("&f/nusamobs killall &7— Remove all custom mobs"));
        s.sendMessage(Chat.color("&f/nusamobs giveegg <mob> &7— Get spawn egg"));
    }

    private boolean perm(CommandSender s) {
        if (s.isOp() || s instanceof ConsoleCommandSender) return true;
        if (s.hasPermission("nusamobs.admin")) return true;
        s.sendMessage("§cNo permission."); return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender s, Command c, String l, String[] args) {
        if (args.length == 1) return List.of("spawn","list","reload","info","killall","giveegg").stream()
            .filter(x->x.startsWith(args[0].toLowerCase())).collect(Collectors.toList());
        if (args.length == 2 && (args[0].equalsIgnoreCase("spawn") || args[0].equalsIgnoreCase("giveegg")))
            return new ArrayList<>(plugin.getMobManager().getIds()).stream()
                .filter(x->x.startsWith(args[1].toLowerCase())).collect(Collectors.toList());
        if (args.length >= 3 && args[0].equalsIgnoreCase("spawn")) {
            if (args.length == 4) return Bukkit.getWorlds().stream().map(w->w.getName())
                .filter(x->x.toLowerCase().startsWith(args[3].toLowerCase())).collect(Collectors.toList());
        }
        return List.of();
    }
}

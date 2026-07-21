package com.github.cahyunkk.nusaitems.command;
import com.github.cahyunkk.nusaitems.NusaItems;
import com.github.cahyunkk.nusaitems.model.ChatUtil;
import com.github.cahyunkk.nusaitems.model.CustomItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import java.util.*;
import java.util.stream.Collectors;

public class NusaCommand implements CommandExecutor, TabCompleter {
    private final NusaItems plugin;
    public NusaCommand(NusaItems p) { this.plugin = p; }

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (args.length == 0) { sendHelp(s); return true; }
        switch (args[0].toLowerCase()) {
            case "reload" -> { if (!has(s,"reload")) return true; plugin.reloadAll(); s.sendMessage(ChatUtil.colorize("&a✅ Reloaded! &7("+plugin.getItemRegistry().getItemCount()+" items)")); }
            case "give" -> { if (!has(s,"give")) return true; give(s, args); }
            case "list" -> { if (!has(s,"admin")) return true; list(s); }
            case "pack" -> { if (!has(s,"admin")) return true; packInfo(s); }
            case "hooks" -> { if (!has(s,"admin")) return true; hooksInfo(s); }
            default -> sendHelp(s);
        }
        return true;
    }

    private void give(CommandSender s, String[] args) {
        if (args.length < 2) { s.sendMessage("§cUsage: /nusaitems give <id> [player] [amount]"); return; }
        String id = args[1]; Player target; int amt = 1;
        if (args.length >= 3) {
            Player p = Bukkit.getPlayer(args[2]);
            if (p != null && args.length >= 4) { target = p; try { amt = Integer.parseInt(args[3]); } catch(Exception ex){} }
            else if (p != null) target = p;
            else { if (s instanceof Player p2) target = p2; else { s.sendMessage("§cSpecify a player!"); return; } try { amt = Integer.parseInt(args[2]); } catch(Exception ex){} }
        } else { if (s instanceof Player p) target = p; else { s.sendMessage("§cSpecify a player!"); return; } }
        var item = plugin.getItemRegistry().buildItem(id);
        if (item == null) { s.sendMessage(ChatUtil.colorize("&cUnknown: &e"+id)); return; }
        item.setAmount(Math.min(amt, 64));
        target.getInventory().addItem(item);
        s.sendMessage(ChatUtil.colorize("&a✅ Gave &f"+amt+"x "+plugin.getItemRegistry().getItem(id).getName()+" &ato &e"+target.getName()));
    }

    private void list(CommandSender s) {
        var items = plugin.getItemRegistry().getRegisteredItems();
        if (items.isEmpty()) { s.sendMessage("§cNo items registered."); return; }
        s.sendMessage("§6═══ NusaItems §7("+items.size()+") §6═══");
        for (CustomItem ci : items.values()) {
            s.sendMessage(Component.text("  • ", NamedTextColor.DARK_GRAY)
                .append(Component.text(ci.getId(), NamedTextColor.WHITE))
                .append(Component.text(" → ", NamedTextColor.GRAY))
                .append(Component.text(ci.getName(), NamedTextColor.GOLD))
                .clickEvent(ClickEvent.suggestCommand("/nusaitems give "+ci.getId()))
                .hoverEvent(HoverEvent.showText(Component.text("Click to give"))));
        }
    }

    private void packInfo(CommandSender s) {
        plugin.getPackBuilder().generate();
        var cfg = plugin.getConfigLoader();
        s.sendMessage("§a📦 Resource Pack:");
        s.sendMessage("§7  URL: §b" + cfg.getPackUrl());
        s.sendMessage("§7  Server: " + (plugin.getPackServer().isRunning()?"§aONLINE":"§cOFFLINE"));
        s.sendMessage("§7  File: output/pack.zip");
    }

    private void hooksInfo(CommandSender s) {
        var hm = plugin.getHookManager();
        s.sendMessage("§6═══ NusaItems Integrations §6═══");
        s.sendMessage("§7  MythicMobs: " + (hm.isMythicMobsEnabled() ? "§a✅ Active" : "§cNot installed"));
        s.sendMessage("§7  MMOItems: " + (Bukkit.getPluginManager().isPluginEnabled("MMOItems") ? "§a✅ Compatible" : "§7—"));
        s.sendMessage("§7  ItemsAdder: " + (Bukkit.getPluginManager().isPluginEnabled("ItemsAdder") ? "§a✅ Compatible" : "§7—"));
        s.sendMessage("§7  Oraxen: " + (Bukkit.getPluginManager().isPluginEnabled("Oraxen") ? "§a✅ Compatible" : "§7—"));
        s.sendMessage("§7  EcoItems: " + (Bukkit.getPluginManager().isPluginEnabled("EcoItems") ? "§a✅ Compatible" : "§7—"));
        s.sendMessage("§7  ExecutableItems: " + (Bukkit.getPluginManager().isPluginEnabled("ExecutableItems") ? "§a✅ Compatible" : "§7—"));
        s.sendMessage("§7  NusaMobs: " + (Bukkit.getPluginManager().isPluginEnabled("NusaMobs") ? "§a✅ Linked" : "§7—"));
        s.sendMessage("");
        s.sendMessage(ChatUtil.colorize("&7MythicMobs: use &f'nusaitems:id' &7in drops/equipment"));
        s.sendMessage(ChatUtil.colorize("&7API: &fNusaItemsAPI.getInstance().getItem(\"id\")"));
    }

    private void sendHelp(CommandSender s) {
        s.sendMessage(ChatUtil.colorize("&6═══ &eNusaItems &6═══"));
        s.sendMessage(ChatUtil.colorize("&f/nusaitems give <id> [player] [amount] &7— Give items"));
        s.sendMessage(ChatUtil.colorize("&f/nusaitems list &7— List all items"));
        s.sendMessage(ChatUtil.colorize("&f/nusaitems hooks &7— Plugin integrations"));
        s.sendMessage(ChatUtil.colorize("&f/nusaitems pack &7— Resource pack info"));
        s.sendMessage(ChatUtil.colorize("&f/nusaitems reload &7— Reload & regenerate"));
    }

    private boolean has(CommandSender s, String perm) {
        if (s.isOp() || s instanceof ConsoleCommandSender) return true;
        if (s.hasPermission("nusaitems."+perm)) return true;
        s.sendMessage("§cNo permission."); return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender s, Command c, String l, String[] args) {
        if (args.length == 1) return List.of("reload","give","list","pack","hooks").stream().filter(x->x.startsWith(args[0].toLowerCase())).collect(Collectors.toList());
        if (args.length == 2 && args[0].equalsIgnoreCase("give")) return new ArrayList<>(plugin.getItemRegistry().getItemIds()).stream().filter(x->x.startsWith(args[1].toLowerCase())).collect(Collectors.toList());
        if (args.length == 3 && args[0].equalsIgnoreCase("give")) return null;
        return List.of();
    }
}

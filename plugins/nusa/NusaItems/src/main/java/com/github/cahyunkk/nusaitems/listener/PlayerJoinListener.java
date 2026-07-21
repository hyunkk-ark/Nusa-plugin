package com.github.cahyunkk.nusaitems.listener;
import com.github.cahyunkk.nusaitems.NusaItems;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import java.security.MessageDigest;

public class PlayerJoinListener implements Listener {
    private final NusaItems plugin;
    public PlayerJoinListener(NusaItems p) { this.plugin = p; }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        var cfg = plugin.getConfigLoader();
        if (!cfg.isPackServerEnabled() && cfg.getExternalUrl().isEmpty()) return;
        var player = e.getPlayer();
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            if (player.isOnline()) {
                player.setResourcePack(cfg.getPackUrl(), sha1(), cfg.getPackDescription(), false);
            }
        }, 40L);
    }

    private byte[] sha1() {
        try {
            var path = plugin.getPackBuilder().getOutputPath();
            if (!java.nio.file.Files.exists(path)) return new byte[20];
            return MessageDigest.getInstance("SHA-1").digest(java.nio.file.Files.readAllBytes(path));
        } catch (Exception e) { return new byte[20]; }
    }
}

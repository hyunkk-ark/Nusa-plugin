package com.github.cahyunkk.nusaauth;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class AuthListener implements Listener {

    private final NusaAuth plugin;

    public AuthListener(NusaAuth plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        var player = event.getPlayer();
        var uuid = player.getUniqueId();
        var auth = plugin.getAuthManager();

        // Anti-bot check
        if (!auth.checkAntiBot(uuid)) {
            player.sendMessage("§6[NusaAuth] §cBot terdeteksi! Login diblokir.");
            return;
        }

        boolean requireLogin = plugin.getConfig().getBoolean("auth.require-login", true);

        if (!auth.isRegistered(uuid)) {
            player.sendMessage("§6[NusaAuth] §eSelamat datang! Gunakan /nusaauth register <password> untuk mendaftar.");
            if (requireLogin) {
                player.sendMessage("§cLogin wajib aktif. Silakan register dan login.");
            }
            return;
        }

        if (!auth.isLoggedIn(uuid)) {
            player.sendMessage("§6[NusaAuth] §eAkun terdaftar. Gunakan /nusaauth login <password> untuk masuk.");
            if (requireLogin) {
                player.sendMessage("§cSilakan login untuk melanjutkan.");
            }
        } else {
            player.sendMessage("§6[NusaAuth] §eAutentikasi berhasil. Selamat datang!");
        }
    }
}

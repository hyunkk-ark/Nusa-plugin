package com.github.cahyunkk.nusaauth;

import java.util.*;

public class AuthManager {

    private final NusaAuth plugin;
    private final Set<UUID> loggedIn = new HashSet<>();
    private final Map<UUID, String> passwords = new HashMap<>();

    public AuthManager(NusaAuth plugin) {
        this.plugin = plugin;
    }

    public boolean register(UUID playerId, String password) {
        if (password == null || password.length() < 4) return false;
        passwords.put(playerId, password);
        plugin.getConfig().set("players." + playerId + ".password", password);
        plugin.getConfig().set("players." + playerId + ".registered", true);
        return true;
    }

    public boolean login(UUID playerId, String password) {
        String saved = passwords.getOrDefault(playerId, plugin.getConfig().getString("players." + playerId + ".password", null));
        if (saved == null || !saved.equals(password)) return false;
        loggedIn.add(playerId);
        plugin.getConfig().set("sessions." + playerId + ".login", System.currentTimeMillis());
        return true;
    }

    public boolean isRegistered(UUID playerId) {
        return passwords.containsKey(playerId) || plugin.getConfig().getBoolean("players." + playerId + ".registered", false);
    }

    public boolean isLoggedIn(UUID playerId) {
        return loggedIn.contains(playerId);
    }

    public void logout(UUID playerId) {
        loggedIn.remove(playerId);
    }

    public boolean checkAntiBot(UUID playerId) {
        String name = plugin.getServer().getPlayer(playerId) != null ? plugin.getServer().getPlayer(playerId).getName() : "unknown";
        boolean antiBot = plugin.getConfig().getBoolean("anti-bot.enabled", false);
        if (antiBot && (name.contains("_bot") || name.contains("bot") || name.length() < 3)) {
            return false;
        }
        return true;
    }
}

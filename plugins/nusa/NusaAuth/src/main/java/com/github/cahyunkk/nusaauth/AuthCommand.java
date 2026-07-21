package com.github.cahyunkk.nusaauth;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AuthCommand implements CommandExecutor {

    private final NusaAuth plugin;

    public AuthCommand(NusaAuth plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§6[NusaAuth] §cHanya pemain yang bisa menggunakan perintah ini.");
            return true;
        }

        var auth = plugin.getAuthManager();
        var uuid = p.getUniqueId();

        if (args.length == 0) {
            boolean logged = auth.isLoggedIn(uuid);
            boolean registered = auth.isRegistered(uuid);
            sender.sendMessage("§6[NusaAuth] §eStatus: " + (logged ? "Login Aktif" : "Belum Login"));
            sender.sendMessage("§6[NusaAuth] §eTerdaftar: " + (registered ? "Ya" : "Tidak"));
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "register":
                if (args.length < 2) {
                    sender.sendMessage("§6[NusaAuth] §cGunakan: /nusaauth register <password>");
                    return true;
                }
                String pw = args[1];
                if (auth.isRegistered(uuid)) {
                    sender.sendMessage("§6[NusaAuth] §cAkun sudah terdaftar.");
                    return true;
                }
                if (auth.register(uuid, pw)) {
                    sender.sendMessage("§6[NusaAuth] §aRegister berhasil! Gunakan /nusaauth login <password>.");
                } else {
                    sender.sendMessage("§6[NusaAuth] §cPassword terlalu pendek (minimal 4 karakter).");
                }
                break;

            case "login":
                if (args.length < 2) {
                    sender.sendMessage("§6[NusaAuth] §cGunakan: /nusaauth login <password>");
                    return true;
                }
                String loginPw = args[1];
                if (!auth.isRegistered(uuid)) {
                    sender.sendMessage("§6[NusaAuth] §cAkun belum terdaftar. Gunakan /nusaauth register <password>.");
                    return true;
                }
                if (auth.login(uuid, loginPw)) {
                    sender.sendMessage("§6[NusaAuth] §aLogin berhasil! Selamat bermain.");
                } else {
                    sender.sendMessage("§6[NusaAuth] §cPassword salah.");
                }
                break;

            case "logout":
                auth.logout(uuid);
                sender.sendMessage("§6[NusaAuth] §eLogout berhasil.");
                break;

            default:
                sender.sendMessage("§6[NusaAuth] §cPerintah: register | login | logout | (tanpa argumen untuk cek status)");
        }
        return true;
    }
}

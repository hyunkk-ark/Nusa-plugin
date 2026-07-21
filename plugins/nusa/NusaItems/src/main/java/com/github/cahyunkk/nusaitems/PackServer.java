package com.github.cahyunkk.nusaitems;
import com.sun.net.httpserver.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.*;

public class PackServer {
    private final NusaItems plugin;
    private HttpServer server;
    private boolean running;

    public PackServer(NusaItems p) { this.plugin = p; }
    public void start() {
        int port = plugin.getConfigLoader().getPackServerPort();
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/pack.zip", ex -> {
                Path pp = plugin.getPackBuilder().getOutputPath();
                if (!Files.exists(pp)) {
                    String msg = "Not generated yet. Run /nusaitems reload";
                    ex.sendResponseHeaders(404, msg.length());
                    try (OutputStream os = ex.getResponseBody()) { os.write(msg.getBytes()); }
                    return;
                }
                byte[] data = Files.readAllBytes(pp);
                ex.getResponseHeaders().set("Content-Type", "application/zip");
                ex.sendResponseHeaders(200, data.length);
                try (OutputStream os = ex.getResponseBody()) { os.write(data); }
            });
            server.createContext("/", ex -> {
                String html = "<html><body style='font-family:sans-serif;text-align:center;padding:50px;'><h1>📦 NusaItems</h1><p>Pack server online ✅</p><a href='/pack.zip'>Download pack.zip</a></body></html>";
                ex.getResponseHeaders().set("Content-Type", "text/html");
                ex.sendResponseHeaders(200, html.length());
                try (OutputStream os = ex.getResponseBody()) { os.write(html.getBytes()); }
            });
            server.setExecutor(null); server.start(); running = true;
            plugin.getLogger().info("§aPack server on port " + port);
        } catch (IOException e) {
            plugin.getLogger().severe("§cFailed to start pack server: " + e.getMessage());
        }
    }
    public void stop() { if (server != null) { server.stop(0); running = false; } }
    public boolean isRunning() { return running; }
}

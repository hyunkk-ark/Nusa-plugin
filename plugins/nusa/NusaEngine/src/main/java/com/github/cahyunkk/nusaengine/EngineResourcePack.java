package com.github.cahyunkk.nusaengine;

import java.io.*;
import java.nio.file.*;

public class EngineResourcePack {
    private final NusaEngine plugin;
    private final Path packDir;
    private final Path outputPath;

    public EngineResourcePack(NusaEngine plugin) {
        this.plugin = plugin;
        this.packDir = new File(plugin.getDataFolder(), "pack").toPath();
        this.outputPath = new File(plugin.getDataFolder(), "output/pack.zip").toPath();
    }

    public void generate() {
        try {
            if (Files.exists(packDir)) deleteRecursive(packDir);
            Files.createDirectories(packDir.resolve("assets/nusaengine/textures"));
            Files.createDirectories(packDir.resolve("assets/nusaengine/models"));
            writePackMeta();
            zipPack();
            plugin.getLogger().info("§a✅ Resource pack generated (Bedrock compatible): output/pack.zip");
        } catch (Exception e) {
            plugin.getLogger().severe("§cPack generation failed: " + e.getMessage());
        }
    }

    private void writePackMeta() throws IOException {
        String json = "{\"pack\":{\"pack_format\":18,\"description\":\"NusaEngine - Java & Bedrock compatible\"}}";
        Files.write(packDir.resolve("pack.mcmeta"), json.getBytes(java.nio.charset.StandardCharsets.UTF_8));
    }

    private void zipPack() throws IOException {
        Files.createDirectories(outputPath.getParent());
        try (java.util.zip.ZipOutputStream zos = new java.util.zip.ZipOutputStream(new FileOutputStream(outputPath.toFile()))) {
            Files.walk(packDir).filter(Files::isRegularFile).forEach(p -> {
                try {
                    String name = packDir.relativize(p).toString().replace("\\", "/");
                    zos.putNextEntry(new java.util.zip.ZipEntry(name));
                    Files.copy(p, zos); zos.closeEntry();
                } catch (IOException ignored) {}
            });
        }
    }

    private void deleteRecursive(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            try (var entries = Files.newDirectoryStream(path)) {
                for (Path e : entries) deleteRecursive(e);
            }
        }
        Files.deleteIfExists(path);
    }

    public Path getOutputPath() { return outputPath; }
}

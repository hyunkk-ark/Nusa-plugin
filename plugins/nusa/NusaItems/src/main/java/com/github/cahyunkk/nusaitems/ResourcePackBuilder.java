package com.github.cahyunkk.nusaitems;
import com.github.cahyunkk.nusaitems.model.CustomItem;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.zip.*;

public class ResourcePackBuilder {
    private final NusaItems plugin;
    private final Path packDir, outputPath;

    public ResourcePackBuilder(NusaItems p) {
        this.plugin = p;
        this.packDir = new File(p.getDataFolder(), "pack").toPath();
        this.outputPath = new File(p.getDataFolder(), "output/pack.zip").toPath();
    }

    public void generate() {
        if (!plugin.getConfigLoader().isAutoGenerate()) return;
        try {
            if (Files.exists(packDir)) deleteRecursive(packDir);
            Files.createDirectories(packDir.resolve("assets/minecraft/textures/nusaitems"));
            Files.createDirectories(packDir.resolve("assets/minecraft/models/nusaitems"));
            Files.createDirectories(packDir.resolve("assets/minecraft/models/item"));

            writePackMeta(); copyTextures(); generateModels(); generateItemOverrides(); zipPack();
            plugin.getLogger().info("§a✅ Resource pack generated: output/pack.zip");
        } catch (Exception e) {
            plugin.getLogger().severe("§cPack generation failed: " + e.getMessage());
        }
    }

    private void writePackMeta() throws IOException {
        String json = String.format("{\"pack\":{\"pack_format\":%d,\"description\":\"%s\"}}",
                plugin.getConfigLoader().getPackFormat(), plugin.getConfigLoader().getPackDescription());
        Files.write(packDir.resolve("pack.mcmeta"), json.getBytes(StandardCharsets.UTF_8));
    }

    private void copyTextures() throws IOException {
        Path src = new File(plugin.getDataFolder(), "textures").toPath();
        Path dst = packDir.resolve("assets/minecraft/textures/nusaitems");
        for (CustomItem item : plugin.getConfigLoader().getItems().values()) {
            Path s = src.resolve(item.getTexture()), d = dst.resolve(item.getTexture());
            if (Files.exists(s)) { Files.copy(s, d, StandardCopyOption.REPLACE_EXISTING); }
            else { createPlaceholder(d); }
        }
    }

    private void createPlaceholder(Path path) throws IOException {
        BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(100, 150, 255)); g.fillRect(0, 0, 16, 16);
        g.setColor(new Color(0, 0, 0, 60)); g.drawRect(0, 0, 15, 15);
        g.dispose();
        Files.createDirectories(path.getParent());
        ImageIO.write(img, "PNG", path.toFile());
    }

    private void generateModels() throws IOException {
        for (CustomItem item : plugin.getConfigLoader().getItems().values()) {
            String json = String.format("{\"parent\":\"minecraft:item/handheld\",\"textures\":{\"layer0\":\"minecraft:nusaitems/%s\"}}",
                    item.getTexture().replace(".png", ""));
            Path p = packDir.resolve("assets/minecraft/models/nusaitems/" + item.getId() + ".json");
            Files.createDirectories(p.getParent());
            Files.write(p, json.getBytes(StandardCharsets.UTF_8));
        }
    }

    private void generateItemOverrides() throws IOException {
        java.util.Map<String, java.util.List<CustomItem>> groups = new java.util.LinkedHashMap<>();
        for (CustomItem item : plugin.getConfigLoader().getItems().values())
            groups.computeIfAbsent(item.getMaterial().name().toLowerCase(), k -> new java.util.ArrayList<>()).add(item);

        for (java.util.Map.Entry<String, java.util.List<CustomItem>> e : groups.entrySet()) {
            StringBuilder overrides = new StringBuilder();
            for (CustomItem item : e.getValue()) {
                if (overrides.length() > 0) overrides.append(",\n        ");
                overrides.append(String.format("{\"predicate\":{\"custom_model_data\":%d},\"model\":\"nusaitems/%s\"}",
                        item.getCustomModelData(), item.getId()));
            }
            String json = String.format("{\"parent\":\"minecraft:item/generated\",\"textures\":{\"layer0\":\"minecraft:item/%s\"},\"overrides\":[\n        %s\n    ]}",
                    e.getKey(), overrides);
            Path p = packDir.resolve("assets/minecraft/models/item/" + e.getKey() + ".json");
            Files.createDirectories(p.getParent());
            Files.write(p, json.getBytes(StandardCharsets.UTF_8));
        }
    }

    private void zipPack() throws IOException {
        Files.createDirectories(outputPath.getParent());
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(outputPath.toFile()))) {
            Files.walk(packDir).filter(Files::isRegularFile).forEach(p -> {
                try {
                    String name = packDir.relativize(p).toString().replace("\\", "/");
                    zos.putNextEntry(new ZipEntry(name));
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

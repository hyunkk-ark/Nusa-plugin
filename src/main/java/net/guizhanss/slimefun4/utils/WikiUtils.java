package net.guizhanss.slimefun4.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.utils.JsonUtils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.stream.Collectors;
import org.bukkit.plugin.Plugin;

/**
 * Provide practical methods related to Wiki
 *
 * @author ybw0014
 */
public final class WikiUtils {
    private WikiUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Read the attached wiki.json and set the item's wiki button
     *
     *@paramaddon attached to{@link SlimefunAddon}instance
     */
    public static void setupJson(Plugin addon) {
        setupJson(addon, page -> page);
    }

    /**
     * Read the attached wiki.json and set the item's wiki button
     * The page address can be changed
     *
     *@paramplugin attached to{@link SlimefunAddon}instance
     *@paramformatter changes the page address
     */
    public static void setupJson(Plugin plugin, Function<String, String> formatter) {
        if (!(plugin instanceof SlimefunAddon)) {
            throw new IllegalArgumentException("This plugin is not a Slimefun addon.");
        }
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(plugin.getClass().getResourceAsStream("/wiki.json"), StandardCharsets.UTF_8))) {
            JsonElement element = JsonUtils.parseString(reader.lines().collect(Collectors.joining("")));
            JsonObject json = element.getAsJsonObject();

            int count = 0;

            for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
                SlimefunItem item = SlimefunItem.getById(entry.getKey());

                if (item != null) {
                    String page = entry.getValue().getAsString();
                    page = formatter.apply(page);
                    item.addWikiPage(page);
                    count++;
                }
            }

            plugin.getLogger()
                    .log(
                            Level.INFO,
                            MessageFormat.format("Loaded wiki page for{1}items in{0}", plugin.getName(), count));
        } catch (Exception e) {
            plugin.getLogger()
                    .log(Level.SEVERE, MessageFormat.format("Unable to load wiki.json for{0}", plugin.getName()), e);
        }
    }
}

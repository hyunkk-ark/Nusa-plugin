package com.github.cahyunkk.nusaitems.model;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public final class ChatUtil {
    private static final LegacyComponentSerializer SERIALIZER = LegacyComponentSerializer.legacyAmpersand();
    private ChatUtil() {}
    public static String colorize(String text) {
        if (text == null) return "";
        return LegacyComponentSerializer.legacySection().serialize(SERIALIZER.deserialize(text));
    }
    public static String decolorize(String text) {
        if (text == null) return "";
        return text.replaceAll("&[0-9a-fk-orA-FK-OR]", "").replaceAll("§[0-9a-fk-orA-FK-OR]", "");
    }
}

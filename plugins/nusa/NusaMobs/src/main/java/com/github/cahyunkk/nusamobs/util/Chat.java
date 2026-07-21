package com.github.cahyunkk.nusamobs.util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public final class Chat {
    private static final LegacyComponentSerializer AMP = LegacyComponentSerializer.legacyAmpersand();
    private static final LegacyComponentSerializer SEC = LegacyComponentSerializer.legacySection();
    private Chat() {}
    public static String color(String s) { return s==null?"":SEC.serialize(AMP.deserialize(s)); }
    public static Component comp(String s) { return AMP.deserialize(s); }
}

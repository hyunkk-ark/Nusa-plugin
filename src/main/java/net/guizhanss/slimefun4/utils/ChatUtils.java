package net.guizhanss.slimefun4.utils;

import io.github.bakedlibs.dough.common.ChatColors;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import org.bukkit.command.CommandSender;

/**
 * Chat related methods
 * @author ybw0014
 */
public class ChatUtils {
    private ChatUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Send messages prefixed with Slimefun
     *@paramsender message receiver
     *@parammessage message
     */
    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(Slimefun.getLocalization().getChatPrefix() + ChatColors.color(message));
    }

    /**
     * Send messages prefixed with Slimefun
     *@paramsender message receiver
     *@parammessage message
     *@paramfunction{@link Function}that processes messages
     */
    public static void sendMessage(CommandSender sender, String message, UnaryOperator<String> function) {
        sendMessage(sender, function.apply(message));
    }
}

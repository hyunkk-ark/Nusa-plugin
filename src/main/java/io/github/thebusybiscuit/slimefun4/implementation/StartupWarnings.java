package io.github.thebusybiscuit.slimefun4.implementation;

import io.github.thebusybiscuit.slimefun4.utils.NumberUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ParametersAreNonnullByDefault;

/** Stores startup warnings for unsupported server configurations. */
final class StartupWarnings {

    private static final String BORDER = "****************************************************";
    private static final String PREFIX = "* ";

    private StartupWarnings() {}

    @ParametersAreNonnullByDefault
    static void discourageCSCoreLib(Logger logger) {
        logger.log(Level.SEVERE, BORDER);
        logger.log(Level.SEVERE, PREFIX + "It looks like you have CS-CoreLib installed.");
        logger.log(Level.SEVERE, PREFIX);
        logger.log(Level.SEVERE, PREFIX + "Slimefun has not required CS-CoreLib since January 30, 2021.");
        logger.log(Level.SEVERE, PREFIX + "Please uninstall CS-CoreLib before starting Slimefun.");
        logger.log(Level.SEVERE, BORDER);
    }

    @ParametersAreNonnullByDefault
    static void invalidMinecraftVersion(Logger logger, String detectedVer, String slimefunVersion) {
        logger.log(Level.SEVERE, BORDER);
        logger.log(Level.SEVERE, PREFIX + "Slimefun failed to load!");
        logger.log(Level.SEVERE, PREFIX + "You are using an unsupported Minecraft version.");
        logger.log(Level.SEVERE, PREFIX);
        logger.log(Level.SEVERE, PREFIX + "Detected Minecraft version: {0}", detectedVer);
        logger.log(Level.SEVERE, PREFIX + "Slimefun {0} supports:", slimefunVersion);
        logger.log(Level.SEVERE, PREFIX + "Minecraft {0}", String.join(" / ", Slimefun.getSupportedVersions()));
        logger.log(Level.SEVERE, BORDER);
    }

    @ParametersAreNonnullByDefault
    static void invalidServerSoftware(Logger logger) {
        logger.log(Level.SEVERE, BORDER);
        logger.log(Level.SEVERE, PREFIX + "Slimefun failed to load!");
        logger.log(Level.SEVERE, PREFIX + "CraftBukkit and hybrid mod/plugin servers are not supported.");
        logger.log(Level.SEVERE, PREFIX);
        logger.log(Level.SEVERE, PREFIX + "Please use Paper or a compatible Paper fork.");
        logger.log(Level.SEVERE, PREFIX + "Paper is recommended: https://papermc.io/downloads/paper");
        logger.log(Level.SEVERE, BORDER);
    }

    @ParametersAreNonnullByDefault
    static void oldJavaVersion(Logger logger, int recommendedJavaVersion) {
        int javaVersion = NumberUtils.getJavaVersion();

        logger.log(Level.WARNING, BORDER);
        logger.log(Level.WARNING, PREFIX + "Your Java version (Java {0}) is outdated.", javaVersion);
        logger.log(Level.WARNING, PREFIX);
        logger.log(
                Level.WARNING,
                PREFIX + "This Minecraft version requires newer Java features. Java {0} is recommended.",
                recommendedJavaVersion);
        logger.log(
                Level.WARNING,
                PREFIX + "Please upgrade to Java {0} to keep Slimefun working correctly.",
                recommendedJavaVersion);
        logger.log(Level.WARNING, BORDER);
    }
}

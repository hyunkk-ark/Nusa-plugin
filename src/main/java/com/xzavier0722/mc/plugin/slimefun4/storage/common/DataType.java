package com.xzavier0722.mc.plugin.slimefun4.storage.common;

/**
 *{@link DataType}is the type of Slimefun database controller,
 * Used to distinguish different data storage types.
 */
public enum DataType {
    /**
     * Player profile, which usually contains research progress, backpack and other player-related data.
     */
    PLAYER_PROFILE,

    /**
     * Slimefun block data
     */
    BLOCK_STORAGE
}

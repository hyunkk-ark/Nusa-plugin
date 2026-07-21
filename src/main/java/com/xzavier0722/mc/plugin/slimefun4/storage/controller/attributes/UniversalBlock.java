package com.xzavier0722.mc.plugin.slimefun4.storage.controller.attributes;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunUniversalBlockData;
import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunUniversalData;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;

/**
 * This attribute is used to declare that{@link SlimefunItem}uses{@link SlimefunUniversalData}
 * <p>
 * When this{@link SlimefunItem}is used as a machine, the corresponding material needs to be supported
 * Use PDC storage container (for identifying UUID).
 * Otherwise, this item/machine cannot be bound to a common data.
 *
 * See here for PDC supported item textures:
 * <a href="https://jd.papermc.io/paper/1.21/org/bukkit/block/TileState.html">Paper Doc</a>
 *
 * @author NoRainCity
 *
 * @see SlimefunUniversalData
 * @see SlimefunUniversalBlockData
 */
public interface UniversalBlock {}

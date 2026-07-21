package city.norain.slimefun4.compatibillty;

import city.norain.slimefun4.SlimefunExtended;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.WallSign;

@UtilityClass
public class CompatibilityUtil {
    /**
     * Get the material of the item used by the player to place this block.
     * For most blocks, this is the same as getMaterial(), but some blocks have different materials used to place them.
     * Note: Not all possible different block data types are covered here.
     *
     * @param blockData
     *@returnThe material used to place this block
     */
    public Material getPlacementMaterial(BlockData blockData) {
        if (SlimefunExtended.isAtLeast(1, 19, 4)) {
            return blockData.getPlacementMaterial();
        } else {
            switch (blockData.getMaterial()) {
                case PLAYER_WALL_HEAD -> {
                    return Material.PLAYER_HEAD;
                }
                case REDSTONE_WIRE -> {
                    return Material.REDSTONE;
                }
                default -> {
                    var mat = blockData.getMaterial();
                    var enumName = blockData.getMaterial().name();

                    if (Ageable.class.equals(mat.data) && enumName.endsWith("S")) {
                        var itemMat = Material.getMaterial(enumName.substring(0, enumName.length() - 1));
                        return itemMat != null && itemMat.isItem() ? itemMat : mat;
                    }

                    if (WallSign.class.equals(mat.data) && enumName.contains("_WALL_")) {
                        Material itemMat = Material.getMaterial(enumName.replace("_WALL_", "_"));

                        if (itemMat != null && itemMat.isItem()) {
                            return mat;
                        }
                    }

                    // Fallback to original material
                    return blockData.getMaterial();
                }
            }
        }
    }

    /**
     * Check if the player is connected.
     * In 1.20- there is no guarantee whether the player is connected or not, only online status will be returned.
     *
     *@paramplayer offline player
     *@returnplayer is connected or online
     */
    public boolean isConnected(OfflinePlayer player) {
        if (SlimefunExtended.isAtLeast(1, 20) && Slimefun.instance().getServer().getOnlineMode()) {
            return player.isConnected();
        } else {
            return player.isOnline();
        }
    }
}

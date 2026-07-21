package com.github.cahyunkk.nusaenchant.effect.impl;

import com.github.cahyunkk.nusaenchant.effect.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashSet;
import java.util.Set;

public class VeinMinerEffect implements Effect {

    private static final Set<Material> ORES = Set.of(
            Material.COAL_ORE, Material.IRON_ORE, Material.GOLD_ORE,
            Material.DIAMOND_ORE, Material.EMERALD_ORE, Material.REDSTONE_ORE,
            Material.LAPIS_ORE, Material.COPPER_ORE, Material.DEEPSLATE_COAL_ORE,
            Material.DEEPSLATE_IRON_ORE, Material.DEEPSLATE_GOLD_ORE,
            Material.DEEPSLATE_DIAMOND_ORE, Material.DEEPSLATE_EMERALD_ORE
    );

    @Override
    public void execute(Player player, Event event, int level) {
        if (event instanceof BlockBreakEvent breakEvent) {
            Block block = breakEvent.getBlock();
            if (!ORES.contains(block.getType())) return;

            // Simple vein mining (max 32 blocks)
            Set<Block> vein = new HashSet<>();
            findVein(block, vein, 32);

            for (Block b : vein) {
                if (b != block) {
                    b.breakNaturally(player.getInventory().getItemInMainHand());
                }
            }
            player.sendMessage("§a[Enchant] Vein Miner: " + vein.size() + " blocks mined!");
        }
    }

    private void findVein(Block start, Set<Block> vein, int max) {
        if (vein.size() >= max) return;
        if (!ORES.contains(start.getType())) return;
        if (vein.contains(start)) return;

        vein.add(start);

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (x == 0 && y == 0 && z == 0) continue;
                    Block next = start.getRelative(x, y, z);
                    findVein(next, vein, max);
                }
            }
        }
    }

    @Override
    public String getName() {
        return "VEIN_MINER";
    }
}
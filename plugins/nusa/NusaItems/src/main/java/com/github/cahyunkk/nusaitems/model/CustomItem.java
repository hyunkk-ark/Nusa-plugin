package com.github.cahyunkk.nusaitems.model;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.*;

public class CustomItem {
    private final String id, name, texture;
    private final Material material;
    private final int customModelData;
    private final List<String> lore;
    private final boolean glow, unbreakable;
    private final Map<Attribute, Double> attributes;

    public CustomItem(String id, String name, Material material, String texture, int cdm,
                      List<String> lore, boolean glow, boolean unbreakable, Map<Attribute, Double> attrs) {
        this.id=id; this.name=name; this.material=material; this.texture=texture;
        this.customModelData=cdm; this.lore=lore; this.glow=glow; this.unbreakable=unbreakable;
        this.attributes=attrs;
    }

    public ItemStack build() {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;
        meta.setDisplayName(ChatUtil.colorize(name));
        List<String> cl = new ArrayList<>();
        for (String l : lore) cl.add(ChatUtil.colorize(l));
        meta.setLore(cl);
        meta.setCustomModelData(customModelData);
        if (unbreakable) meta.setUnbreakable(true);
        if (glow) {
            meta.addEnchant(org.bukkit.enchantments.Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        if (!attributes.isEmpty()) {
            ItemMeta m2 = item.getItemMeta();
            if (m2 != null) {
                for (var e : attributes.entrySet()) {
                    try {
                        m2.addAttributeModifier(e.getKey(), new AttributeModifier(
                            new NamespacedKey("nusaitems", e.getKey().name().toLowerCase()),
                            e.getValue(), AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.HAND));
                    } catch (Exception ignored) {}
                }
                item.setItemMeta(m2);
            }
        }
        return item;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public Material getMaterial() { return material; }
    public String getTexture() { return texture; }
    public int getCustomModelData() { return customModelData; }
}

package com.github.Glatinis.godTrident;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public final class GodTridentItem {

    public static final int CUSTOM_MODEL_DATA = 10000;
    private static NamespacedKey key;

    private GodTridentItem() {}

    public static void init(GodTrident plugin) {
        key = new NamespacedKey(plugin, "god_trident");
    }

    public static ItemStack create() {
        ItemStack item = new ItemStack(Material.TRIDENT);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text("God Trident").color(NamedTextColor.AQUA));
        meta.lore(List.of(
            Component.text("A trident forged by the gods.").color(NamedTextColor.GRAY)
        ));
        meta.setCustomModelData(CUSTOM_MODEL_DATA);
        meta.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);
        item.setItemMeta(meta);
        return item;
    }

    public static boolean isGodTrident(ItemStack item) {
        if (item == null || item.getType() != Material.TRIDENT) return false;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;
        return meta.getPersistentDataContainer().has(key, PersistentDataType.BYTE);
    }
}

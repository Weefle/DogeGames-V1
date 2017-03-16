package net.dogegames.dogecore.api.item;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemBuilder {
    private Material material;
    private int amount;
    private byte data;
    private String name;
    private List<String> lore;
    private Map<Enchantment, Integer> enchantments;
    private List<ItemFlag> flags;
    private boolean unbreakable;

    /**
     * @param material The material.
     */
    public ItemBuilder(Material material) {
        this(material, 1);
    }

    /**
     * @param material The material.
     * @param amount   The amount.
     */
    public ItemBuilder(Material material, int amount) {
        this.material = material;
        this.amount = amount;
    }

    /**
     * Set the material.
     *
     * @param material The material.
     * @return Builder.
     */
    public ItemBuilder setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * Set the amount.
     *
     * @param amount The amount.
     * @return Builder.
     */
    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    /**
     * Set the data.
     *
     * @param data The data.
     * @return Builder.
     */
    public ItemBuilder setData(byte data) {
        this.data = data;
        return this;
    }

    /**
     * Set the name.
     *
     * @param name The name.
     * @return Builder.
     */
    public ItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Set the lore.
     *
     * @param lore The lore.
     * @return Builder.
     */
    public ItemBuilder setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    /**
     * Add an enchantment.
     *
     * @param enchantment The enchantment.
     * @param level       The level.
     * @return Builder.
     */
    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        if (enchantments == null) enchantments = new HashMap<>();
        enchantments.put(enchantment, level);
        return this;
    }

    /**
     * Add a flag.
     *
     * @param flag The flag.
     * @return Builder.
     */
    public ItemBuilder addFlag(ItemFlag flag) {
        if (flags == null) flags = new ArrayList<>();
        flags.add(flag);
        return this;
    }

    /**
     * Set if it's unbreakable.
     *
     * @param unbreakable If it's unbreakable.
     * @return Builder.
     */
    public ItemBuilder setUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        return this;
    }

    /**
     * Build the item.
     *
     * @return An {@link ItemStack}.
     */
    public ItemStack build() {
        ItemStack item = new ItemStack(material, amount, data);
        ItemMeta meta = item.getItemMeta();
        if (name != null) meta.setDisplayName(name);
        if (lore != null) meta.setLore(lore);
        if (enchantments != null)
            enchantments.forEach((enchantment, level) -> meta.addEnchant(enchantment, level, true));
        if (flags != null) flags.forEach(meta::addItemFlags);
        meta.spigot().setUnbreakable(unbreakable);
        item.setItemMeta(meta);
        return item;
    }
}

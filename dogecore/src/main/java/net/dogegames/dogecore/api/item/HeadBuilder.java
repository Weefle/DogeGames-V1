package net.dogegames.dogecore.api.item;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeadBuilder {
    private String username;
    private int amount;
    private byte data;
    private String name;
    private List<String> lore;
    private Map<Enchantment, Integer> enchantments;
    private List<ItemFlag> flags;
    private boolean unbreakable;

    /**
     * @param head The head.
     */
    public HeadBuilder(Head head) {
        this(head, 1);
    }

    /**
     * @param head   The head.
     * @param amount The amount.
     */
    public HeadBuilder(Head head, int amount) {
        this(head.getUsername(), amount);
    }

    /**
     * @param username The head's name.
     */
    public HeadBuilder(String username) {
        this(username, 1);
    }

    /**
     * @param username The head's name.
     * @param amount   The amount.
     */
    public HeadBuilder(String username, int amount) {
        this.username = username;
        this.amount = amount;
    }

    /**
     * Set the head's name.
     *
     * @param username The head's name.
     * @return Builder.
     */
    public HeadBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * Set the head.
     *
     * @param head The head.
     * @return Builder.
     */
    public HeadBuilder setUsername(Head head) {
        return setUsername(head.getUsername());
    }

    /**
     * Set the amount.
     *
     * @param amount The amount.
     * @return Builder.
     */
    public HeadBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    /**
     * Set the data.
     *
     * @param data The data.
     * @return Builder.
     */
    public HeadBuilder setData(byte data) {
        this.data = data;
        return this;
    }

    /**
     * Set the name.
     *
     * @param name The name.
     * @return Builder.
     */
    public HeadBuilder setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Set the lore.
     *
     * @param lore The lore.
     * @return Builder.
     */
    public HeadBuilder setLore(List<String> lore) {
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
    public HeadBuilder addEnchantment(Enchantment enchantment, int level) {
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
    public HeadBuilder addFlag(ItemFlag flag) {
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
    public HeadBuilder setUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        return this;
    }

    /**
     * Build the head.
     *
     * @return An {@link ItemStack}.
     */
    public ItemStack build() {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, amount, data);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(username);
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

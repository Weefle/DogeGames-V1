package net.dogegames.dogecore.listener;

import com.comphenix.packetwrapper.WrapperPlayClientBlockPlace;
import com.comphenix.packetwrapper.WrapperPlayServerSetSlot;
import com.comphenix.packetwrapper.WrapperPlayServerWindowItems;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import net.dogegames.dogecore.DogeCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class PvPListener extends PacketAdapter implements Listener {
    private static final Collection<Material> SWORDS;
    private static final Map<Material, Double> TOOLS_DAMAGE;
    private static final Collection<PotionEffect> ENCHANTED_GOLDEN_APPLE_EFFECTS;
    private static final Collection<PotionEffect> GOLDEN_APPLE_EFFECTS;
    private static final ItemStack SHIELD;
    private static final ShapedRecipe ENCHANTED_GOLDEN_APPLE_RECIPE;

    static {
        SWORDS = Collections.unmodifiableCollection(Arrays.asList(
                Material.WOOD_SWORD,
                Material.STONE_SWORD,
                Material.IRON_SWORD,
                Material.GOLD_SWORD,
                Material.DIAMOND_SWORD));
        TOOLS_DAMAGE = Collections.unmodifiableMap(new HashMap<Material, Double>() {
            {
                // Sword
                put(Material.WOOD_SWORD, 4D);
                put(Material.STONE_SWORD, 5D);
                put(Material.IRON_SWORD, 6D);
                put(Material.GOLD_SWORD, 4D);
                put(Material.DIAMOND_SWORD, 7D);
                // Axe
                put(Material.WOOD_AXE, 3D);
                put(Material.STONE_AXE, 4D);
                put(Material.IRON_AXE, 5D);
                put(Material.GOLD_AXE, 3D);
                put(Material.DIAMOND_AXE, 6D);
                // Pickaxe
                put(Material.WOOD_PICKAXE, 2D);
                put(Material.STONE_PICKAXE, 3D);
                put(Material.IRON_PICKAXE, 4D);
                put(Material.GOLD_PICKAXE, 2D);
                put(Material.DIAMOND_PICKAXE, 5D);
                // Spade
                put(Material.WOOD_SPADE, 1D);
                put(Material.STONE_SPADE, 2D);
                put(Material.IRON_SPADE, 3D);
                put(Material.GOLD_SPADE, 1D);
                put(Material.DIAMOND_SPADE, 4D);
            }
        });

        ENCHANTED_GOLDEN_APPLE_EFFECTS = Arrays.asList(
                new PotionEffect(PotionEffectType.REGENERATION, 30 * 20, 4),
                new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 300 * 20, 0),
                new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 300 * 20, 0),
                new PotionEffect(PotionEffectType.ABSORPTION, 120 * 20, 0));

        GOLDEN_APPLE_EFFECTS = Arrays.asList(
                new PotionEffect(PotionEffectType.REGENERATION, 5 * 20, 1),
                new PotionEffect(PotionEffectType.ABSORPTION, 120 * 20, 0));

        SHIELD = new ItemStack(Material.SHIELD);
        ItemMeta meta = SHIELD.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.spigot().setUnbreakable(true);
        SHIELD.setItemMeta(meta);

        ENCHANTED_GOLDEN_APPLE_RECIPE = new ShapedRecipe(new ItemStack(Material.GOLDEN_APPLE, 1, (byte) 1));
        ENCHANTED_GOLDEN_APPLE_RECIPE.shape("ggg", "gag", "ggg");
        ENCHANTED_GOLDEN_APPLE_RECIPE.setIngredient('g', Material.GOLD_BLOCK);
        ENCHANTED_GOLDEN_APPLE_RECIPE.setIngredient('a', Material.APPLE);
    }

    private DogeCore plugin;
    private Map<UUID, ItemStack> shieldUsers;

    public PvPListener(DogeCore plugin) {
        super(plugin, ListenerPriority.HIGHEST,
                PacketType.Play.Server.WINDOW_ITEMS,
                PacketType.Play.Server.SET_SLOT,
                PacketType.Play.Client.BLOCK_PLACE,
                PacketType.Play.Client.HELD_ITEM_SLOT,
                PacketType.Play.Client.CLIENT_COMMAND);
        ProtocolLibrary.getProtocolManager().addPacketListener(this);
        this.plugin = plugin;
        this.shieldUsers = new HashMap<>();
        Bukkit.addRecipe(ENCHANTED_GOLDEN_APPLE_RECIPE);
    }

    public void clearShields() {
        shieldUsers.forEach((k, v) -> {
            Player player = Bukkit.getPlayer(k);
            if (player != null) {
                player.getInventory().setItemInOffHand(v);
            }
        });
        shieldUsers.clear();
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        PacketType type = event.getPacketType();
        if (type == PacketType.Play.Server.WINDOW_ITEMS) {
            WrapperPlayServerWindowItems packet = new WrapperPlayServerWindowItems(event.getPacket());
            ItemStack[] items = packet.getSlotData();
            boolean updated = false;
            for (int i = 0; i < items.length; i++) {
                ItemStack item = items[i];
                if ((item = updateItem(item)) != null) {
                    items[i] = item;
                    updated = true;
                }
            }
            if (updated) packet.setSlotData(items);
        } else if (type == PacketType.Play.Server.SET_SLOT) {
            WrapperPlayServerSetSlot packet = new WrapperPlayServerSetSlot(event.getPacket());
            ItemStack item = packet.getSlotData();
            if ((item = updateItem(item)) != null) {
                packet.setSlotData(item);
            }
        }
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        PacketType type = event.getPacketType();
        if (type == PacketType.Play.Client.BLOCK_PLACE) {
            WrapperPlayClientBlockPlace packet = new WrapperPlayClientBlockPlace(event.getPacket());
            if (packet.getHand() == EnumWrappers.Hand.MAIN_HAND) {
                Player player = event.getPlayer();
                if (player.getInventory().getItemInMainHand() != null && SWORDS.contains(player.getInventory().getItemInMainHand().getType())) {
                    addShield(player);
                }
            }
        } else if (type == PacketType.Play.Client.HELD_ITEM_SLOT) {
            Player player = event.getPlayer();
            removeShield(player);
        } else if (type == PacketType.Play.Client.CLIENT_COMMAND) {
            Player player = event.getPlayer();
            removeShield(player);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(100);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (shieldUsers.containsKey(player.getUniqueId())) {
            player.getInventory().setItemInOffHand(shieldUsers.get(player.getUniqueId()));
            shieldUsers.remove(player.getUniqueId());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSwap(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        if (shieldUsers.containsKey(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item != null && TOOLS_DAMAGE.containsKey(item.getType())) {
                event.setDamage(getDamage(item));
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onHit(ProjectileHitEvent event) {
        if (event.getEntityType() == EntityType.FISHING_HOOK) {
            Entity entity = event.getEntity();
            Collection<Entity> entities = Bukkit.getWorld(entity.getLocation().getWorld().getName()).getNearbyEntities(entity.getLocation(), 0.25, 0.25, 0.25);
            entities.stream().filter(e -> e instanceof Player).forEach(e -> {
                Location loc = e.getLocation().add(0, 0.5, 0);
                e.teleport(loc);
                e.setVelocity(loc.subtract(((Player) event.getEntity().getShooter()).getLocation()).toVector().normalize().multiply(0.2));
            });
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageEvent event) {
        if (event.isApplicable(EntityDamageEvent.DamageModifier.BLOCKING) && event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (player.isBlocking()) {
                double damage = event.getOriginalDamage(EntityDamageEvent.DamageModifier.BASE) + event.getOriginalDamage(EntityDamageEvent.DamageModifier.HARD_HAT);
                if (damage > 0) {
                    event.setDamage(EntityDamageEvent.DamageModifier.BLOCKING, -(damage - (1 + damage) * 0.5f));
                    return;
                }
            }
            event.setDamage(EntityDamageEvent.DamageModifier.BLOCKING, -0);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onOpenInventory(InventoryOpenEvent event) {
        Player player = (Player) event.getPlayer();
        removeShield(player);
        if (event.getInventory().getType() == InventoryType.ENCHANTING) {
            event.getInventory().setItem(1, new ItemStack(Material.INK_SACK, 64, (byte) 4));
        } else if (event.getInventory().getType() == InventoryType.BREWING) {
            event.getInventory().setItem(4, new ItemStack(Material.BLAZE_POWDER, 64));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClickInventory(InventoryClickEvent event) {
        if (event.getClickedInventory() != null) {
            if (event.getClickedInventory().getType() == InventoryType.ENCHANTING && event.getSlot() == 1) {
                event.setCancelled(true);
            } else if (event.getClickedInventory().getType() == InventoryType.BREWING && event.getSlot() == 4) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCloseInventory(InventoryCloseEvent event) {
        if (event.getInventory().getType() == InventoryType.ENCHANTING) {
            event.getInventory().setItem(1, null);
        } else if (event.getInventory().getType() == InventoryType.BREWING) {
            event.getInventory().setItem(4, null);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onConsumeItem(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() == Material.GOLDEN_APPLE) {
            Player player = event.getPlayer();
            player.setFoodLevel(player.getFoodLevel() + 4 > 20 ? 20 : player.getFoodLevel() + 4);
            Collection<PotionEffect> effects = event.getItem().getDurability() == 1 ? ENCHANTED_GOLDEN_APPLE_EFFECTS : GOLDEN_APPLE_EFFECTS;
            effects.forEach(effect -> player.removePotionEffect(effect.getType()));
            player.addPotionEffects(effects);
            ItemStack item = event.getItem();
            item.setAmount(item.getAmount() - 1);
            if (player.getInventory().getItemInMainHand().equals(event.getItem())) {
                player.getInventory().setItemInMainHand(item);
            } else {
                player.getInventory().setItemInOffHand(item);
            }
            event.setCancelled(true);
        }
    }

    private ItemStack updateItem(ItemStack item) {
        if (item == null) return null;
        ItemMeta meta = item.getItemMeta();
        if (meta.getItemFlags().contains(ItemFlag.HIDE_ATTRIBUTES) && meta.getLore() != null) {
            return null;
        }
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        return item;
    }

    private double getSharpnessDamage(int level) {
        return 1.25 * level;
    }

    private double getDamage(ItemStack item) {
        if (item == null) return 0;
        if (TOOLS_DAMAGE.containsKey(item.getType())) {
            double damage = TOOLS_DAMAGE.get(item.getType());
            ItemMeta meta = item.getItemMeta();
            damage += getSharpnessDamage(meta.getEnchantLevel(Enchantment.DAMAGE_ALL));
            return damage;
        }
        return 0;
    }

    private void addShield(Player player) {
        Bukkit.getScheduler().runTask(plugin, () -> {
            synchronized (shieldUsers) {
                if (!shieldUsers.containsKey(player.getUniqueId())) {
                    shieldUsers.put(player.getUniqueId(), player.getInventory().getItemInOffHand());
                    player.getInventory().setItemInOffHand(SHIELD.clone());
                    Bukkit.getScheduler().runTaskLater(plugin, () -> removeShield(player), 20L);
                }
            }
        });
    }

    private void removeShield(Player player) {
        Bukkit.getScheduler().runTask(plugin, () -> {
            synchronized (shieldUsers) {
                if (shieldUsers.containsKey(player.getUniqueId())) {
                    player.getInventory().setItemInOffHand(shieldUsers.get(player.getUniqueId()));
                    shieldUsers.remove(player.getUniqueId());
                }
            }
        });
    }
}

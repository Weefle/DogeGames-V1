package net.dogegames.dogebungee.player.permission;

import net.md_5.bungee.api.ChatColor;

public enum Rank {
    PLAYER(0, "Joueur", ChatColor.GRAY),
    VIP(1, "VIP", ChatColor.GREEN),
    DOGE(2, "Doge", ChatColor.YELLOW),
    YOUTUBER(3, "Youtubeur", ChatColor.LIGHT_PURPLE),
    HELPER(4, "Guide", ChatColor.AQUA),
    MODERATOR(5, "Modérateur", ChatColor.DARK_PURPLE),
    BUILDER(6, "Constructeur", ChatColor.DARK_GREEN),
    DEVELOPER(6, "Développeur", ChatColor.DARK_GREEN),
    ADMIN(9, "Administrateur", ChatColor.RED);

    private int power;
    private String name;
    private ChatColor color;

    Rank(int power, String name, ChatColor color) {
        this.power = power;
        this.name = name;
        this.color = color;
    }

    /**
     * @return Rank's power.
     */
    public int getPower() {
        return power;
    }

    /**
     * @return Rank's name.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Rank's color.
     */
    public ChatColor getColor() {
        return color;
    }
}

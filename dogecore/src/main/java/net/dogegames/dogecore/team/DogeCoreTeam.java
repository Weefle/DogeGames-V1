package net.dogegames.dogecore.team;

import net.dogegames.dogecore.api.event.player.DogePlayerJoinTeamEvent;
import net.dogegames.dogecore.api.event.player.DogePlayerQuitTeamEvent;
import net.dogegames.dogecore.api.team.DogeTeam;
import net.dogegames.dogecore.player.DogeCorePlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DogeCoreTeam implements DogeTeam {
    private Team craftTeam;
    private int id;
    private String name;
    private ChatColor chatColor;
    private Color color;
    private DyeColor dyeColor;
    private boolean allowFriendlyFire;
    private Collection<DogeCorePlayer> players;
    private Map<Plugin, Object> datas;

    DogeCoreTeam(int id, String name, ChatColor chatColor, Color color, DyeColor dyeColor) {
        this.craftTeam = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(id + "-" + name.toLowerCase());
        if (craftTeam == null) {
            craftTeam = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam(id + "-" + name.toLowerCase());
        }
        this.id = id;
        this.name = name;
        this.chatColor = chatColor;
        this.color = color;
        this.dyeColor = dyeColor;
        this.players = new ArrayList<>();
        this.datas = new HashMap<>();

        craftTeam.setDisplayName(name);
        craftTeam.setCanSeeFriendlyInvisibles(false);
        craftTeam.setPrefix(chatColor.toString());
        craftTeam.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ChatColor getChatColor() {
        return chatColor;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public DyeColor getDyeColor() {
        return dyeColor;
    }

    @Override
    public boolean isAllowFriendlyFire() {
        return allowFriendlyFire;
    }

    @Override
    public void setAllowFriendlyFire(boolean allowFriendlyFire) {
        this.allowFriendlyFire = allowFriendlyFire;
        craftTeam.setAllowFriendlyFire(allowFriendlyFire);
    }

    @Override
    public Collection<DogeCorePlayer> getPlayers() {
        return players;
    }

    @Override
    public Object getData(Plugin plugin) {
        return datas.get(plugin);
    }

    @Override
    public void setData(Plugin plugin, Object object) {
        datas.put(plugin, object);
    }

    @Override
    public boolean hasData(Plugin plugin) {
        return datas.containsKey(plugin);
    }

    @Override
    public void broadcast(String message) {
        getPlayers().forEach(player -> Bukkit.getPlayer(player.getUniqueId()).sendMessage(message));
    }

    /**
     * Add new member.
     *
     * @param player Player.
     */
    public void addPlayer(DogeCorePlayer player) {
        craftTeam.addEntry(player.getUsername());
        players.add(player);
        Bukkit.getPluginManager().callEvent(new DogePlayerJoinTeamEvent(player, this));
    }

    /**
     * Remove a member.
     *
     * @param player Player.
     */
    public void removePlayer(DogeCorePlayer player) {
        craftTeam.removeEntry(player.getUsername());
        players.remove(player);
        Bukkit.getPluginManager().callEvent(new DogePlayerQuitTeamEvent(player, this));
    }
}

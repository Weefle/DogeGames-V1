package net.dogegames.dogecore.player;

import com.google.common.base.Preconditions;
import net.dogegames.dogecore.DogeCore;
import net.dogegames.dogecore.api.event.player.DogePlayerUpdateRankEvent;
import net.dogegames.dogecore.api.permission.Rank;
import net.dogegames.dogecore.api.player.DogePlayer;
import net.dogegames.dogecore.api.team.DogeTeam;
import net.dogegames.dogecore.display.DogeCoreBar;
import net.dogegames.dogecore.display.DogeCoreScoreboard;
import net.dogegames.dogecore.team.DogeCoreTeam;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DogeCorePlayer implements DogePlayer {
    private UUID uniqueId;
    private String username;
    private Rank rank;
    private double dogeCoins;
    private double shiba;
    private DogeCorePlayerOptions options;
    private Map<Plugin, Object> datas;
    private DogeCoreTeam team;
    private DogeCoreBar bar;
    private DogeCoreScoreboard scoreboard;

    DogeCorePlayer(UUID uniqueId, String username, Rank rank, double dogeCoins, double shiba, DogeCorePlayerOptions options) {
        this.uniqueId = uniqueId;
        this.username = username;
        this.rank = rank;
        this.dogeCoins = dogeCoins;
        this.shiba = shiba;
        this.options = options;
        this.datas = new HashMap<>();
        this.bar = new DogeCoreBar(this);
        this.scoreboard = new DogeCoreScoreboard(this);
    }

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Rank getRank() {
        return rank;
    }

    /**
     * Update the player's rank.
     *
     * @param rank The new rank.
     */
    public void setRank(Rank rank) {
        Bukkit.getPluginManager().callEvent(new DogePlayerUpdateRankEvent(this, this.rank, rank));
        this.rank = rank;
    }

    @Override
    public double getDogeCoins() {
        return dogeCoins;
    }

    @Override
    public void setDogeCoins(double dogeCoins) {
        this.dogeCoins = dogeCoins;
    }

    @Override
    public double getShiba() {
        return shiba;
    }

    @Override
    public void setShiba(double shiba) {
        this.shiba = shiba;
    }

    @Override
    public DogeCorePlayerOptions getOptions() {
        return options;
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
    public DogeCoreTeam getTeam() {
        return team;
    }

    @Override
    public void setTeam(DogeTeam team) {
        if (this.team != null) this.team.removePlayer(this);
        if (team != null) {
            Preconditions.checkNotNull(DogeCore.getInstance().getTeamManager().getTeam(team.getId()), "This team isn't registered!");
        }
        this.team = (DogeCoreTeam) team;
        if (this.team != null) this.team.addPlayer(this);
    }

    @Override
    public DogeCoreBar getBar() {
        return bar;
    }

    @Override
    public DogeCoreScoreboard getScoreboard() {
        return scoreboard;
    }
}

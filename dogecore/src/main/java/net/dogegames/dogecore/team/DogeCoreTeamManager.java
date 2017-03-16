package net.dogegames.dogecore.team;

import net.dogegames.dogecore.DogeCore;
import net.dogegames.dogecore.api.team.DogePredefinedTeams;
import net.dogegames.dogecore.api.team.DogeTeam;
import net.dogegames.dogecore.api.team.DogeTeamManager;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class DogeCoreTeamManager implements DogeTeamManager {
    private Collection<DogeCoreTeam> teams;
    private int maxPlayersPerTeam;
    private DogePredefinedTeams predefinedTeams;

    public DogeCoreTeamManager(DogeCore plugin) {
        this.teams = new ArrayList<>();
        this.maxPlayersPerTeam = 2;
        this.predefinedTeams = new DogeCorePredefinedTeams(plugin);
    }

    @Override
    public Collection<DogeCoreTeam> getTeams() {
        return Collections.unmodifiableCollection(teams);
    }

    @Override
    public DogeCoreTeam getTeam(String name) {
        return teams.stream().filter(team -> team.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    @Override
    public DogeCoreTeam getTeam(int id) {
        return teams.stream().filter(team -> team.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void registerTeam(DogeTeam team) {
        if (getTeam(team.getName()) != null) throw new RuntimeException("This team is already registered!");
        teams.add((DogeCoreTeam) team);
    }

    @Override
    public int getMaxPlayersPerTeam() {
        return maxPlayersPerTeam;
    }

    @Override
    public void setMaxPlayersPerTeam(int maxPlayersPerTeam) {
        this.maxPlayersPerTeam = maxPlayersPerTeam;
    }

    @Override
    public DogeCoreTeam createTeam(String name, ChatColor chatColor, Color color, DyeColor dyeColor) {
        return new DogeCoreTeam(teams.size(), name, chatColor, color, dyeColor);
    }

    @Override
    public DogePredefinedTeams getPredefinedTeams() {
        return predefinedTeams;
    }
}

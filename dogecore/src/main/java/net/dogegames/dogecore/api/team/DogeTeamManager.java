package net.dogegames.dogecore.api.team;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;

import java.util.Collection;

public interface DogeTeamManager {
    /**
     * @return All registered teams;
     */
    Collection<? extends DogeTeam> getTeams();

    /**
     * Find team by name.
     *
     * @param name Name.
     * @return Team.
     */
    DogeTeam getTeam(String name);

    /**
     * Find team by id.
     *
     * @param id Id.
     * @return Team.
     */
    DogeTeam getTeam(int id);

    /**
     * Register new team.
     *
     * @param team The team.
     */
    void registerTeam(DogeTeam team);

    /**
     * @return Max players per team.
     */
    int getMaxPlayersPerTeam();

    /**
     * Set max players per team.
     *
     * @param maxPlayersPerTeam Max players per team.
     */
    void setMaxPlayersPerTeam(int maxPlayersPerTeam);

    /**
     * Create new team.
     *
     * @param name      Name.
     * @param chatColor Chat color.
     * @param color     Color.
     * @param dyeColor  Dye color.
     * @return The team.
     */
    DogeTeam createTeam(String name, ChatColor chatColor, Color color, DyeColor dyeColor);

    /**
     * @return Predefined teams.
     */
    DogePredefinedTeams getPredefinedTeams();
}

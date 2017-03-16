package net.dogegames.dogecore.api.location;

import org.bukkit.Location;

import java.util.Map;

public interface DogeLocationManager {
    /**
     * @return All locations.
     */
    Map<String, Location> getLocations();

    /**
     * @param path The path.
     * @return The location.
     */
    Location getLocation(String path);

    /**
     * Set a location.
     *
     * @param path     The path.
     * @param location The location.
     */
    void setLocation(String path, Location location);

    /**
     * @param path The path.
     * @return If the location exists.
     */
    boolean hasLocation(String path);

    /**
     * Resolve all worlds and imported.
     */
    void importWorlds();
}

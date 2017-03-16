package net.dogegames.dogecore.location;

import net.dogegames.dogecore.DogeCore;
import net.dogegames.dogecore.api.location.DogeLocationManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class DogeCoreLocationManager implements DogeLocationManager {
    private DogeCore plugin;
    private Map<String, Location> locationMap;
    private Map<String, YamlConfiguration> configurationMap;

    public DogeCoreLocationManager(DogeCore plugin) {
        this.plugin = plugin;
        this.locationMap = new HashMap<>();
        this.configurationMap = new HashMap<>();

        importWorlds();
    }

    @Override
    public Map<String, Location> getLocations() {
        return Collections.unmodifiableMap(locationMap);
    }

    @Override
    public Location getLocation(String path) {
        return hasLocation(path) ? locationMap.get(path.toLowerCase()) : null;
    }

    @Override
    public void setLocation(String path, Location location) {
        path = path.toLowerCase();
        YamlConfiguration configuration = configurationMap.get(location.getWorld().getName());
        configuration.set(path + ".x", location.getX());
        configuration.set(path + ".y", location.getY());
        configuration.set(path + ".z", location.getZ());
        configuration.set(path + ".yaw", location.getYaw());
        configuration.set(path + ".pitch", location.getPitch());

        try {
            configuration.save(new File(location.getWorld().getWorldFolder(), "locations.yml"));
            locationMap.put(path, location);
        } catch (IOException e) {
            plugin.getLogger().severe("Error while saving locations.yml: " + e.getMessage());
        }
    }

    @Override
    public boolean hasLocation(String path) {
        return locationMap.containsKey(path.toLowerCase());
    }

    @Override
    public void importWorlds() {
        // Find world folders
        List<File> worldFolders = new ArrayList<>();
        File[] files = Bukkit.getWorldContainer().listFiles();
        if (files == null) return;
        for (File world : files) {
            File region = new File(world, "region");
            if (world.isDirectory() && region.exists() && region.isDirectory()) {
                worldFolders.add(world);
            }
        }

        // Import
        worldFolders.forEach(folder -> {
            // Import the world
            World world = Bukkit.createWorld(new WorldCreator(folder.getName()));
            world.setAutoSave(false);
            world.setKeepSpawnInMemory(false);

            // Import locations
            File locationFile = new File(folder, "locations.yml");
            if (!locationFile.exists()) {
                try {
                    locationFile.createNewFile();
                } catch (IOException e) {
                    plugin.getLogger().severe("Error while creating locations.yml in " + folder + ": " + e.getMessage());
                }
            }
            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(locationFile);
            configuration.getKeys(false).forEach(key -> {
                double x = configuration.getDouble(key + ".x");
                double y = configuration.getDouble(key + ".y");
                double z = configuration.getDouble(key + ".z");
                float yaw = (float) configuration.getDouble(key + ".yaw");
                float pitch = (float) configuration.getDouble(key + ".pitch");
                locationMap.put(key, new Location(world, x, y, z, yaw, pitch));
            });
            configurationMap.put(world.getName(), configuration);
        });
    }
}

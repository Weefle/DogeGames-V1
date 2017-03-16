package net.dogegames.dogecore.api.data;

import org.bukkit.plugin.Plugin;

public interface DataHolder {
    /**
     * @param plugin The plugin.
     * @return Stored data.
     */
    Object getData(Plugin plugin);

    /**
     * Store custom data.
     *
     * @param plugin The plugin.
     * @param object Data.
     */
    void setData(Plugin plugin, Object object);

    /**
     * @param plugin The plugin.
     * @return If the plugin is storing data.
     */
    boolean hasData(Plugin plugin);
}

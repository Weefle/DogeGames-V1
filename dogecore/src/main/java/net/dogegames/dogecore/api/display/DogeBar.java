package net.dogegames.dogecore.api.display;

import org.bukkit.boss.BarColor;

public interface DogeBar {
    /**
     * @return If the bossbar is enable.
     */
    boolean isEnabled();

    /**
     * Set if the bossbar is enable.
     *
     * @param enabled If the bossbar is enable.
     */
    void setEnabled(boolean enabled);

    /**
     * @return Message.
     */
    String getMessage();

    /**
     * Set the message.
     *
     * @param message Message.
     */
    void setMessage(String message);

    /**
     * @return Percentage.
     */
    double getPercent();

    /**
     * Set the percentage.
     *
     * @param percent Percentage.
     */
    void setPercent(double percent);

    /**
     * @return Color.
     */
    BarColor getColor();

    /**
     * Set the color.
     *
     * @param color Color.
     */
    void setColor(BarColor color);
}

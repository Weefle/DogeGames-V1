package net.dogegames.dogecore.api.display;

public interface DogeScoreboard {
    /**
     * @return If the scoreboard is enable.
     */
    boolean isEnabled();

    /**
     * Set if the scoreboard is enable.
     *
     * @param enabled If the scoreboard is enable.
     */
    void setEnabled(boolean enabled);

    /**
     * @return Title.
     */
    String getTitle();

    /**
     * Set the title.
     *
     * @param title Title.
     */
    void setTitle(String title);

    /**
     * @return Lines.
     */
    String[] getLines();

    /**
     * Set a line.
     *
     * @param line  The line.
     * @param value The content.
     */
    void setLine(int line, String value);
}

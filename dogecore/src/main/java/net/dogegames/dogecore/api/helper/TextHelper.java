package net.dogegames.dogecore.api.helper;

public final class TextHelper {
    // We prevent fdp
    private TextHelper() {
    }

    /**
     * @param character The character.
     * @param times     Times.
     * @return A string with times value character.
     */
    public static String repeat(char character, int times) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < times; i++) builder.append(character);
        return builder.toString();
    }

    /**
     * @param text  The text.
     * @param width Width.
     * @return The text centered.
     */
    public static String centerText(String text, int width) {
        int spaces = (int) Math.round((width - 1.4 * text.length()) / 2);
        return repeat(' ', spaces) + text;
    }

    /**
     * @param word   The word.
     * @param number The number.
     * @return The new word.
     */
    public static String pluralize(String word, Number number) {
        return number.doubleValue() > 1 ? word + "s" : word;
    }

    /**
     * @param word           The word.
     * @param number         The number.
     * @param pluralizedWord The new pluralized word.
     * @return The new word.
     */
    public static String pluralize(String word, Number number, String pluralizedWord) {
        return number.doubleValue() > 1 ? pluralizedWord : word;
    }

    /**
     * Concat a string array to string
     *
     * @param args    String array.
     * @param startAt Start at.
     * @return Concatted string array.
     */
    public static String concat(String[] args, int startAt) {
        StringBuilder builder = new StringBuilder();
        for (int i = startAt; i < args.length; i++) {
            builder.append(args[i]);
            if (i != args.length - 1) builder.append(' ');
        }
        return builder.toString();
    }

    /**
     * Concat a string array to string
     *
     * @param args String array.
     * @return Concatted string array.
     */
    public static String concat(String[] args) {
        return concat(args, 0);
    }

    /**
     * Create string from array.
     *
     * @param array The array.
     * @return Concatted array.
     */
    public static String parseArray(String[] array) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            builder.append(array[i]);
            if (i != array.length - 1) builder.append(", ");
        }
        return builder.toString();
    }

    /**
     * @return The separator.
     */
    public static String separator() {
        return "-----------------------------------------------------";
    }
}

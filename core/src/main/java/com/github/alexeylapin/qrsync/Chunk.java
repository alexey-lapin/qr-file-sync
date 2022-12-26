package com.github.alexeylapin.qrsync;

public class Chunk implements Marker {

    public static final int RADIX = 36;

    private final int index;
    private final String data;

    public Chunk(int index, String data) {
        this.index = index;
        this.data = data;
    }

    @Override
    public String render() {
        return getPrefix() + data;
    }

    public String getPrefix() {
        return Integer.toString(index, RADIX).toUpperCase() + DELIMITER;
    }

    public static Chunk parse(String content) {
        String[] parts = content.split(DELIMITER);
        return new Chunk(Integer.parseInt(parts[0], RADIX), parts[1]);
    }

}

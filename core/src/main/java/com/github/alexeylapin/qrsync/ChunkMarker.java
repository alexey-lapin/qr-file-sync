package com.github.alexeylapin.qrsync;

public class ChunkMarker implements Marker {

    public static final int RADIX = 36;

    private final int index;
    private final String data;

    public ChunkMarker(int index, String data) {
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

    public static ChunkMarker parse(String content) {
        String[] parts = content.split(DELIMITER);
        return new ChunkMarker(Integer.parseInt(parts[0], RADIX), parts[1]);
    }

}

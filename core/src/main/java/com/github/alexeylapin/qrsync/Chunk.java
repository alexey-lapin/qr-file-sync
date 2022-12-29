package com.github.alexeylapin.qrsync;

public class Chunk {

    private final int index;
    private final byte[] bytes;

    public Chunk(int index, byte[] bytes) {
        this.index = index;
        this.bytes = bytes;
    }

    public int getIndex() {
        return index;
    }

    public byte[] getBytes() {
        return bytes;
    }

    @Override
    public String toString() {
        return "Chunk[" + index + "][" + bytes.length + "]";
    }

}

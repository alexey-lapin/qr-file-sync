package com.github.alexeylapin.qrsync;

public class Base32ByteArrayAllocator implements ByteArrayAllocator {

    private final int limit;

    public Base32ByteArrayAllocator(int limit) {
        this.limit = limit;
    }

    @Override
    public byte[] allocate(int index) {
        int length = limit - calculateBytes(getMetadata(index));
        return new byte[length];
    }

    private String getMetadata(int index) {
        return Integer.toString(index, 36).toUpperCase() + "/";
    }

    private int calculateBytes(String string) {
        int bytes = string.getBytes().length;
        if (bytes % 5 == 0) {
            return bytes;
        }
        return (bytes + 5) - (bytes % 5);
    }

}

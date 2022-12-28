package com.github.alexeylapin.qrsync;

@FunctionalInterface
public interface ByteArrayAllocator {

    byte[] allocate(int index);

}

package com.github.alexeylapin.qrsync;

import java.util.List;

public class Batch {

    private List<Chunk> chunks;

    public Batch(List<Chunk> chunks) {
        this.chunks = chunks;
    }

    public List<Chunk> getChunks() {
        return chunks;
    }

}

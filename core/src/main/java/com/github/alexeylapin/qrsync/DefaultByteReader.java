package com.github.alexeylapin.qrsync;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DefaultByteReader implements ByteReader {

    private final SeekableByteChannelInputStream source;
    private final ByteArrayAllocator allocator;
    private final int batchSize;
    private int currentBatchIndex;

    public DefaultByteReader(Path path, ByteArrayAllocator allocator, int batchSize) throws IOException {
        this.source = new SeekableByteChannelInputStream(Files.newByteChannel(path));
        this.allocator = allocator;
        this.batchSize = batchSize;
    }

    @Override
    public boolean next() throws IOException {
        source.savePosition();
        return false;
    }

    @Override
    public boolean prev() throws IOException {
        source.restorePosition();
        return false;
    }

    @Override
    public Batch readBatch() throws IOException {
        source.rewind();
        List<Chunk> chunks = new ArrayList<>();
        for (int i = currentBatchIndex; i < currentBatchIndex + batchSize; i++) {
            byte[] buffer = allocator.allocate(0);
            int read = source.read(buffer);
            chunks.add(new Chunk(i, buffer));
        }
//        source.mark(0);
        return new Batch(chunks);
    }

    @Override
    public void close() throws IOException {
        source.close();
    }

}

package com.github.alexeylapin.qrsync;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class DefaultBatchReader implements BatchReader {

    private final SeekableByteChannelInputStream source;
    private final ByteArrayAllocator allocator;
    private final Deque<Integer> stack;
    private final int batchSize;

    private int nextBatchIndex;

    public DefaultBatchReader(Path path, ByteArrayAllocator allocator, int batchSize) throws IOException {
        this.source = new SeekableByteChannelInputStream(Files.newByteChannel(path));
        this.stack = new ArrayDeque<>();
        this.allocator = allocator;
        this.batchSize = batchSize;
    }

    @Override
    public boolean next() throws IOException {
        boolean status = source.savePosition();
        if (status) {
            stack.addLast(nextBatchIndex);
        }
        return status;
    }

    @Override
    public boolean prev() throws IOException {
        boolean status = source.restorePosition();
        if (status) {
            stack.pollLast();
        }
        source.rewind();
        return status;
    }

    @Override
    public Batch readBatch() throws IOException {
        source.rewind();
        List<Chunk> chunks = new ArrayList<>();
        int currentBatchIndex = getCurrentBatchIndex();
        for (int i = currentBatchIndex; i < currentBatchIndex + batchSize; i++) {
            byte[] buffer = allocator.allocate(0);
            int read = source.read(buffer);
            if (read == -1) {
                break;
            }
            if (read < buffer.length) {
                buffer = Arrays.copyOf(buffer, read);
            }
            chunks.add(new Chunk(i, buffer));
            nextBatchIndex = i + 1;
        }
        return new Batch(chunks);
    }

    private int getCurrentBatchIndex() {
        Integer index = stack.peekLast();
        return index == null ? 0 : index;
    }

    @Override
    public void close() throws IOException {
        source.close();
    }

}

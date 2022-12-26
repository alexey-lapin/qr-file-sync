package com.github.alexeylapin.qrsync;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.channels.Channels;
import java.nio.channels.SeekableByteChannel;
import java.util.ArrayDeque;
import java.util.Deque;

public class SeekableByteChannelInputStream extends FilterInputStream {

    private final Deque<Long> stack;
    private final SeekableByteChannel byteChannel;

    public SeekableByteChannelInputStream(SeekableByteChannel byteChannel) {
        super(Channels.newInputStream(byteChannel));
        this.stack = new ArrayDeque<>();
        this.byteChannel = byteChannel;
    }

    @Override
    public boolean markSupported() {
        return true;
    }

    @Override
    public void mark(int readlimit) {
        try {
            Long position = byteChannel.position();
            if (position != 0 && !position.equals(stack.peekLast())) {
                stack.addLast(position);
            }
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    @Override
    public void reset() throws IOException {
        Long position = stack.pollLast();
        if (position == null) {
            byteChannel.position(0);
        } else {
            byteChannel.position(position);
        }
    }

}

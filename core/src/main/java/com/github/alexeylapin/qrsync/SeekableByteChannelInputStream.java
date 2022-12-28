package com.github.alexeylapin.qrsync;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.channels.Channels;
import java.nio.channels.SeekableByteChannel;
import java.util.ArrayDeque;
import java.util.Deque;

public class SeekableByteChannelInputStream extends InputStream {

    private final Deque<Long> stack;
    private final InputStream delegate;
    private final SeekableByteChannel byteChannel;

    public SeekableByteChannelInputStream(SeekableByteChannel byteChannel) {
        this.delegate = Channels.newInputStream(byteChannel);
        this.byteChannel = byteChannel;
        this.stack = new ArrayDeque<>();
    }

    @Override
    public int read() throws IOException {
        return delegate.read();
    }

    public void savePosition() {
        try {
            Long position = byteChannel.position();
            if (position != 0 && !position.equals(stack.peekLast())) {
                stack.addLast(position);
            }
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    public void restorePosition() throws IOException {
        Long position = stack.pollLast();
        if (position == null) {
            byteChannel.position(0);
        } else {
            byteChannel.position(position);
        }
    }

    public void rewind() throws IOException {
        Long position = stack.peekLast();
        if (position == null) {
            byteChannel.position(0);
        } else {
            byteChannel.position(position);
        }
    }

}

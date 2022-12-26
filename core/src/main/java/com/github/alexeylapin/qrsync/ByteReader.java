package com.github.alexeylapin.qrsync;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ByteReader implements Closeable {

    private InputStream source;

    public ByteReader(Path path) throws IOException {
        this.source = new SeekableByteChannelInputStream(Files.newByteChannel(path));
    }

    public boolean next() {
        source.mark(0);
        return false;
    }

    public boolean prev() throws IOException {
        source.reset();
        return false;
    }

    public Batch readBatch() throws IOException {
        source.read();
        return null;
    }

    @Override
    public void close() throws IOException {
        source.close();
    }

}

package com.github.alexeylapin.qrsync;

import java.io.Closeable;
import java.io.IOException;

public interface ByteReader extends Closeable {

    boolean next() throws IOException;

    boolean prev() throws IOException;

    Batch readBatch() throws IOException;

}

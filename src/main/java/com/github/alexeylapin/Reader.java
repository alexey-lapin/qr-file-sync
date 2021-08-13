package com.github.alexeylapin;

import java.io.Closeable;

public interface Reader extends Closeable {

    Batch read();

}

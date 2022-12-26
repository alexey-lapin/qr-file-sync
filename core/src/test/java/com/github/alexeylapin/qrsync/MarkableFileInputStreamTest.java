package com.github.alexeylapin.qrsync;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;

class MarkableFileInputStreamTest {

    @Test
    void name() throws Exception {
        new MarkableFileInputStream(new FileInputStream(""));
    }

}
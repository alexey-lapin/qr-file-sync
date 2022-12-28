package com.github.alexeylapin.qrsync;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;

class SeekableByteChannelInputStreamTest extends VirtualFileSystemSupport {

    @Test
    void name() throws IOException {
        Path path = fileSystem.getPath("file.bin");
        Files.write(path, new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9});

        SeekableByteChannel byteChannel = Files.newByteChannel(path);
        try (SeekableByteChannelInputStream inputStream = new SeekableByteChannelInputStream(byteChannel)) {
            inputStream.mark(0);
            System.out.println(inputStream.read());
            System.out.println(inputStream.read());
            System.out.println(inputStream.read());
            inputStream.restorePosition();
            System.out.println(inputStream.read());
            System.out.println(inputStream.read());
            System.out.println(inputStream.read());
            inputStream.mark(0);
            System.out.println(inputStream.read());
            System.out.println(inputStream.read());
            inputStream.mark(0);
            System.out.println(inputStream.read());
            inputStream.restorePosition();
            System.out.println(inputStream.read());
            inputStream.restorePosition();
            System.out.println(inputStream.read());
        }
    }

}
package com.github.alexeylapin.qrsync;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class SeekableByteChannelInputStreamTest extends VirtualFileSystemSupport {

    @Test
    void name() throws IOException {
        Path path = fileSystem.getPath("file.bin");
        byte[] fileBytes = new byte[10];
        fillSequenced(fileBytes);
        Files.write(path, fileBytes);

        try (SeekableByteChannelInputStream inputStream =
                     new SeekableByteChannelInputStream(Files.newByteChannel(path))) {
            assertThat(inputStream.savePosition()).isFalse();
            assertThat(inputStream.read()).isEqualTo(0);
            assertThat(inputStream.read()).isEqualTo(1);
            assertThat(inputStream.read()).isEqualTo(2);

            inputStream.rewind();
            assertThat(inputStream.read()).isEqualTo(0);
            assertThat(inputStream.read()).isEqualTo(1);
            assertThat(inputStream.read()).isEqualTo(2);

            assertThat(inputStream.savePosition()).isTrue();
            assertThat(inputStream.savePosition()).isFalse();
            assertThat(inputStream.read()).isEqualTo(3);
            assertThat(inputStream.read()).isEqualTo(4);

            inputStream.rewind();
            assertThat(inputStream.read()).isEqualTo(3);
            assertThat(inputStream.read()).isEqualTo(4);

            assertThat(inputStream.savePosition()).isTrue();
            assertThat(inputStream.read()).isEqualTo(5);

            inputStream.rewind();
            assertThat(inputStream.read()).isEqualTo(5);

            assertThat(inputStream.restorePosition()).isTrue();
            assertThat(inputStream.read()).isEqualTo(5);

            assertThat(inputStream.restorePosition()).isTrue();
            assertThat(inputStream.read()).isEqualTo(3);

            assertThat(inputStream.restorePosition()).isTrue();
            assertThat(inputStream.read()).isEqualTo(0);

            assertThat(inputStream.restorePosition()).isTrue();
            assertThat(inputStream.restorePosition()).isFalse();
        }
    }

}
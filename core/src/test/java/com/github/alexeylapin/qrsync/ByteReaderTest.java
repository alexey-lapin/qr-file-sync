package com.github.alexeylapin.qrsync;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class ByteReaderTest extends VirtualFileSystemSupport {

    @Test
    void name() throws Exception {
        Path path = fileSystem.getPath("file.bin");
        byte[] fileBytes = new byte[20];
        fillSequenced(fileBytes);
        Files.write(path, fileBytes);

        try (ByteReader byteReader = new DefaultByteReader(path, i -> new byte[2], 3)) {
            Batch batch1 = byteReader.readBatch();
            assertThat(batch1.getChunks().size()).isEqualTo(3);
            assertThat(batch1.getChunks().get(0).getBytes()).containsExactly(0, 1);
            assertThat(batch1.getChunks().get(1).getBytes()).containsExactly(2, 3);
            assertThat(batch1.getChunks().get(2).getBytes()).containsExactly(4, 5);

            Batch batch2 = byteReader.readBatch();
            assertThat(batch2.getChunks().size()).isEqualTo(3);
            assertThat(batch2.getChunks().get(0).getBytes()).containsExactly(0, 1);
            assertThat(batch2.getChunks().get(1).getBytes()).containsExactly(2, 3);
            assertThat(batch2.getChunks().get(2).getBytes()).containsExactly(4, 5);

            byteReader.next();

            Batch batch3 = byteReader.readBatch();
            assertThat(batch3.getChunks().size()).isEqualTo(3);
            assertThat(batch3.getChunks().get(0).getBytes()).containsExactly(6, 7);
            assertThat(batch3.getChunks().get(1).getBytes()).containsExactly(8, 9);
            assertThat(batch3.getChunks().get(2).getBytes()).containsExactly(10, 11);

            Batch batch4 = byteReader.readBatch();
            assertThat(batch4.getChunks().size()).isEqualTo(3);
            assertThat(batch4.getChunks().get(0).getBytes()).containsExactly(6, 7);
            assertThat(batch4.getChunks().get(1).getBytes()).containsExactly(8, 9);
            assertThat(batch4.getChunks().get(2).getBytes()).containsExactly(10, 11);

            byteReader.prev();

            Batch batch5 = byteReader.readBatch();
            assertThat(batch5.getChunks().size()).isEqualTo(3);
            assertThat(batch5.getChunks().get(0).getBytes()).containsExactly(0, 1);
            assertThat(batch5.getChunks().get(1).getBytes()).containsExactly(2, 3);
            assertThat(batch5.getChunks().get(2).getBytes()).containsExactly(4, 5);

        }

    }

}
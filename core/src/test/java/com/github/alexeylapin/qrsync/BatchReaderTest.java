package com.github.alexeylapin.qrsync;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class BatchReaderTest extends VirtualFileSystemSupport {

    @Test
    void name() throws Exception {
        Path path = fileSystem.getPath("file.bin");
        byte[] fileBytes = new byte[21];
        fillSequenced(fileBytes);
        Files.write(path, fileBytes);

        try (BatchReader batchReader = new DefaultBatchReader(path, i -> new byte[2], 3)) {
            Batch batch1 = batchReader.readBatch();
            assertThat(batch1.getChunks().size()).isEqualTo(3);
            assertThat(batch1.getChunks().get(0).getIndex()).isEqualTo(0);
            assertThat(batch1.getChunks().get(0).getBytes()).containsExactly(0, 1);
            assertThat(batch1.getChunks().get(1).getIndex()).isEqualTo(1);
            assertThat(batch1.getChunks().get(1).getBytes()).containsExactly(2, 3);
            assertThat(batch1.getChunks().get(2).getIndex()).isEqualTo(2);
            assertThat(batch1.getChunks().get(2).getBytes()).containsExactly(4, 5);

            Batch batch2 = batchReader.readBatch();
            assertThat(batch2.getChunks().size()).isEqualTo(3);
            assertThat(batch2.getChunks().get(0).getIndex()).isEqualTo(0);
            assertThat(batch2.getChunks().get(0).getBytes()).containsExactly(0, 1);
            assertThat(batch2.getChunks().get(1).getIndex()).isEqualTo(1);
            assertThat(batch2.getChunks().get(1).getBytes()).containsExactly(2, 3);
            assertThat(batch2.getChunks().get(2).getIndex()).isEqualTo(2);
            assertThat(batch2.getChunks().get(2).getBytes()).containsExactly(4, 5);

            assertThat(batchReader.next()).isTrue();
            assertThat(batchReader.next()).isFalse();

            Batch batch3 = batchReader.readBatch();
            assertThat(batch3.getChunks().size()).isEqualTo(3);
            assertThat(batch3.getChunks().get(0).getIndex()).isEqualTo(3);
            assertThat(batch3.getChunks().get(0).getBytes()).containsExactly(6, 7);
            assertThat(batch3.getChunks().get(1).getIndex()).isEqualTo(4);
            assertThat(batch3.getChunks().get(1).getBytes()).containsExactly(8, 9);
            assertThat(batch3.getChunks().get(2).getIndex()).isEqualTo(5);
            assertThat(batch3.getChunks().get(2).getBytes()).containsExactly(10, 11);

            Batch batch4 = batchReader.readBatch();
            assertThat(batch4.getChunks().size()).isEqualTo(3);
            assertThat(batch4.getChunks().get(0).getIndex()).isEqualTo(3);
            assertThat(batch4.getChunks().get(0).getBytes()).containsExactly(6, 7);
            assertThat(batch4.getChunks().get(1).getIndex()).isEqualTo(4);
            assertThat(batch4.getChunks().get(1).getBytes()).containsExactly(8, 9);
            assertThat(batch4.getChunks().get(2).getIndex()).isEqualTo(5);
            assertThat(batch4.getChunks().get(2).getBytes()).containsExactly(10, 11);

            assertThat(batchReader.next()).isTrue();

            Batch batch5 = batchReader.readBatch();
            assertThat(batch5.getChunks().size()).isEqualTo(3);
            assertThat(batch5.getChunks().get(0).getIndex()).isEqualTo(6);
            assertThat(batch5.getChunks().get(0).getBytes()).containsExactly(12, 13);
            assertThat(batch5.getChunks().get(1).getIndex()).isEqualTo(7);
            assertThat(batch5.getChunks().get(1).getBytes()).containsExactly(14, 15);
            assertThat(batch5.getChunks().get(2).getIndex()).isEqualTo(8);
            assertThat(batch5.getChunks().get(2).getBytes()).containsExactly(16, 17);

            assertThat(batchReader.prev()).isTrue();

            Batch batch6 = batchReader.readBatch();
            assertThat(batch6.getChunks().size()).isEqualTo(3);
            assertThat(batch6.getChunks().get(0).getIndex()).isEqualTo(3);
            assertThat(batch6.getChunks().get(0).getBytes()).containsExactly(6, 7);
            assertThat(batch6.getChunks().get(1).getIndex()).isEqualTo(4);
            assertThat(batch6.getChunks().get(1).getBytes()).containsExactly(8, 9);
            assertThat(batch6.getChunks().get(2).getIndex()).isEqualTo(5);
            assertThat(batch6.getChunks().get(2).getBytes()).containsExactly(10, 11);

            assertThat(batchReader.prev()).isTrue();
            assertThat(batchReader.prev()).isFalse();

            Batch batch7 = batchReader.readBatch();
            assertThat(batch7.getChunks().size()).isEqualTo(3);
            assertThat(batch7.getChunks().get(0).getIndex()).isEqualTo(0);
            assertThat(batch7.getChunks().get(0).getBytes()).containsExactly(0, 1);
            assertThat(batch7.getChunks().get(1).getIndex()).isEqualTo(1);
            assertThat(batch7.getChunks().get(1).getBytes()).containsExactly(2, 3);
            assertThat(batch7.getChunks().get(2).getIndex()).isEqualTo(2);
            assertThat(batch7.getChunks().get(2).getBytes()).containsExactly(4, 5);

            batchReader.next();
            Batch batch8 = batchReader.readBatch();

            batchReader.next();
            Batch batch9 = batchReader.readBatch();

            batchReader.next();
            Batch batch10 = batchReader.readBatch();
            assertThat(batch10.getChunks().size()).isEqualTo(2);
            assertThat(batch10.getChunks().get(0).getIndex()).isEqualTo(9);
            assertThat(batch10.getChunks().get(0).getBytes()).containsExactly(18, 19);
            assertThat(batch10.getChunks().get(1).getIndex()).isEqualTo(10);
            assertThat(batch10.getChunks().get(1).getBytes()).containsExactly(20);
        }

    }

}
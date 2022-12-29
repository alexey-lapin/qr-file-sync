package com.github.alexeylapin.qrsync;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ThreadLocalRandom;

public class EncodeTest extends VirtualFileSystemSupport {

    @Test
    void name() throws Exception {
        Path path = fileSystem.getPath("file.bin");
        byte[] bytes = new byte[10000];
        ThreadLocalRandom.current().nextBytes(bytes);
        Files.write(path, bytes);

        DataEncoder dataEncoder = new Base32DataEncoder();

        Base32ByteArrayAllocator allocator = new Base32ByteArrayAllocator(2685);
        try (DefaultBatchReader reader = new DefaultBatchReader(path, allocator, 5)) {
            Batch batch = reader.readBatch();
            for (Chunk chunk : batch.getChunks()) {
                String data = dataEncoder.encode(chunk.getBytes());
                ChunkMarker marker = new ChunkMarker(chunk.getIndex(), data);
                String render = marker.render();
                System.out.println(render.length() + " " + render);
            }
        }
    }

}

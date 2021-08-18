package com.github.alexeylapin;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultReader implements Reader {

    private final DataEncoder dataEncoder;
    private final ImageEncoder imageEncoder;

    private final File file;
    private final InputStream source;
    private final int batch;

    private int chunkIndex = 0;
    private int batchIndex = 0;
    private boolean finished = false;

    public DefaultReader(ImageEncoder imageEncoder, DataEncoder dataEncoder, File file, int batch) {
        try {
            this.imageEncoder = imageEncoder;
            this.dataEncoder = dataEncoder;
            this.file = file;
            this.source = new BufferedInputStream(new FileInputStream(file));
            this.batch = batch;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Batch read() {
        if (finished) {
            return null;
        }
        List<BufferedImage> images = new ArrayList<>();
        int batchStartIndex = chunkIndex;
        int count = -1;
        while (images.size() < batch) {
            Chunk chunk = new Chunk(chunkIndex, null);
            int length = imageEncoder.getCapacity() - dataEncoder.calculateBytes(chunk.getPrefix());
            // TODO: maybe make header of fixed length and reuse buffer?
            byte[] bytes = new byte[length];
            try {
                count = source.read(bytes);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            if (count == -1) {
                finished = true;
                break;
            }
            // TODO: add padding
            if (count < length) {
                bytes = Arrays.copyOf(bytes, count);
            }

//            String payload = chunkIndex + Marker.DELIMITER + dataEncoder.encode(bytes);
            String payload = chunk.withData(dataEncoder.encode(bytes)).render();
            BufferedImage image = imageEncoder.encode(payload);
            images.add(image);
            chunkIndex++;
        }
        int batchEndIndex = chunkIndex - 1;

        String batchMarker = new BatchMarker(batchIndex, batchStartIndex, batchEndIndex).render();
        BufferedImage batchMarkerImage = imageEncoder.encode(batchMarker);
        images.add(batchMarkerImage);

        if (count == -1) {
            String transmissionMarker = new TransmissionMarker(batchEndIndex + 1, file.getName(), "?").render();
            BufferedImage transmissionMarkerImage = imageEncoder.encode(transmissionMarker);
            images.add(transmissionMarkerImage);
        }

        Batch batch = new Batch(batchIndex, batchStartIndex, batchEndIndex, images);
        batchIndex++;
        return batch;
    }

    @Override
    public void close() throws IOException {
        source.close();
    }

//    private int getPrefixLength() {
//        int prefixLength = (chunkIndex + ".").getBytes().length;
//        if (prefixLength % 5 == 0) {
//            return prefixLength;
//        }
//        return (prefixLength + 5) - (prefixLength % 5);
//    }

}

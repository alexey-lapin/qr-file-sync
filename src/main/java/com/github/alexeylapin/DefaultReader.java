package com.github.alexeylapin;

import org.apache.commons.codec.binary.Base32;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DefaultReader implements Reader {

    private final Base32 BASE32 = new Base32(true);
    private final QREncoder qrEncoder;

    private final File file;
    private final InputStream source;
    private final int batch;
    private int index = 0;
    private int batchIndex = 0;
    private int batchStartIndex = 0;
    private int batchEndIndex = 0;
    private boolean finished = false;

    public DefaultReader(QREncoder qrEncoder, File file, int batch) {
        try {
            this.qrEncoder = qrEncoder;
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
        batchStartIndex = index;
        int count = -1;
        while (images.size() < batch) {
            int length = 2685 - getPrefixLength();
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
            if (count < length) {
                bytes = Arrays.copyOf(bytes, count);
            }
            String string = index + "+" + BASE32.encodeToString(bytes).replaceAll("=", "+");
            BufferedImage image = qrEncoder.encode(string);
            images.add(image);
            index++;
        }
        batchEndIndex = index -1;
        String marker = "M+" + batchIndex + "+" + batchStartIndex + "+" + batchEndIndex;
        BufferedImage image = qrEncoder.encode(marker);
        images.add(image);
        if (count == -1) {
            marker = "T+" + batchEndIndex + "+" + file.getName();
            image = qrEncoder.encode(marker);
            images.add(image);
        }
        Batch batch = new Batch(batchIndex, batchStartIndex, batchEndIndex, images);
        batchIndex++;
        return batch;
    }

    @Override
    public void close() throws IOException {
        source.close();
    }

    private int getPrefixLength() {
        int prefixLength = (index + ".").getBytes().length;
        if (prefixLength % 5 == 0) {
            return prefixLength;
        }
        return (prefixLength + 5) - (prefixLength % 5);
    }

}

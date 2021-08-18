package com.github.alexeylapin;

import org.junit.jupiter.api.Test;

import java.io.File;

public class DefaultReaderTest {

    @Test
    void name() {
        File file = new File("screenshot.png");
        Batch batch;
        try (DefaultReader reader = new DefaultReader(new ZxingImageEncoder(), null, file, Integer.MAX_VALUE)) {
            batch = reader.read();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        System.out.println(batch.getImages().size());
    }

    @Test
    void name2() {
        File file = new File("screenshot.png");
        Batch batch;
        try (DefaultReader reader = new DefaultReader(new ZxingImageEncoder(), null, file, 20)) {
            batch = reader.read();
            System.out.println(batch.getImages().size());
            batch = reader.read();
            System.out.println(batch.getImages().size());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}

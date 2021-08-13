package com.github.alexeylapin;

import org.apache.commons.codec.binary.Base32;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class QRTest {

    // 4296 (2685) 2953 (2214)
    // 3391 (2115) 2331 (1746)
    // 2420 (1510) 1663 (1245)
    // 1852 (1155) 1273 (954)


    private static final int[] ALPHANUMERIC_TABLE = {
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  // 0x00-0x0f
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  // 0x10-0x1f
            36, -1, -1, -1, 37, 38, -1, -1, -1, -1, 39, 40, -1, 41, 42, 43,  // 0x20-0x2f
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 44, -1, -1, -1, -1, -1,  // 0x30-0x3f
            -1, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24,  // 0x40-0x4f
            25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, -1, -1, -1, -1, -1,  // 0x50-0x5f
    };

    @Test
    void name() {
        ZxingQREncoder encoder = new ZxingQREncoder();
        String string = getString("A", 4296);
        System.out.println(string.length());
        encoder.encode(string);
    }

    @Test
    void name1() {
        ZxingQREncoder encoder = new ZxingQREncoder();
        String string = getString("a", 2953);
        System.out.println(string.length());
        encoder.encode(string);
    }

    @Test
    void name11() {
        byte[] bytes = new byte[2685];
        ZxingQREncoder encoder = new ZxingQREncoder();
//        String string = getString("a", 2953);
        String string = new Base32().encodeToString(bytes);
        System.out.println(string.length());
        encoder.encode(string);
    }

    @Test
    void name12() throws Exception {
        byte[] bytes = new byte[2685];
        ZxingQREncoder encoder = new ZxingQREncoder(null, 50);
        String string = new Base32().encodeToString(bytes);
        BufferedImage image = encoder.encode(string);
        ImageIO.write(image, "png", new File("image.png"));
    }

    @Test
    void name2() {
        an();
    }

    private String getString(String string, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(string);
        }
        return sb.toString();
    }

    private void an() {
        for (int i = 0; i < ALPHANUMERIC_TABLE.length; i++) {
            int i1 = ALPHANUMERIC_TABLE[i];
            char c = (char) i;
            System.out.println(c + " " + i1);
        }
    }

}

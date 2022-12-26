package com.github.alexeylapin.qrsync;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class QRTest {

    // @formatter:off
    // The original table is defined in the table 5 of JISX0510:2004 (p.19).
    private static final int[] ALPHANUMERIC_TABLE = {
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  // 0x00-0x0f
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  // 0x10-0x1f
            36, -1, -1, -1, 37, 38, -1, -1, -1, -1, 39, 40, -1, 41, 42, 43,  // 0x20-0x2f
            0,   1,  2,  3,  4,  5,  6,  7,  8,  9, 44, -1, -1, -1, -1, -1,  // 0x30-0x3f
            -1, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24,  // 0x40-0x4f
            25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, -1, -1, -1, -1, -1,  // 0x50-0x5f
    };
    // @formatter:on

    /*
      Allowed chars:
             x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x
             x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x
            [ ]  x   x   x  [$] [%]  x   x   x   x  [*] [+]  x  [-] [.] [/]
            [0] [1] [2] [3] [4] [5] [6] [7] [8] [9] [:]  x   x   x   x   x
             x  [A] [B] [C] [D] [E] [F] [G] [H] [I] [J] [K] [L] [M] [N] [O]
            [P] [Q] [R] [S] [T] [U] [V] [W] [X] [Y] [Z]  x   x   x   x   x
     */

    @Test
    void name() {
        int pixels = 100;
        Map<EncodeHintType, String> hints = new HashMap<>();
        QRCodeWriter writer = new QRCodeWriter();
        for (int i = 1000; i < 5000; i++) {
            String text = getString("A", i);
            try {
                BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, pixels, pixels, hints);
            } catch (Exception ex) {
                System.out.println(i - 1);
                break;
            }
        }
    }

    @Test
    void name2() {
        an();
    }

    @Test
    void name3() {
        an2();
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
            if (i1 != -1) {
                System.out.println(c + " " + i1);
            }
        }
    }

    private void an2() {
        for (int i = 0; i < ALPHANUMERIC_TABLE.length; i++) {
            if (i > 0 && i % 16 == 0) {
                System.out.println();
            }
            int i1 = ALPHANUMERIC_TABLE[i];
            char c = (char) i;
            if (i1 != -1) {
                System.out.print("[" + c + "] ");
            } else {
                System.out.print(" x  ");
            }
        }
        System.out.println();
    }

}

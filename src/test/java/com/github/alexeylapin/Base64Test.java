package com.github.alexeylapin;

import org.apache.commons.codec.binary.Base32;
import org.junit.jupiter.api.Test;

import java.util.Base64;

public class Base64Test {

    @Test
    void name() {
        // 3 bytes -> 4 chars
        byte[] src = {1, 1, 1, 2, 2, 2};
        String s = Base64.getEncoder().encodeToString(src);
        System.out.println(s);
    }

    @Test
    void name2() {
        int next = next(5, 3);
        System.out.println(next);
    }

    @Test
    void name3() {
        // 5 bytes -> 8 chars
        byte[] src = {1, 1, 1, 1, 1, 1};
        String s = new Base32().encodeToString(src);
        System.out.println(s);
    }

    //    int c2 = (a + b) - (a % b);
    int next(int a, int b) {
        return (a + b) - (a % b);
    }

}

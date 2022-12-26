package com.github.alexeylapin.qrsync;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Arrays;

public class IntTest {

    @Test
    void name() {
        System.out.println(Integer.toString(35, 36));
        System.out.println(Integer.toString(100, 36));
        System.out.println(Integer.toString(1_000, 36));
        System.out.println(Integer.toString(10_000, 36));
        System.out.println(Integer.toString(100_000, 36));
        System.out.println(Arrays.toString(BigInteger.valueOf(10000).toByteArray()));
    }

    @Test
    void name2() {
        String s = Integer.toString(10_000, 36).toUpperCase();
        int i = Integer.parseInt(s, 36);
        System.out.println(i);
    }

}

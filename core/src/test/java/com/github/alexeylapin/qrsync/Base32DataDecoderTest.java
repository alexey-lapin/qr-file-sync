package com.github.alexeylapin.qrsync;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Base32DataDecoderTest {

    @Test
    void name() {
        Base32DataEncoder encoder = new Base32DataEncoder();
        Base32DataDecoder decoder = new Base32DataDecoder();

        byte[] bytes1 = new byte[0];
        assertThat(decoder.decode(encoder.encode(bytes1))).isEqualTo(bytes1);

        byte[] bytes2 = new byte[]{0, 1, 2, 3, 4};
        assertThat(decoder.decode(encoder.encode(bytes2))).isEqualTo(bytes2);

        byte[] bytes3 = new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        assertThat(decoder.decode(encoder.encode(bytes3))).isEqualTo(bytes3);
    }

}
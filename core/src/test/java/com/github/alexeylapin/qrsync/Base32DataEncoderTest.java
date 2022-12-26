package com.github.alexeylapin.qrsync;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Base32DataEncoderTest {

    @Test
    void name() {
        Base32DataEncoder encoder = new Base32DataEncoder();

        assertThat(encoder.calculateBytes("")).isEqualTo(0);
        assertThat(encoder.calculateBytes("A")).isEqualTo(5);
        assertThat(encoder.calculateBytes("AA")).isEqualTo(5);
        assertThat(encoder.calculateBytes("AAA")).isEqualTo(5);
        assertThat(encoder.calculateBytes("AAAA")).isEqualTo(5);
        assertThat(encoder.calculateBytes("AAAAA")).isEqualTo(5);
        assertThat(encoder.calculateBytes("AAAAAA")).isEqualTo(10);
        assertThat(encoder.calculateBytes("AAAAAAA")).isEqualTo(10);
        assertThat(encoder.calculateBytes("AAAAAAAA")).isEqualTo(10);
        assertThat(encoder.calculateBytes("AAAAAAAAA")).isEqualTo(10);
        assertThat(encoder.calculateBytes("AAAAAAAAAA")).isEqualTo(10);
        assertThat(encoder.calculateBytes("AAAAAAAAAAA")).isEqualTo(15);
    }

    @Test
    void name2() {
        Base32DataEncoder encoder = new Base32DataEncoder();

        assertThat(encoder.encode(new byte[0])).isEqualTo("");
        assertThat(encoder.encode(new byte[]{0})).isEqualTo("00++++++");
        assertThat(encoder.encode(new byte[]{127})).isEqualTo("FS++++++");
        assertThat(encoder.encode(new byte[]{0, 0})).isEqualTo("0000++++");
        assertThat(encoder.encode(new byte[]{127, 127})).isEqualTo("FTVG++++");
        assertThat(encoder.encode(new byte[]{0, 0, 0})).isEqualTo("00000+++");
        assertThat(encoder.encode(new byte[]{0, 0, 127})).isEqualTo("0007U+++");
        assertThat(encoder.encode(new byte[]{0, 0, 0, 0})).isEqualTo("0000000+");
        assertThat(encoder.encode(new byte[]{0, 0, 0, 0, 0})).isEqualTo("00000000");
        assertThat(encoder.encode(new byte[]{127, 127, 127, 127, 127})).isEqualTo("FTVNUVRV");
        assertThat(encoder.encode(new byte[]{0, 0, 0, 0, 0, 0})).isEqualTo("0000000000++++++");
    }

}
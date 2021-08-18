package com.github.alexeylapin;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BatchMarkerTest {

    @Test
    void name() {
        BatchMarker.fromString("");
    }

    @Test
    void name2() {
        BatchMarker marker = BatchMarker.fromString("B+12+56+53");

        assertThat(marker.getIndex()).isEqualTo(12);
        assertThat(marker.getStart()).isEqualTo(56);
        assertThat(marker.getEnd()).isEqualTo(53);
    }

}
package com.github.alexeylapin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;

@AllArgsConstructor
@Getter
public class Chunk implements Marker {

    private final int index;
    @With
    private final String data;

    public String getPrefix() {
        return index + DELIMITER;
    }

    @Override
    public String render() {
        return getPrefix() + data;
    }

}

package com.github.alexeylapin;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TransmissionMarker implements Marker {

    public static final String MARKER_TRANSMISSION = "T";

    private final int parts;
    private final String name;
    private final String hash;

    @Override
    public String render() {
        return new StringBuilder()
                .append(MARKER_TRANSMISSION)
                .append(DELIMITER)
                .append(parts)
                .append(DELIMITER)
                .append(name)
                .append(DELIMITER)
                .append(hash)
                .toString();
    }

    public static TransmissionMarker fromString(String string) {
        String[] parts = string.split("\\" + DELIMITER);
        if (parts.length < 4 || !MARKER_TRANSMISSION.equals(parts[0])) {
            throw new RuntimeException("failed to parse " + TransmissionMarker.class.getSimpleName());
        }
        return new TransmissionMarker(Integer.parseInt(parts[1]), parts[2], parts[3]);
    }

}

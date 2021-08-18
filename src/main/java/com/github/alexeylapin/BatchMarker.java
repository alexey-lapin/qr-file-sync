package com.github.alexeylapin;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BatchMarker implements Marker {

    public static final String MARKER_BATCH = "B";

    private final int index;
    private final int start;
    private final int end;

    @Override
    public String render() {
        return new StringBuilder()
                .append(MARKER_BATCH)
                .append(DELIMITER)
                .append(index)
                .append(DELIMITER)
                .append(start)
                .append(DELIMITER)
                .append(end)
                .toString();
    }

    public static BatchMarker fromString(String string) {
        String[] parts = string.split("\\" + DELIMITER);
        if (parts.length < 4 || !MARKER_BATCH.equals(parts[0])) {
            throw new RuntimeException("failed to parse " + BatchMarker.class.getSimpleName());
        }
        return new BatchMarker(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
    }

}

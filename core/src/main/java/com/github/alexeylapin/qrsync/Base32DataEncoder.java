package com.github.alexeylapin.qrsync;

import org.apache.commons.codec.binary.Base32;

public class Base32DataEncoder implements DataEncoder {

    private final Base32 BASE32 = new Base32(true);

    @Override
    public int calculateBytes(String string) {
        int bytes = string.getBytes().length;
        if (bytes % 5 == 0) {
            return bytes;
        }
        return (bytes + 5) - (bytes % 5);
    }

    @Override
    public String encode(byte[] bytes) {
        return BASE32.encodeToString(bytes).replaceAll("=", "+");
    }

}

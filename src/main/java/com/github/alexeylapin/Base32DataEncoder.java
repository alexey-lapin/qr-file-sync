package com.github.alexeylapin;

import org.apache.commons.codec.binary.Base32;

public class Base32DataEncoder implements DataEncoder {

    private final Base32 BASE32 = new Base32(true);

    @Override
    public int calculateBytes(String string) {
        int prefixLength = string.getBytes().length;
        if (prefixLength % 5 == 0) {
            return prefixLength;
        }
        return (prefixLength + 5) - (prefixLength % 5);
    }

    @Override
    public String encode(byte[] bytes) {
        return BASE32.encodeToString(bytes).replaceAll("=", "+");
    }

}

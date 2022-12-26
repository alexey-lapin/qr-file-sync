package com.github.alexeylapin.qrsync;

import org.apache.commons.codec.binary.Base32;

public class Base32DataDecoder implements DataDecoder {

    private final Base32 BASE32 = new Base32(true);

    @Override
    public byte[] decode(String content) {
        return BASE32.decode(content.replaceAll("\\+", "="));
    }

}

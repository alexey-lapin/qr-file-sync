package com.github.alexeylapin.qrsync;

public interface DataEncoder {

    int calculateBytes(String string);

    String encode(byte[] bytes);

}

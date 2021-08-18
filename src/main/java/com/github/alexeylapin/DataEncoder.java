package com.github.alexeylapin;

public interface DataEncoder {

    int calculateBytes(String string);

    String encode(byte[] bytes);

}

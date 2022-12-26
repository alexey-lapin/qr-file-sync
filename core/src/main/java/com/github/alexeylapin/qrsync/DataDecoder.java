package com.github.alexeylapin.qrsync;

public interface DataDecoder {

    byte[] decode(String content);

}

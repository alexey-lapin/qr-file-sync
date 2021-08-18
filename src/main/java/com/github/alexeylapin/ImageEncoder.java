package com.github.alexeylapin;

import java.awt.image.BufferedImage;

public interface ImageEncoder {

    int getCapacity();

    BufferedImage encode(String string);

}

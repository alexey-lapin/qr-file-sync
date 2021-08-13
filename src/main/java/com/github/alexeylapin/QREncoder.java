package com.github.alexeylapin;

import java.awt.image.BufferedImage;

public interface QREncoder {

    BufferedImage encode(String string);

}

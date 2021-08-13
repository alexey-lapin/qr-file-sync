package com.github.alexeylapin;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collection;

public interface Encoder {

    Collection<BufferedImage> encode(File file);

}

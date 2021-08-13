package com.github.alexeylapin;

import lombok.Value;

import java.awt.image.BufferedImage;
import java.util.List;

@Value
public class Batch {

    int index;
    int start;
    int end;
    List<BufferedImage> images;

}

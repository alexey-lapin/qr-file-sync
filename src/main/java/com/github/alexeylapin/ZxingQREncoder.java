package com.github.alexeylapin;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.awt.image.BufferedImage;
import java.util.Map;

import static java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment;

public class ZxingQREncoder implements QREncoder {

    private final Writer writer;
    private final Map<EncodeHintType, Object> hints;
    private final int pixels;

    public ZxingQREncoder() {
        this(new QRCodeWriter(), null, getLocalGraphicsEnvironment().getMaximumWindowBounds().height);
    }

    public ZxingQREncoder(Map<EncodeHintType, Object> hints, int pixels) {
        this(new QRCodeWriter(), hints, pixels);
    }

    public ZxingQREncoder(Writer writer, Map<EncodeHintType, Object> hints, int pixels) {
        this.writer = writer;
        this.hints = hints;
        this.pixels = pixels;
    }

    @Override
    public BufferedImage encode(String text) {
        BitMatrix bitMatrix;
        try {
            bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, pixels, pixels, hints);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

}

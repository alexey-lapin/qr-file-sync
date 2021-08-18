package com.github.alexeylapin;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Map;

import static java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment;

public class ZxingImageEncoder implements ImageEncoder {

    private final Writer writer;
    private final Map<EncodeHintType, Object> hints;
    private final int pixels;

    public ZxingImageEncoder() {
        this(new QRCodeWriter(), Collections.emptyMap(), getLocalGraphicsEnvironment().getMaximumWindowBounds().height);
    }

    public ZxingImageEncoder(Map<EncodeHintType, Object> hints, int pixels) {
        this(new QRCodeWriter(), hints, pixels);
    }

    public ZxingImageEncoder(Writer writer, Map<EncodeHintType, Object> hints, int pixels) {
        this.writer = writer;
        this.hints = hints;
        this.pixels = pixels;
    }

    @Override
    public int getCapacity() {
        // assume ALPHANUMERIC mode
        Object o = hints.get(EncodeHintType.ERROR_CORRECTION);
        if (o != null) {
            ErrorCorrectionLevel level = ErrorCorrectionLevel.valueOf(o.toString());
            switch (level) {
                case L: return 2685;
                case M: return 2115;
                case Q: return 1510;
                case H: return 1155;
            }
        }
        return 2685;
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

package com.github.alexeylapin;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EncoderImpl2 {

    private final Options options;
    private final File source;

    public EncoderImpl2(Options options, File source) {
        this.options = options;
        this.source = source;
    }

//    @Override
    public List<BufferedImage> encode(File file) {
        List<BufferedImage> list = new ArrayList<>();
        int index = 1;

        int size = 900;

        try (InputStream is = new BufferedInputStream(new FileInputStream(file))) {
            int read = 1;
            byte[] bytes = new byte[size];
            while (read > 0) {
                read = is.read(bytes);
                if (read == -1) {
                    break;
                }
                if (read < size) {
                    bytes = Arrays.copyOf(bytes, read);
                }
                String s = index + "." + Base64.getEncoder().encodeToString(bytes);
//                System.out.println(s.length());
                BufferedImage image = generateQRCodeImage(s);
                list.add(image);
                index++;
            }
            String s = "0." + index + "." + file.getName();
            BufferedImage image = generateQRCodeImage(s);
            list.add(0, image);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

    List<BufferedImage> next() {
        return null;
    }

    private BufferedImage generateQRCodeImage(String text) throws WriterException {
        int px = 700;
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, px, px, hints);
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        return bufferedImage;
    }

}

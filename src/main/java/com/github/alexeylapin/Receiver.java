package com.github.alexeylapin;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Receiver {

    public String fileName = "";

    public byte[] receive() throws Exception {
        Transparent transparent = new Transparent();

        int parts = Integer.MAX_VALUE;

        Map<Integer, byte[]> map = new TreeMap<>();
        Set<Integer> missing = new TreeSet<>();

        while (parts != map.size()) {
            long start = System.currentTimeMillis();

//            BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            BufferedImage image = new Robot().createScreenCapture(transparent.getBounds());
            String s = decodeQR(image);
            String message = "";
            if (s != null) {
                int i = s.indexOf('.');
                if (i < 0) {
                    System.out.println(s);
                    continue;
                }
                String partString = s.substring(0, i);
                int part = Integer.parseInt(partString);
                if (!map.containsKey(part)) {
                    if (part == 0) {
                        int i1 = s.indexOf('.', i + 1);
                        parts = Integer.parseInt(s.substring(i + 1, i1));
                        fileName = s.substring(i1 + 1);
                        map.put(0, new byte[0]);
                        for (int z =1; z< parts; z++) {
                            missing.add(z);
                        }
                        missing.removeAll(map.keySet());
                    } else {
                        byte[] decode = Base64.getDecoder().decode(s.substring(i + 1));
                        map.put(part, decode);
                        missing.remove(part);
                    }
                    float pers = ((float) missing.size()) / parts;
                    String miss = "";
                    if (pers <= 0.05) {
                        miss = " missing: " + missing;
                    }
                    transparent.setTitle("Receiver " + fileName + " [" + map.size() + "/" + (parts == Integer.MAX_VALUE ? "?" : parts) + "]" + miss);
                    message = ">> got " + part;
                } else {
//                    message = ">> has " + part;
                }
            } else {
//                message = ">> error";
            }
            if (!message.equals("")) {
                System.out.println("[" + map.size() + "] " + message + " spent: " + (System.currentTimeMillis() - start));
            }
            Thread.sleep(50);
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        for (byte[] chunk : map.values()) {
            byteArrayOutputStream.write(chunk);
        }
        return byteArrayOutputStream.toByteArray();
    }

    private String decodeQR(BufferedImage bufferedImage) {
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        try {
            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();
        } catch (NotFoundException e) {
//            System.out.println("There is no QR code in the image");
            return null;
        }
    }


}

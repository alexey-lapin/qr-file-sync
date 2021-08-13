package com.github.alexeylapin;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import org.apache.commons.codec.binary.Base32;

import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Receiver2 {

    private int parts = Integer.MAX_VALUE;
    public String fileName = "";

    private Integer batchIndex;
    private Integer batchStartIndex;
    private Integer batchEndIndex;

    public byte[] receive() throws Exception {
        Transparent transparent = new Transparent();

//        int parts = Integer.MAX_VALUE;
        boolean finished = false;

        Map<Integer, byte[]> map = new TreeMap<>();
        Set<Integer> missing = new TreeSet<>();

        while (parts != map.size()) {
            long start = System.currentTimeMillis();

//            BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            BufferedImage image = new Robot().createScreenCapture(transparent.getBounds());
            String s = decodeQR(image);
            String message = "";
            if (s != null) {
                int i = s.indexOf('+');
                if (i < 0) {
//                    System.out.println(s);
                    continue;
                }
                String partString = s.substring(0, i);
                if ("T".equals(partString)) {
                    String[] split = s.split("\\+");
                    parts = Integer.parseInt(split[1]) + 1;
                    fileName = split[2];
                } else if ("M".equals(partString)) {
                    String[] split = s.split("\\+");
                    int index = Integer.parseInt(split[1]);
                    if (!Objects.equals(batchIndex, index)) {
                        this.batchIndex = index;
                        batchStartIndex = Integer.parseInt(split[2]);
                        batchEndIndex = Integer.parseInt(split[3]);
                        for (int z = batchStartIndex; z <= batchEndIndex; z++) {
                            missing.add(z);
                        }
                        missing.removeAll(map.keySet());
                    }

                } else {
                    int part = Integer.parseInt(partString);
                    if (!map.containsKey(part)) {

                        String substring = s.substring(i + 1).replaceAll("\\+", "=");
                        byte[] decode = new Base32(true).decode(substring);
                        map.put(part, decode);
                        missing.remove(part);

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

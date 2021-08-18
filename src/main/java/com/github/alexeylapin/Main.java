package com.github.alexeylapin;

import com.google.zxing.EncodeHintType;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception {
        if ("send".equals(args[0])) {
            File file = new File(args[1]);
            int px = Integer.parseInt(args[2]);
            int size = Integer.parseInt(args[3]);
            int batch = Integer.parseInt(args[4]);

            Map<EncodeHintType, Object> hints = new HashMap<>();
//            hints.put(EncodeHintType.MARGIN, 0);
            ZxingImageEncoder imageEncoder = new ZxingImageEncoder(hints, 700);
            DefaultReader reader = new DefaultReader(imageEncoder, new Base32DataEncoder(), file, batch);
            new Renderer(reader::read).render();
        } else if ("receive".equals(args[0])) {
            Receiver receiver = new Receiver();
            byte[] receive = receiver.receive();
            System.out.println("received");
            try (OutputStream os = new BufferedOutputStream(new FileOutputStream(receiver.fileName))) {
                os.write(receive);
            }
        }
    }

}

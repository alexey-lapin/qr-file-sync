package com.github.alexeylapin;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class Main {

    public static void main(String[] args) throws Exception {
        if ("send".equals(args[0])) {
            File file = new File(args[1]);
            int px = Integer.parseInt(args[2]);
            int size = Integer.parseInt(args[3]);
            int batch = Integer.parseInt(args[4]);
//            List<BufferedImage> encode = new EncoderImpl(px, size).encode(new File(args[1]));
//            new Renderer(encode).circulate();
            DefaultReader reader = new DefaultReader(new ZxingQREncoder(), file, batch);
            new Renderer2(reader::read).render();
        } else if ("receive".equals(args[0])) {
            Receiver2 receiver = new Receiver2();
            byte[] receive = receiver.receive();
            System.out.println("received");
            try (OutputStream os = new BufferedOutputStream(new FileOutputStream(receiver.fileName))) {
                os.write(receive);
            }
        }
    }

}

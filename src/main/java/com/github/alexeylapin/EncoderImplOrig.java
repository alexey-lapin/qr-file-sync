package com.github.alexeylapin;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EncoderImplOrig {

    void encode() throws Exception {
        String text = getString("0");
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        int height1 = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
        System.out.println(height);
        System.out.println(height1);
        BufferedImage image = generateQRCodeImage(text, height1, height1, null);
        JLabel jLabel = new JLabel();
        show(jLabel, image);

        for (int i = 1; i < 5; i++) {
            Thread.sleep(1000);
            text = getString(String.valueOf(i));
            image = generateQRCodeImage(text, height1, height1, null);
            jLabel.setIcon(new ImageIcon(image));

        }
        screenshot();
    }

    private static String getString(String t) {
        String text = "";
        for (int i = 0; i < 2000; i++) {
            text += t;
        }
        return text;
    }

    private static BufferedImage generateQRCodeImage(String text, int width, int height, String filePath)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

//        Path path = FileSystems.getDefault().getPath(filePath);
//        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        return bufferedImage;
    }

    private static void show(JLabel jLabel, BufferedImage image) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame editorFrame = new JFrame("Image Demo");
                editorFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

//                BufferedImage image = null;
//                try {
//                    image = ImageIO.read(new File(filename));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    System.exit(1);
//                }
                ImageIcon imageIcon = new ImageIcon(image);
//                JLabel jLabel = new JLabel();
                jLabel.setIcon(imageIcon);
                editorFrame.getContentPane().add(jLabel, BorderLayout.CENTER);

                editorFrame.pack();
                editorFrame.setLocationRelativeTo(null);
                editorFrame.setVisible(true);
            }
        });
    }

    private static void screenshot() throws Exception {
        BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        ImageIO.write(image, "png", new File("screenshot.png"));
    }

}

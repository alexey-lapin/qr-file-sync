package com.github.alexeylapin;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.util.List;

public class Renderer {

    private final JFrame jFrame = new JFrame("Demo");
    private final JLabel jLabel = new JLabel();
    private final JTextField field = new JTextField(10);
    private final List<BufferedImage> images;

    public Renderer(List<BufferedImage> images) {
        this.images = images;
        render();
    }

    public void render() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JPanel panel = new JPanel();
                JButton button = new JButton();
                panel.add(jLabel);
                panel.add(field);
                panel.add(button);
                jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//                jFrame.getContentPane().add(jLabel, BorderLayout.CENTER);
//                jFrame.getContentPane().add(field, BorderLayout.WEST);
                jFrame.getContentPane().add(panel, BorderLayout.CENTER);
                jFrame.pack();
                jFrame.setLocationRelativeTo(null);
                jFrame.setVisible(true);
            }
        });
    }

    public void circulate() {
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//
//            }
//        });
        try {
            while (true) {
                int index = 0;

                int i = getIndex();
                if (i > -1) {
                    jFrame.setTitle("Renderer " + i + "/" + images.size());
                    jLabel.setIcon(new ImageIcon(images.get(i)));
                    Thread.sleep(100);
                } else {
                    for (BufferedImage image : images) {
                        if (getIndex() > -1) {
                            break;
                        }
                        jFrame.setTitle("Renderer " + index + "/" + images.size());
                        jLabel.setIcon(new ImageIcon(image));
                        index++;
                        Thread.sleep(100);
                    }
                }
            }
        } catch (Exception ex) {

        }
    }

    int getIndex() {
        String text = field.getText();
        int i = -1;
        if (text != null && !text.isEmpty()) {
            i = Integer.parseInt(text);
        }
        if (i >= images.size()) {
            i = -1;
        }
        return i;
    }


}

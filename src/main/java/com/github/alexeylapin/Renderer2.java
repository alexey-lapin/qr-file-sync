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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class Renderer2 {

    private final JFrame jFrame = new JFrame("Demo");
    private final JLabel jLabel = new JLabel();
    private final JTextField field = new JTextField(10);

    private final Supplier<Batch> supplier;
    private AtomicBoolean next = new AtomicBoolean(true);

    private Batch batch;
//    private List<BufferedImage> images;

    public Renderer2(Supplier<Batch> supplier) {
        this.supplier = supplier;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JPanel panel = new JPanel();
                JButton button = new JButton("Next");
                button.addActionListener((event) -> {
                    next.set(true);
                });
                panel.add(jLabel);
                panel.add(field);
                panel.add(button);
                jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                jFrame.getContentPane().add(panel, BorderLayout.CENTER);
                jFrame.pack();
                jFrame.setLocationRelativeTo(null);
                jFrame.setVisible(true);
            }
        });
    }

    public void render() {
        try {
            while (true) {
                if (next.compareAndSet(true, false)) {
                    batch = supplier.get();
                }
                int index = 0;
//                for (BufferedImage image : batch.getImages()) {
//                    jFrame.setTitle("Renderer " + (batch.getStart() + index) + "[" + batch.getStart() + ":" + batch.getEnd() + "]");
//                    jLabel.setIcon(new ImageIcon(image));
//                    index++;
//                    Thread.sleep(100);
//                }
                int i = getIndex();
                if (i > -1) {
                    jFrame.setTitle("Renderer " + (batch.getStart() + index) + "[" + batch.getStart() + ":" + batch.getEnd() + "]");
                    jLabel.setIcon(new ImageIcon(batch.getImages().get(i)));
                    Thread.sleep(100);
                } else {
                    for (BufferedImage image : batch.getImages()) {
                        if (getIndex() > -1) {
                            break;
                        }
                        jFrame.setTitle("Renderer " + (batch.getStart() + index) + "[" + batch.getStart() + ":" + batch.getEnd() + "]");
                        jLabel.setIcon(new ImageIcon(image));
                        index++;
                        Thread.sleep(100);
                    }
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    int getIndex() {
        String text = field.getText();
        int i = -1;
        if (text != null && !text.isEmpty()) {
            i = Integer.parseInt(text);
        }
        if (i < batch.getStart() || i >= batch.getStart() + batch.getImages().size()) {
            i = -1;
        }
        return i - batch.getStart();
    }

}

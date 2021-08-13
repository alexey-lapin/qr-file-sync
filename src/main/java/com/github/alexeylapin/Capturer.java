package com.github.alexeylapin;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class Capturer {

    JFrame frame = new JFrame("Receiver");

//    void frame() {
//
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                jFrame.setUndecorated(true);
//                jFrame.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.5f));
//                jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//                JLabel comp = new JLabel();
////                comp.setSize(100, 100);
////                jFrame.getContentPane().add(comp, BorderLayout.CENTER);
//                jFrame.setSize(200, 200);
////                jFrame.pack();
//                jFrame.setLocationRelativeTo(null);
//                jFrame.setVisible(true);
//            }
//        });
//    }

//    public void createAnsShowGui() {
//        ComponentResizer cr = new ComponentResizer();
//        cr.setMinimumSize(new Dimension(300, 300));
//        cr.setMaximumSize(new Dimension(800, 600));
//        cr.registerComponent(frame);
//        cr.setSnapSize(new Dimension(10, 10));
//        frame.setUndecorated(true);
//        frame.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.5f));
//        frame.add(new OutsidePanel());
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//    }

//    class MainPanel extends JPanel {
//
//        public MainPanel() {
////            setBackground(Color.gray);
//            setBackground(new Color(1.0f, 1.0f, 1.0f, 0.5f));
//        }
//
//        @Override
//        public Dimension getPreferredSize() {
//            return new Dimension(400, 400);
//        }
//    }

//    class BorderPanel extends JPanel {
//
//        private JLabel label;
//        int pX, pY;
//
//        public BorderPanel() {
//            label = new JLabel(" X ");
//            label.setOpaque(true);
//            label.setBackground(Color.RED);
//            label.setForeground(Color.WHITE);
//
////            setBackground(Color.black);
//            setBackground(new Color(1.0f, 1.0f, 1.0f, 0.5f));
//            setLayout(new FlowLayout(FlowLayout.RIGHT));
//
//            add(label);
//
//            label.addMouseListener(new MouseAdapter() {
//                public void mouseReleased(MouseEvent e) {
//                    System.exit(0);
//                }
//            });
//            addMouseListener(new MouseAdapter() {
//                public void mousePressed(MouseEvent me) {
//                    // Get x,y and store them
//                    pX = me.getX();
//                    pY = me.getY();
//
//                }
//
//                public void mouseDragged(MouseEvent me) {
//
//                    frame.setLocation(frame.getLocation().x + me.getX() - pX,
//                            frame.getLocation().y + me.getY() - pY);
//                }
//            });
//
//            addMouseMotionListener(new MouseMotionAdapter() {
//                public void mouseDragged(MouseEvent me) {
//
//                    frame.setLocation(frame.getLocation().x + me.getX() - pX,
//                            frame.getLocation().y + me.getY() - pY);
//                }
//            });
//        }
//    }

//    class OutsidePanel extends JPanel {
//
//        public OutsidePanel() {
//            setLayout(new BorderLayout());
//            add(new MainPanel(), BorderLayout.CENTER);
//            add(new BorderPanel(), BorderLayout.PAGE_START);
//            setBorder(new LineBorder(Color.BLACK, 5));
//        }
//    }

}

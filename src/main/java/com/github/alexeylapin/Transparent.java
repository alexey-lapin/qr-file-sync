package com.github.alexeylapin;

import javax.swing.JFrame;
import javax.swing.JRootPane;
import java.awt.Color;

class Transparent extends JFrame {

    public Transparent() {
        setTitle("Receiver");
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
        setBackground(new Color(0, 0, 0, 0.5f));
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

}

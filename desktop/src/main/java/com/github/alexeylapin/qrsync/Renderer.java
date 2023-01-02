package com.github.alexeylapin.qrsync;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Renderer {

    private final JFrame jFrame = new JFrame("Demo");
    private final JLabel imageContainer = new JLabel();
    private final JTextField field = new JTextField(10);

    public Renderer() {
        SwingUtilities.invokeLater(() -> {
            JPanel panel = new JPanel();
            panel.add(getSettingsPanel());
            panel.add(getImagePanel());

//            jFrame.addComponentListener(new ComponentAdapter() {
//                @Override
//                public void componentResized(ComponentEvent e) {
//                    System.out.println(e);
//                    int x = jFrame.getInsets().top + jFrame.getInsets().bottom;
//                    System.out.println(x);
//                    resize(String.valueOf(jFrame.getBounds().height - x));
//                }
//            });

            imageContainer.setPreferredSize(new Dimension(200, 200));
            imageContainer.setBorder(new LineBorder(Color.BLACK));

//            jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            jFrame.getContentPane().add(panel, BorderLayout.CENTER);
            jFrame.pack();
            jFrame.setLocationRelativeTo(null);
            jFrame.setVisible(true);
        });
    }

    private JPanel getSettingsPanel() {
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);

        JTextField comp = new JTextField("100", 10);
        comp.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                resize(comp.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                resize(comp.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                resize(comp.getText());
            }


        });
        settingsPanel.add(comp, gbc);

        gbc.gridy++;
        gbc.gridwidth = 2;

        settingsPanel.add(new JButton("q1"), gbc);

        gbc.gridy++;
        gbc.gridwidth = 2;

        settingsPanel.add(new JButton("q2888"), gbc);

        gbc.gridy++;
        gbc.gridwidth = 2;

        JButton button = new JButton("Next");
        button.addActionListener((event) -> {
//                next.set(true);
        });
        settingsPanel.add(button, gbc);

        gbc.gridy++;
        gbc.gridwidth = 2;

        settingsPanel.add(field, gbc);

        return settingsPanel;
    }

    private JPanel getImagePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);

        panel.add(imageContainer);
        return panel;
    }

    private void resize(String size) {
        try {
            int i = Integer.parseInt(size);
            SwingUtilities.invokeLater(() -> {
                imageContainer.setPreferredSize(new Dimension(i, i));
                jFrame.revalidate();
                jFrame.repaint();
//                jFrame.pack();
            });
        } catch (Exception ex) {
        }
    }

}

package com.wrh.meteo.draw.help;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CanvasPanel extends JPanel {

    private BufferedImage image;

    CanvasPanel(BufferedImage image) {
        this.image = image;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        try {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, image.getWidth(), image.getHeight());
            g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

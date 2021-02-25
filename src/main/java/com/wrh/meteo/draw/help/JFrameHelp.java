package com.wrh.meteo.draw.help;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class JFrameHelp {

    public static void showImageFrame(BufferedImage image) {
        JFrame frame = new JFrame("test");
        frame.getContentPane().add(new CanvasPanel(image));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(image.getWidth() + 20, image.getHeight() + 30);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        frame.setLocation((screenWidth - frame.getWidth()) >> 1, screenHeight > frame.getHeight() ? (screenHeight - frame.getHeight()) >> 1 : 0);
        frame.setVisible(true);
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

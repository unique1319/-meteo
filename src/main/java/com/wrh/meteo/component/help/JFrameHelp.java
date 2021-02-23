package com.wrh.meteo.component.help;

import com.wrh.meteo.component.quickhull.QuickHull;
import com.wrh.meteo.component.quickhull.datastructures.LinkedList;
import com.wrh.meteo.component.quickhull.datastructures.LinkedListNode;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
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

    public static void main(String[] args) {

        LinkedList points = new LinkedList();
        points.insert(new Point2D.Double(50, 50));
        points.insert(new Point2D.Double(360, 70));
        points.insert(new Point2D.Double(42, 293));
        points.insert(new Point2D.Double(150, 200));
        points.insert(new Point2D.Double(168, 258));
        points.insert(new Point2D.Double(260, 362));

        QuickHull quickHull = new QuickHull();
        LinkedList calcPointsSet = quickHull.useAlgorithm(points);

        int imageWidth = 400;
        int imageHeight = 400;
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, imageWidth, imageHeight);
        g.setColor(Color.BLACK);

        LinkedListNode node = calcPointsSet.getHead();
        while (node != null) {
            Point2D.Double point = node.getPoint();
            g.drawOval((int) point.x, (int) point.y, 8, 8);
            node = node.getNext();
        }
        g.dispose();
        showImageFrame(image);
    }

}

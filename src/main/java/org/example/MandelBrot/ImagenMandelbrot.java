package org.example.MandelBrot;

import javax.swing.*;
import java.awt.*;

class ImagenMandelbrot extends JPanel {
    private final int width, height;
    private final double x1, y1, x2, y2;
    private final int[] colors;

    public ImagenMandelbrot(int width, int height, double x1, double y1, double x2, double y2, int[] colors) {
        this.width = width;
        this.height = height;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.colors = colors;
    }

    @Override
    protected void paintComponent(Graphics g) {
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                g.setColor(new Color(colors[j * width + i]));
                g.fillRect(i, j, 1, 1);
            }
        }
    }
}

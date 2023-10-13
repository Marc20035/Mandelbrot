package org.example.MandelBrot;

import java.awt.*;
import java.util.concurrent.Callable;

class Colores implements Callable<int[]> {
    private final int start, end, width, height;
    private final double x1, y1, x2, y2;

    public Colores(int start, int end, int width, int height,
                   double x1, double y1,
                   double x2, double y2) {
        this.start = start;
        this.end = end;
        this.width = width;
        this.height = height;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public int[] call() {
        int[] colors = new int[(end - start) * width];

        for (int j = start; j < end; j++) {
            for (int i = 0; i < width; i++) {
                double x0 = x1 + i * (x2 - x1) / width;
                double y0 = y1 + j * (y2 - y1) / height;

                int iterations = mandelbrot(x0, y0);
                Color color = getColor(iterations);
                colors[(j - start) * width + i] = color.getRGB();
            }
        }

        return colors;
    }

    private static final int MAX_ITERATIONS = 255;

    private static Color getColor(int iterations) {
        if (iterations == MAX_ITERATIONS) {
            return Color.BLACK;
        } else {
            int red = (iterations % 8) * 32;
            int green = (iterations % 16) * 16;
            int blue = (iterations % 32) * 8;
            return new Color(red, green, blue);
        }
    }

    private static int mandelbrot(double real, double imag) {
        double r = real, i = imag, rnew, inew;

        for (int t = 0; t < MAX_ITERATIONS; t++) {
            if (r * r + i * i > 4.0) return t;

            rnew = r * r - i * i + real;
            inew = 2 * r * i + imag;

            r = rnew;
            i = inew;
        }
        return MAX_ITERATIONS;
    }
}

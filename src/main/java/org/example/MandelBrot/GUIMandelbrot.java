package org.example.MandelBrot;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class GUIMandelbrot {
    public static void mostrarMain() {

        JFrame frame = new JFrame("Mandelbrot Set");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(4, 1, 100, 1);
        JSpinner spinner = new JSpinner(spinnerModel);
        frame.add(spinner, BorderLayout.NORTH);

        JButton button = new JButton("Render");
        frame.add(button, BorderLayout.SOUTH);

        button.addActionListener(e -> {
            int workers = (Integer) spinner.getValue();
            int width = 800, height = 800;
            double x1 = -2.0, y1 = -2.0, x2 = 2.0, y2 = 2.0;

            ExecutorService executorService = Executors.newFixedThreadPool(workers);


            Future<int[]>[] futures = new Future[height];

            for (int j = 0; j < height; j++) {
                futures[j] = executorService.submit(new Colores(j, j + 1, width, height, x1, y1, x2, y2));
            }

            int[] colors = new int[width * height];

            for (int j = 0; j < height; j++) {
                try {
                    System.arraycopy(futures[j].get(), 0, colors, j * width, width);
                } catch (InterruptedException | ExecutionException ex) {
                    ex.printStackTrace();
                }
            }

            executorService.shutdown();

            ImagenMandelbrot panel = new ImagenMandelbrot(width, height, x1, y1, x2, y2, colors);
            frame.add(panel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();
        });

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setSize(new Dimension(800, 800));
    }
}

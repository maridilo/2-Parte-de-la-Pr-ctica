package Simulacion;

import javax.swing.*;
import java.awt.*;

    public class VisualizacionSimulacion extends JPanel {
        private static final int CELL_SIZE = 20;
        private int[][] matriz;

        public VisualizacionSimulacion(int[][] matriz) {
            this.matriz = matriz;
            setPreferredSize(new Dimension(matriz.length * CELL_SIZE, matriz[0].length * CELL_SIZE));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (int i = 0; i < matriz.length; i++) {
                for (int j = 0; j < matriz[i].length; j++) {
                    int bacterias = matriz[i][j];
                    g.setColor(getColor(bacterias));
                    g.fillRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }

        private Color getColor(int bacterias) {
            if (bacterias >= 20) {
                return Color.RED;
            } else if (bacterias >= 15) {
                return new Color(128, 0, 128); // Morado
            } else if (bacterias >= 10) {
                return Color.ORANGE;
            } else if (bacterias >= 5) {
                return Color.YELLOW;
            } else if (bacterias >= 1) {
                return Color.GREEN;
            } else {
                return Color.WHITE;
            }
        }

        public static void mostrar(int[][] matriz) {
            JFrame frame = new JFrame("Simulación de Bacterias");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new VisualizacionSimulacion(matriz));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    }

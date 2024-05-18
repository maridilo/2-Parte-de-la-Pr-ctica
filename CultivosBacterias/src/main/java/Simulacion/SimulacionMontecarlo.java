package Simulacion;

import java.util.Random;
import Datos.PoblacionBacterias;
import java.util.Random;

    public class SimulacionMontecarlo {
        private static final int SIZE = 20;
        private static final int MAX_COMIDA = 300000;
        private Random random = new Random();

        public void simular(PoblacionBacterias poblacion) {
            int[][][] matriz = new int[poblacion.getDuracionExperimento()][SIZE][SIZE];
            int[][][] comida = new int[poblacion.getDuracionExperimento()][SIZE][SIZE];

            // Inicializar matriz y comida
            inicializar(poblacion, matriz, comida);

            for (int dia = 0; dia < poblacion.getDuracionExperimento(); dia++) {
                for (int i = 0; i < SIZE; i++) {
                    for (int j = 0; j < SIZE; j++) {
                        int bacterias = matriz[dia][i][j];
                        for (int k = 0; k < bacterias; k++) {
                            simularBacteria(matriz, comida, dia, i, j);
                        }
                    }
                }
                // Sumar comida al día siguiente
                if (dia + 1 < poblacion.getDuracionExperimento()) {
                    acumularComida(poblacion, comida, dia);
                }
            }

            // Mostrar resultados
            mostrarResultados(matriz);
        }

        private void inicializar(PoblacionBacterias poblacion, int[][][] matriz, int[][][] comida) {
            int centro = SIZE / 2;
            int subCuadrado = 4;
            int cantidadBacteriasInicial = poblacion.getCantidadInicialBacterias();
            int bacteriasPorCelda = cantidadBacteriasInicial / (subCuadrado * subCuadrado);

            for (int i = centro - 2; i < centro + 2; i++) {
                for (int j = centro - 2; j < centro + 2; j++) {
                    matriz[0][i][j] = bacteriasPorCelda;
                }
            }

            int dosisInicial = poblacion.getDosisInicialAlimento();
            int dosisFinal = poblacion.getDosisFinalAlimento();
            int diasIncremento = poblacion.getIncrementarHastaDia();

            for (int dia = 0; dia < poblacion.getDuracionExperimento(); dia++) {
                int dosisDia = calcularDosis(dia, dosisInicial, dosisFinal, diasIncremento, poblacion.getPatronAlimento());
                int comidaPorCelda = dosisDia / (SIZE * SIZE);

                for (int i = 0; i < SIZE; i++) {
                    for (int j = 0; j < SIZE; j++) {
                        comida[dia][i][j] = comidaPorCelda;
                    }
                }
            }
        }

        private int calcularDosis(int dia, int dosisInicial, int dosisFinal, int diasIncremento, String patronAlimento) {
            switch (patronAlimento) {
                case "Constante":
                    return dosisInicial;
                case "Incremento lineal":
                    if (dia <= diasIncremento) {
                        return dosisInicial + (dosisFinal - dosisInicial) * dia / diasIncremento;
                    } else {
                        return dosisFinal;
                    }
                case "Constante alternada":
                    return (dia % 2 == 0) ? dosisInicial : 0;
                default:
                    return dosisInicial;
            }
        }

        private void simularBacteria(int[][][] matriz, int[][][] comida, int dia, int i, int j) {
            int comidaCelda = comida[dia][i][j];
            if (comidaCelda >= 100) {
                comidaCelda -= 20;
                comida[dia][i][j] = comidaCelda;
                int decision = random.nextInt(100);
                if (decision < 3) {
                    matriz[dia][i][j]--;
                } else if (decision >= 60) {
                    moverBacteria(matriz, dia, i, j, decision);
                }
            } else if (comidaCelda > 9) {
                comidaCelda -= 10;
                comida[dia][i][j] = comidaCelda;
                int decision = random.nextInt(100);
                if (decision < 6) {
                    matriz[dia][i][j]--;
                } else if (decision >= 20) {
                    moverBacteria(matriz, dia, i, j, decision);
                }
            } else {
                int decision = random.nextInt(100);
                if (decision < 20) {
                    matriz[dia][i][j]--;
                } else if (decision >= 60) {
                    moverBacteria(matriz, dia, i, j, decision);
                }
            }
        }

        private void moverBacteria(int[][][] matriz, int dia, int i, int j, int decision) {
            int[] dx = {-1, 0, 1, 0};
            int[] dy = {0, 1, 0, -1};

            int dir = (decision - 60) / 5;
            int ni = i + dx[dir];
            int nj = j + dy[dir];

            if (ni >= 0 && ni < SIZE && nj >= 0 && nj < SIZE) {
                matriz[dia][i][j]--;
                matriz[dia][ni][nj]++;
            }
        }

        private void acumularComida(PoblacionBacterias poblacion, int[][][] comida, int dia) {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    comida[dia + 1][i][j] += comida[dia][i][j];
                }
            }
        }

        private void mostrarResultados(int[][][] matriz) {
            for (int dia = 0; dia < matriz.length; dia++) {
                System.out.println("Día " + (dia + 1) + ":");
                for (int i = 0; i < SIZE; i++) {
                    for (int j = 0; j < SIZE; j++) {
                        System.out.print(matriz[dia][i][j] + "\t");
                    }
                    System.out.println();
                }
                System.out.println();
            }
        }
    }

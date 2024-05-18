package Datos;

import java.util.Date;
import java.io.Serializable;

public class PoblacionBacterias implements Serializable {
        private String nombre;
        private Date fechaInicio;
        private Date fechaFin;
        private int cantidadInicialBacterias;
        private double temperatura;
        private String condicionesLuz;
        private int dosisInicialAlimento;
        private int dosisFinalAlimento;
        private int incrementarHastaDia;
        private int[] dosisDiariaAlimento;
        private int[] comida;
        private String patronAlimento;

        public PoblacionBacterias(String nombre, Date fechaInicio, Date fechaFin, int cantidadInicialBacterias, double temperatura, String condicionesLuz, int comidaInicial, int comidaFinal, int diaComidaDisminuye, String patronAlimento) {
            this.nombre = nombre;
            this.fechaInicio = fechaInicio;
            this.fechaFin = fechaFin;
            this.cantidadInicialBacterias = cantidadInicialBacterias;
            this.temperatura = temperatura;
            this.condicionesLuz = condicionesLuz;
            this.dosisInicialAlimento = dosisInicialAlimento;
            this.incrementarHastaDia = incrementarHastaDia;
            this.dosisFinalAlimento = dosisFinalAlimento;
            this.dosisDiariaAlimento = calcularDosisDiariaAlimento();
            this.comida = calcularComida(comidaInicial, comidaFinal, diaComidaDisminuye);
            this.patronAlimento = this.patronAlimento;
        }

        private int[] calcularDosisDiariaAlimento() {
            int[] dosisDiariaAlimento = new int[incrementarHastaDia];
            int dosisDiaria = dosisInicialAlimento;
            for (int i = 0; i < incrementarHastaDia; i++) {
                dosisDiariaAlimento[i] = dosisDiaria;
                dosisDiaria += (dosisFinalAlimento - dosisInicialAlimento) / incrementarHastaDia;
            }
            return dosisDiariaAlimento;
        }

        private int[] calcularComida(int comidaInicial, int comidaFinal, int diaComidaDisminuye) {
            int[] comida = new int[30];
            int incrementoComida = (comidaFinal - comidaInicial) / diaComidaDisminuye;
            for (int i = 0; i < diaComidaDisminuye; i++) {
                comida[i] = comidaInicial + i * incrementoComida;
            }
            int decrementoComida = (comidaFinal - comidaInicial) / (30 - diaComidaDisminuye);
            for (int i = diaComidaDisminuye; i < 30; i++) {
                comida[i] = comidaFinal - (i - diaComidaDisminuye) * decrementoComida;
            }
            return comida;
        }

        // Getters y Setters

        public String getNombre() {
            return nombre;
        }

        public Date getFechaInicio() {
            return fechaInicio;
        }

        public Date getFechaFin() {
            return fechaFin;
        }

        public int getCantidadInicialBacterias() {
            return cantidadInicialBacterias;
        }

        public double getTemperatura() {
            return temperatura;
        }

        public String getCondicionesLuz() {
            return condicionesLuz;
        }

        public int getDosisInicialAlimento() {
            return dosisInicialAlimento;
        }

        public int getDosisFinalAlimento() {
            return dosisFinalAlimento;
        }

        public int getIncrementarHastaDia() {
            return incrementarHastaDia;
        }

        public int[] getDosisDiariaAlimento() {
            return dosisDiariaAlimento;
        }

        public int[] getComida() {
            return comida;
        }

        public String getPatronAlimento() {
            return patronAlimento;
        }

        public void setPatronAlimento(String patronAlimento) {
            this.patronAlimento = patronAlimento;
        }

    public int getDuracionExperimento() {
        long diffInMillies = Math.abs(fechaFin.getTime() - fechaInicio.getTime());
        long diff = diffInMillies / (24 * 60 * 60 * 1000);
        return (int) diff;
    }

}


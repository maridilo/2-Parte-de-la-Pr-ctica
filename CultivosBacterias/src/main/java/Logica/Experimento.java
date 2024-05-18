package Logica;

import Datos.PoblacionBacterias;
import java.util.ArrayList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

    public class Experimento implements Serializable {
        private ArrayList<PoblacionBacterias> poblaciones;
        private String descripcion;
        private String nombre;

        // Implementar lógica para el constructor
        public Experimento(String nombre, String descripcion) {
            this.nombre = nombre;
            this.descripcion = descripcion;
            poblaciones = new ArrayList<>();
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public void getPoblaciones(ArrayList<PoblacionBacterias> poblaciones) {
            this.poblaciones = poblaciones;
        }

        public void setPoblaciones(ArrayList<PoblacionBacterias> poblaciones) {
            this.poblaciones = poblaciones;
        }

        public ArrayList<PoblacionBacterias> getPoblaciones() {
            return poblaciones;
        }

        public void agregarPoblacion(PoblacionBacterias nuevaPoblacion) {
            PoblacionBacterias agregarPoblacion = new PoblacionBacterias(nuevaPoblacion.getNombre(), nuevaPoblacion.getFechaInicio(), nuevaPoblacion.getFechaFin(), nuevaPoblacion.getCantidadInicialBacterias(), nuevaPoblacion.getTemperatura(), nuevaPoblacion.getCondicionesLuz(), nuevaPoblacion.getDosisInicialAlimento(), nuevaPoblacion.getDosisFinalAlimento(), nuevaPoblacion.getIncrementarHastaDia());
            poblaciones.add(nuevaPoblacion);
        }

        public void eliminarPoblacion(String nombre) {
            // Implementar lógica para eliminar una población de bacterias
            for (int i = 0; i < poblaciones.size(); i++) {
                if (poblaciones.get(i).getNombre().equals(nombre)) {
                    poblaciones.remove(i);
                    break;
                }
            }
        }

        public void editarPoblacion(String nombre, PoblacionBacterias nuevaPoblacion) {
            for (int i = 0; i < poblaciones.size(); i++) {
                if (poblaciones.get(i).getNombre().equals(nombre)) {
                    poblaciones.set(i, nuevaPoblacion);
                    break;
                }
            }
        }

        public PoblacionBacterias obtenerPoblacion(String nombre) {
            // Implementar lógica para obtener una población de bacterias
            for (PoblacionBacterias poblacion : poblaciones) {
                if (poblacion.getNombre().equals(nombre)) {
                    return poblacion;
                }
            }
            return null;
        }

        public ArrayList<PoblacionBacterias> obtenerPoblaciones() {
            return poblaciones;
        }
    }

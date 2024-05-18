package Logica;

import Datos.PoblacionBacterias;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;



public class Experimento implements Serializable {
        private ArrayList<PoblacionBacterias> poblaciones;
        private String descripcion;
        private String nombre;
    private String currentFilePath;
    private static final String EXPERIMENTS_DIR = "experimentos";

        public Experimento(String nombre, String descripcion) {
            this.nombre = nombre;
            this.descripcion = descripcion;
            poblaciones = new ArrayList<>();
            File dir = new File(EXPERIMENTS_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }
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
            PoblacionBacterias agregarPoblacion = new PoblacionBacterias(nuevaPoblacion.getNombre(), nuevaPoblacion.getFechaInicio(), nuevaPoblacion.getFechaFin(), nuevaPoblacion.getCantidadInicialBacterias(), nuevaPoblacion.getTemperatura(), nuevaPoblacion.getCondicionesLuz(), nuevaPoblacion.getDosisInicialAlimento(), nuevaPoblacion.getDosisFinalAlimento(), nuevaPoblacion.getIncrementarHastaDia(), nuevaPoblacion.getPatronAlimento());
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

        public void ordenarPorFechaInicio() {
            poblaciones.sort(Comparator.comparing(PoblacionBacterias::getFechaInicio));
        }

        public void ordenarPorFechaFin() {
            poblaciones.sort(Comparator.comparing(PoblacionBacterias::getFechaFin));
        }

        public void ordenarPorNombre() {
            poblaciones.sort(Comparator.comparing(PoblacionBacterias::getNombre));
        }

        public void ordenarPorCantidadBacterias() {
            poblaciones.sort(Comparator.comparing(PoblacionBacterias::getCantidadInicialBacterias));
        }

    public void crearNuevoExperimento() {
        this.poblaciones.clear();
        this.currentFilePath = null;
    }

    public void cargarExperimento(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            this.poblaciones = (ArrayList<PoblacionBacterias>) ois.readObject();
            this.currentFilePath = filePath;
        }
    }

    public void guardarExperimento() throws IOException {
        if (this.currentFilePath != null) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(currentFilePath))) {
                oos.writeObject(this.poblaciones);
            }
        } else {
            throw new IOException("No file path specified. Use 'guardarComo' to specify a file path.");
        }
    }

    public void guardarComo(String filePath) throws IOException {
        this.currentFilePath = filePath;
        guardarExperimento();
    }

    public Experimento[] obtenerExperimentos() {
        File dir = new File(EXPERIMENTS_DIR);
        String[] archivos = dir.list((d, name) -> name.endsWith(".exp"));

        if (archivos != null && archivos.length > 0) {
            System.out.println("Experimentos disponibles:");
            for (String archivo : archivos) {
                System.out.println(archivo);
            }
        } else {
            System.out.println("No hay experimentos disponibles.");
        }

        return archivos != null ?
            List.of(archivos).stream()
                .map(archivo -> {
                    Experimento experimento = new Experimento("", "");
                    try {
                        experimento.cargarExperimento(EXPERIMENTS_DIR + "/" + archivo);
                        return experimento;
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .collect(Collectors.toList())
                .toArray(new Experimento[0])
            : new Experimento[0];
    }
}

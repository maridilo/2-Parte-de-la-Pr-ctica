package Datos;

import Logica.Experimento;
import Datos.PoblacionBacterias;
import java.io.*;
import java.util.ArrayList;

public class Archivos {

        public Experimento abrirArchivo(String nombreArchivo) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
                return (Experimento) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }

        public void guardarArchivo(String nombreArchivo, Experimento experimento) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
                oos.writeObject(experimento);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public ArrayList<PoblacionBacterias> abrirArchivoPoblaciones(String nombreArchivo) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
                return (ArrayList<PoblacionBacterias>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }

        public void guardarComoArchivo (String nombreArchivo, ArrayList<PoblacionBacterias> poblaciones) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
                oos.writeObject(poblaciones);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

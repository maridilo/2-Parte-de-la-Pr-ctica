package Datos;

import Logica.Experimento;
import Datos.PoblacionBacterias;
import java.io.*;
import java.util.ArrayList;
import java.io.ObjectOutputStream;

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
        try {
            FileOutputStream fileOut = new FileOutputStream(nombreArchivo);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(experimento);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
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

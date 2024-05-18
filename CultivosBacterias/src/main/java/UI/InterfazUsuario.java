
package UI;

import Logica.Experimento;
import Datos.Archivos;
import Datos.PoblacionBacterias;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.lang.Integer.parseInt;

public class InterfazUsuario {
        private Experimento experimento;
        private Archivos archivos;
        private JFrame frame;

        public InterfazUsuario() {
            experimento = new Experimento("Experimento", "Descripción del experimento");
            archivos = new Archivos();
            frame = new JFrame("Interfaz de Usuario");
            frame.setSize(200, 100);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            try {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                Logger.getLogger(InterfazUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Establecer el administrador de diseño
            frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

            JMenu menu = new JMenu("Menu");
            ImageIcon icon = new ImageIcon("icon.png");
            JMenuItem menuItem = new JMenuItem("Opción del menú", icon);
            menu.add(menuItem);

            JButton button = new JButton("Mostrar Menú");
            button.setForeground(new Color(255, 255, 255));
            button.setFont(new Font("Tahoma", 0, 24));
            button.setBackground(new Color(0, 102, 102));
            button.setAlignmentX(JButton.CENTER_ALIGNMENT);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mostrarMenu();
                }
            });

            frame.getContentPane().add(button);
            frame.setVisible(true);
        }

        public void mostrarMenu() {
            String[] options = new String[] {
                    "Abrir un archivo que contenga un experimento",
                    "Crear un nuevo experimento",
                    "Crear una población de bacterias y añadirla al experimento actual",
                    "Visualizar los nombres de todas las poblaciones de bacterias del experimento actual",
                    "Borrar una población de bacterias del experimento actual",
                    "Ver información detallada de una población de bacterias del experimento actual",
                    "Guardar",
                    "Guardar como",
                    "Salir"
            };

            JList<String> list = new JList<>(options);
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            list.setFixedCellWidth(400);
            list.setFixedCellHeight(20);
            list.setCellRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (c instanceof JLabel) {
                        JLabel label = (JLabel) c;
                        label.setText("<html>" + value.toString() + "</html>"); // Permite el ajuste de línea
                    }
                    return c;
                }
            });

            JScrollPane scrollPane = new JScrollPane(list);
            scrollPane.setPreferredSize(new Dimension(400, 200)); // Ajusta el tamaño según tus necesidades

            int response = JOptionPane.showOptionDialog(frame, scrollPane, "Menú", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
            if (response != JOptionPane.CLOSED_OPTION) {
                ejecutarOpcion(list.getSelectedIndex() + 1);
            }
        }

        public void ejecutarOpcion(int opcion) {
            switch (opcion) {
                case 1:
                    String nombreArchivo = JOptionPane.showInputDialog(frame, "Ingrese el nombre del archivo:");
                    experimento = archivos.abrirArchivo(nombreArchivo);
                    if (experimento != null) {
                        JOptionPane.showMessageDialog(frame, "Se ha abierto el archivo correctamente.", "Archivo Abierto", JOptionPane.INFORMATION_MESSAGE);
                        StringBuilder nombresExperimentos = new StringBuilder("Experimentos:\n");
                        for (Experimento experimento : experimento.obtenerExperimentos()) {
                            nombresExperimentos.append(experimento.getNombre()).append("\n");
                        }
                        JOptionPane.showMessageDialog(frame, nombresExperimentos.toString(), "Nombres de los Experimentos", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, "No se pudo abrir el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case 2:
                    String nombre = JOptionPane.showInputDialog(frame, "Ingrese el nombre del nuevo experimento:");
                    String descripcion = JOptionPane.showInputDialog(frame, "Ingrese una descripción para el nuevo experimento:");
                    experimento = new Experimento(nombre, descripcion);
                    JOptionPane.showMessageDialog(frame, "Se ha creado un nuevo experimento.", "Nuevo Experimento", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 3:
                    String nombrePoblacion = JOptionPane.showInputDialog(frame, "Ingrese el nombre de la población de bacterias:");
                    String fechaInicio = JOptionPane.showInputDialog(frame, "Ingrese la fecha de inicio de la población de bacterias (dd/MM/yyyy):");
                    String fechaFin = JOptionPane.showInputDialog(frame, "Ingrese la fecha de fin de la población de bacterias (dd/MM/yyyy):");
                    int cantidadInicialBacterias = parseInt(JOptionPane.showInputDialog(frame, "Ingrese la cantidad inicial de bacterias:"));
                    double temperatura = Double.parseDouble(JOptionPane.showInputDialog(frame, "Ingrese la temperatura:"));
                    String condicionesLuz = JOptionPane.showInputDialog(frame, "Ingrese las condiciones de luz:");
                    int dosisInicialAlimento = parseInt(JOptionPane.showInputDialog(frame, "Ingrese la dosis inicial de alimento:"));
                    int dosisFinalAlimento = parseInt(JOptionPane.showInputDialog(frame, "Ingrese la dosis final de alimento:"));
                    int incrementarHastaDia = parseInt(JOptionPane.showInputDialog(frame, "Ingrese el día hasta el que se incrementará la dosis de alimento:"));
                    String[] patrones = {"Constante","Incremento lineal", "Constante alternada"};
                    String patronAlimento = (String) JOptionPane.showInputDialog(frame, "Seleccione el patrón de alimentación:", "Patrón de Alimentación", JOptionPane.QUESTION_MESSAGE, null, patrones, patrones[0]);

                    PoblacionBacterias poblacion = new PoblacionBacterias(nombrePoblacion, parseDate(fechaInicio), parseDate(fechaFin), cantidadInicialBacterias, temperatura, condicionesLuz, dosisInicialAlimento, dosisFinalAlimento, incrementarHastaDia, patronAlimento);

                    experimento.agregarPoblacion(poblacion);
                    JOptionPane.showMessageDialog(frame, "Se ha creado una nueva población de bacterias.", "Población Creada", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 4:
                    String[] criterios = {"Fecha de inicio", "Nombre", "Cantidad de bacterias"};
                    String criterio = (String) JOptionPane.showInputDialog(frame, "Seleccione el criterio de ordenación:", "Ordenar por", JOptionPane.QUESTION_MESSAGE, null, criterios, criterios[0]);

                    if (criterio.equals("Fecha de inicio")) {
                        experimento.ordenarPorFechaInicio();
                    } else if (criterio.equals("Nombre")) {
                        experimento.ordenarPorNombre();
                    } else if (criterio.equals("Cantidad de bacterias")) {
                        experimento.ordenarPorCantidadBacterias();
                    }

                    StringBuilder nombresPoblaciones = new StringBuilder("Poblaciones:\n");
                    for (PoblacionBacterias poblacionBacterias : experimento.obtenerPoblaciones()) {
                        nombresPoblaciones.append(poblacionBacterias.getNombre()).append("\n");
                    }
                    JOptionPane.showMessageDialog(frame, nombresPoblaciones.toString(), "Nombres de las Poblaciones", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 5:
                    String nombrePoblacioneliminar = JOptionPane.showInputDialog(frame, "Ingrese el nombre de la población de bacterias que desea eliminar:");
                    experimento.eliminarPoblacion(nombrePoblacioneliminar);
                    JOptionPane.showMessageDialog(frame, "Se ha eliminado la población de bacterias.", "Población Eliminada", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 6:
                    String nombrePoblacionDetallada = JOptionPane.showInputDialog(frame, "Ingrese el nombre de la población de bacterias de la que desea ver la información detallada:");
                    PoblacionBacterias poblacionDetallada = experimento.obtenerPoblacion(nombrePoblacionDetallada);
                    if (poblacionDetallada != null) {
                        String informacionDetallada = "Nombre: " + poblacionDetallada.getNombre() + "\n" +
                                "Fecha de inicio: " + poblacionDetallada.getFechaInicio() + "\n" +
                                "Fecha de fin: " + poblacionDetallada.getFechaFin() + "\n" +
                                "Cantidad inicial de bacterias: " + poblacionDetallada.getCantidadInicialBacterias() + "\n" +
                                "Temperatura: " + poblacionDetallada.getTemperatura() + "\n" +
                                "Condiciones de luz: " + poblacionDetallada.getCondicionesLuz() + "\n" +
                                "Dosis inicial de alimento: " + poblacionDetallada.getDosisInicialAlimento() + "\n" +
                                "Dosis final de alimento: " + poblacionDetallada.getDosisFinalAlimento() + "\n" +
                                "Incrementar hasta el día: " + poblacionDetallada.getIncrementarHastaDia();
                        JOptionPane.showMessageDialog(frame, informacionDetallada, "Información detallada de la población de bacterias", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, "No se encontró la población de bacterias con ese nombre.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case 7:
                    nombreArchivo = JOptionPane.showInputDialog(frame, "Ingrese el nombre del archivo donde desea guardar:");
                    archivos.guardarArchivo(nombreArchivo, experimento);
                    JOptionPane.showMessageDialog(frame, "Se ha guardado el archivo correctamente.", "Archivo Guardado", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 8:
                    nombreArchivo = JOptionPane.showInputDialog(frame, "Ingrese el nombre del nuevo archivo donde desea guardar:");
                    archivos.guardarComoArchivo(nombreArchivo, experimento.obtenerPoblaciones());
                    JOptionPane.showMessageDialog(frame, "Se ha guardado el archivo correctamente.", "Archivo Guardado", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 9:
                    int salir = JOptionPane.showConfirmDialog(frame, "¿Está seguro de que desea salir?", "Salir", JOptionPane.YES_NO_OPTION);
                    if (salir == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                    break;
                default:
                    JOptionPane.showMessageDialog(frame, "Opción no válida. Por favor, elija una opción del menú.");
                    break;
            }
        }

    private Date parseDate(String mensaje) {
        Date date = null;
        while (date == null) {
            String fechaStr = JOptionPane.showInputDialog(frame, mensaje);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            try {
                date = formatter.parse(fechaStr);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(frame, "Fecha no válida. Por favor, ingrese la fecha en el formato correcto (dd/mm/yyyy).");
            }
        }
        return date;
    }

    private int parseInteger(String message) {
        String intString = JOptionPane.showInputDialog(frame, message);
        try {
            return parseInt(intString);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Debe ingresar un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
            return parseInteger(message);
        }
    }

    private double parseDouble(String message) {
        String doubleString = JOptionPane.showInputDialog(frame, message);
        try {
            return Double.parseDouble(doubleString);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Debe ingresar un número decimal.", "Error", JOptionPane.ERROR_MESSAGE);
            return parseDouble(message);
        }
    }

        public static void main(String[] args) {
            new UI.InterfazUsuario();
        }
    }


package Item;

import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Miembros {
    private static final String RUTA_ARCHIVO = "src\\data\\DatosM.csv";

    public static String calcularFechaVencimiento(LocalDate fechaInicio) {
        if (fechaInicio == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return fechaInicio.plusYears(1).format(formatter);
    }

    public static DefaultTableModel leerSociosDesdeArchivo(String rutaArchivo) {
        List<Socio> socios = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
        try (BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea = lector.readLine(); // Leer encabezados
    
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length >= 9) { // Mínimo 9 campos para incluir fecha de fin y cashback
                    try {
                        String nombre = campos[0].trim();
                        Socio.TipoMembresia tipoMembresia = Socio.convertirAMembresia(campos[1]);
                        String direccion = campos[2].trim();
                        String telefono = campos[3].trim();
                        String rfc = campos[4].trim();
                        String add1 = campos[5].trim();
                        String add2 = campos[6].trim();
    
                        // Leer Fecha Inicio
                        LocalDate fechaInicio = null;
                        for (String formato : new String[]{"dd/MM/yyyy", "yyyy-MM-dd", "yyyy/MM/dd"}) {
                            try {
                                fechaInicio = campos.length > 7
                                        ? LocalDate.parse(campos[7].trim(), DateTimeFormatter.ofPattern(formato))
                                        : null;
                                break;
                            } catch (Exception e) {
                                // Ignorar error y continuar
                            }
                        }
    
                        // Leer Fecha de Fin
                        LocalDate fechaFin = null;
                        for (String formato : new String[]{"dd/MM/yyyy", "yyyy-MM-dd", "yyyy/MM/dd"}) {
                            try {
                                fechaFin = campos.length > 8
                                        ? LocalDate.parse(campos[8].trim(), DateTimeFormatter.ofPattern(formato))
                                        : null;
                                break;
                            } catch (Exception e) {
                                // Ignorar error y continuar
                            }
                        }
    
                        // Recalcular Fecha de Fin si no es válida
                        if (fechaFin == null && fechaInicio != null) {
                            fechaFin = fechaInicio.plusYears(1);
                        }
    
                        // Leer Cashback
                        double cashback = campos.length > 9 ? Double.parseDouble(campos[9].replace("$", "")) : 0.0;
    
                        Socio socio = new Socio(nombre, direccion, telefono, rfc, tipoMembresia);
                        socio.setUsuarioAdicional1(add1);
                        socio.setUsuarioAdicional2(add2);
                        socio.setFechaInicio(fechaInicio);
                        socio.setCashback(cashback);
    
                        socios.add(socio);
                    } catch (Exception e) {
                        System.err.println("Error procesando línea: " + linea + " | " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    
        return convertirSociosATableModel(socios);
    }
    

    public static DefaultTableModel convertirSociosATableModel(List<Socio> socios) {
        String[] columnas = {"Nombre", "Tipo", "Dirección", "Teléfono", "RFC",
                "Add1", "Add2", "Fecha I.", "Fecha F.", "Cashback"};

        DefaultTableModel tableModel = new DefaultTableModel(columnas, 0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Socio socio : socios) {
            LocalDate fechaInicio = socio.getFechaInicio();
            String fechaFin = calcularFechaVencimiento(fechaInicio);

            Object[] fila = {
                    socio.getNombre(),
                    socio.getTipoMembresia().name(),
                    socio.getDireccion(),
                    socio.getTelefono(),
                    socio.getRfc(),
                    socio.getUsuarioAdicional1(),
                    socio.getUsuarioAdicional2(),
                    fechaInicio != null ? fechaInicio.format(formatter) : "",
                    fechaFin,
                    "$" + socio.getCashback()
            };
            tableModel.addRow(fila);
        }

        return tableModel;
    }

    public static void guardarTableModelEnArchivo(String rutaArchivo, DefaultTableModel tableModel) {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (int col = 0; col < tableModel.getColumnCount(); col++) {
                escritor.write(tableModel.getColumnName(col));
                if (col < tableModel.getColumnCount() - 1) {
                    escritor.write(",");
                }
            }
            escritor.newLine();
            for (int fila = 0; fila < tableModel.getRowCount(); fila++) {
                for (int col = 0; col < tableModel.getColumnCount(); col++) {
                    Object valor = tableModel.getValueAt(fila, col);
                    escritor.write(valor != null ? valor.toString() : "");
                    if (col < tableModel.getColumnCount() - 1) {
                        escritor.write(",");
                    }
                }
                escritor.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo: " + e.getMessage());
        }
    }
}

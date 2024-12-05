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
    
    // Método para calcular la fecha de vencimiento
    public static String calcularFechaVencimiento(LocalDate fechaInicio) {
        if (fechaInicio == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        return fechaInicio.plusYears(1).format(formatter);
    }
    
    // Convertir DefaultTableModel a lista de objetos Socio
    public static List<Socio> convertirTableModelASocios(DefaultTableModel tableModel) {
        List<Socio> socios = new ArrayList<>();
        if (tableModel == null || tableModel.getRowCount() == 0) {
            return socios;
        }
        
        int indiceNombre = findColumnIndex(tableModel, "Nombre");
        int indiceDireccion = findColumnIndex(tableModel, "Dirección");
        int indiceTelefono = findColumnIndex(tableModel, "Teléfono");
        int indiceRFC = findColumnIndex(tableModel, "RFC");
        int indiceTipo = findColumnIndex(tableModel, "Tipo");
    
        if (indiceNombre == -1 || indiceDireccion == -1 || indiceTelefono == -1 ||
            indiceRFC == -1 || indiceTipo == -1) {
            throw new IllegalArgumentException("No se encontraron todas las columnas necesarias");
        }
        
        for (int fila = 0; fila < tableModel.getRowCount(); fila++) {
            try {
                String nombre = (String) tableModel.getValueAt(fila, indiceNombre);
                String direccion = (String) tableModel.getValueAt(fila, indiceDireccion);
                String telefono = (String) tableModel.getValueAt(fila, indiceTelefono);
                String rfc = (String) tableModel.getValueAt(fila, indiceRFC);
                Socio.TipoMembresia tipoMembresia = 
                    Socio.TipoMembresia.valueOf(((String) tableModel.getValueAt(fila, indiceTipo)).toUpperCase());
                
                Socio socio = new Socio(nombre, direccion, telefono, rfc, tipoMembresia);
                
                int indiceAdicional1 = findColumnIndex(tableModel, "Adicional 1");
                int indiceAdicional2 = findColumnIndex(tableModel, "Adicional 2");
                
                if (indiceAdicional1 != -1) {
                    socio.setUsuarioAdicional1((String) tableModel.getValueAt(fila, indiceAdicional1));
                }
                if (indiceAdicional2 != -1) {
                    socio.setUsuarioAdicional2((String) tableModel.getValueAt(fila, indiceAdicional2));
                }
                
                socios.add(socio);
            } catch (Exception e) {
                System.err.println("Error procesando fila " + (fila + 1) + ": " + e.getMessage());
            }
        }
        
        return socios;
    }
    
    // Convertir lista de Socios a DefaultTableModel
    public static DefaultTableModel convertirSociosATableModel(List<Socio> socios) {
        String[] columnas = {"Nombre", "Tipo", "Dirección", "Teléfono", "RFC", 
            "Adicional 1", "Adicional 2", "Fecha de Inicio", "Fecha de Fin"};
        
        DefaultTableModel tableModel = new DefaultTableModel(columnas, 0);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        
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
            };
            tableModel.addRow(fila);
        }
        
        return tableModel;
    }
    
    // Leer socios desde un archivo CSV
    public static DefaultTableModel leerSociosDesdeArchivo(String rutaArchivo) {
        List<Socio> socios = new ArrayList<>();
    
        try (BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea = lector.readLine(); // Leer encabezados
            
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length >= 7) {
                    try {
                        String nombre = campos[0];
                        String direccion = campos[1];
                        String telefono = campos[2];
                        String rfc = campos[3];
                        Socio.TipoMembresia tipoMembresia = Socio.TipoMembresia.valueOf(campos[4].toUpperCase());
                        
                        Socio socio = new Socio(nombre, direccion, telefono, rfc, tipoMembresia);
                        if (campos.length > 7) {
                            socio.setCashback(Double.parseDouble(campos[7]));
                        }
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
    
    // Guardar DefaultTableModel en un archivo CSV
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
            e.printStackTrace(); // Para ver la traza completa del error
        }
    }
    
    // Buscar índice de columna
    private static int findColumnIndex(DefaultTableModel tableModel, String nombreColumna) {
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            if (tableModel.getColumnName(i).equalsIgnoreCase(nombreColumna)) {
                return i;
            }
        }
        return -1;
    }
}

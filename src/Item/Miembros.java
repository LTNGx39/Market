package Item;

import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Miembros {
    private static final String RUTA_ARCHIVO = "src\\data\\DatosM.csv";
    
    // Convertir DefaultTableModel a lista de objetos Socio
    public static List<Socio> convertirTableModelASocios(DefaultTableModel tableModel) {
        List<Socio> socios = new ArrayList<>();
        if (tableModel == null || tableModel.getRowCount() == 0) {
            return socios;
        }
        
        int indiceNombre = findColumnIndex(tableModel, "Nombre");
        int indiceDireccion = findColumnIndex(tableModel, "Direccion");
        int indiceTelefono = findColumnIndex(tableModel, "Telefono");
        int indiceRFC = findColumnIndex(tableModel, "RFC");
        int indiceTipoMembresia = findColumnIndex(tableModel, "TipoMembresia");

        if (indiceNombre == -1 || indiceDireccion == -1 || indiceTelefono == -1 ||
            indiceRFC == -1 || indiceTipoMembresia == -1) {
            throw new IllegalArgumentException("No se encontraron todas las columnas necesarias");
        }
        
        for (int fila = 0; fila < tableModel.getRowCount(); fila++) {
            try {
                String nombre = (String) tableModel.getValueAt(fila, indiceNombre);
                String direccion = (String) tableModel.getValueAt(fila, indiceDireccion);
                String telefono = (String) tableModel.getValueAt(fila, indiceTelefono);
                String rfc = (String) tableModel.getValueAt(fila, indiceRFC);
                Socio.TipoMembresia tipoMembresia = 
                    Socio.TipoMembresia.valueOf(((String) tableModel.getValueAt(fila, indiceTipoMembresia)).toUpperCase());
                
                Socio socio = new Socio(nombre, direccion, telefono, rfc, tipoMembresia);
                
                int indiceUsuario1 = findColumnIndex(tableModel, "UsuarioAdicional1");
                int indiceUsuario2 = findColumnIndex(tableModel, "UsuarioAdicional2");
                
                if (indiceUsuario1 != -1) {
                    socio.setUsuarioAdicional1((String) tableModel.getValueAt(fila, indiceUsuario1));
                }
                if (indiceUsuario2 != -1) {
                    socio.setUsuarioAdicional2((String) tableModel.getValueAt(fila, indiceUsuario2));
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
        String[] columnas = {"Nombre", "Direccion", "Telefono", "RFC", 
            "Usuario Adicional 1", "Usuario Adicional 2", 
            "Tipo Membresía", "Fecha Inicio", "Fecha Renovación", 
            "Activa", "Cashback"};
        
        DefaultTableModel tableModel = new DefaultTableModel(columnas, 0);
        
        for (Socio socio : socios) {
            Object[] fila = {
                socio.getNombre(),
                socio.getDireccion(),
                socio.getTelefono(),
                socio.getRfc(),
                socio.getUsuarioAdicional1(),
                socio.getUsuarioAdicional2(),
                socio.getTipoMembresia().name(),
                socio.getFechaInicio(),
                socio.getFechaRenovacion(),
                socio.isActiva(),
                socio.getCashback()
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
    public static void guardarTableModelEnArchivo(String rutaArchivo, DefaultTableModel tableModel) throws IOException {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(rutaArchivo))) {
            // Escribir encabezados
            for (int col = 0; col < tableModel.getColumnCount(); col++) {
                escritor.write(tableModel.getColumnName(col));
                if (col < tableModel.getColumnCount() - 1) {
                    escritor.write(",");
                }
            }
            escritor.newLine();
            
            // Escribir filas
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

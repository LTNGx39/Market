package Item;

import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Miembros {
    
    /**
     * Convierte un DefaultTableModel a una lista de objetos Socio
     * 
     * @param tableModel Modelo de tabla con datos de socios
     * @return Lista de objetos Socio
     */
    public static List<Socio> convertirTableModelASocios(DefaultTableModel tableModel) {
        List<Socio> socios = new ArrayList<>();
        
        // Validar que el modelo de tabla no esté vacío
        if (tableModel == null || tableModel.getRowCount() == 0) {
            return socios;
        }
        
        // Definir índices de columnas (ajustar según la estructura real de la tabla)
        int indiceNombre = findColumnIndex(tableModel, "Nombre");
        int indiceDireccion = findColumnIndex(tableModel, "Direccion");
        int indiceTelefono = findColumnIndex(tableModel, "Telefono");
        int indiceRFC = findColumnIndex(tableModel, "RFC");
        int indiceTipoMembresia = findColumnIndex(tableModel, "TipoMembresia");
        
        // Validar que se encuentren todas las columnas necesarias
        if (indiceNombre == -1 || indiceDireccion == -1 || 
            indiceTelefono == -1 || indiceRFC == -1 || indiceTipoMembresia == -1) {
            throw new IllegalArgumentException("No se encontraron todas las columnas necesarias");
        }
        
        // Recorrer cada fila del DefaultTableModel
        for (int fila = 0; fila < tableModel.getRowCount(); fila++) {
            try {
                // Obtener datos de la fila
                String nombre = (String) tableModel.getValueAt(fila, indiceNombre);
                String direccion = (String) tableModel.getValueAt(fila, indiceDireccion);
                String telefono = (String) tableModel.getValueAt(fila, indiceTelefono);
                String rfc = (String) tableModel.getValueAt(fila, indiceRFC);
                
                // Convertir tipo de membresía
                Socio.TipoMembresia tipoMembresia = 
                    Socio.TipoMembresia.valueOf(
                        ((String) tableModel.getValueAt(fila, indiceTipoMembresia)).toUpperCase()
                    );
                
                // Crear objeto Socio
                Socio socio = new Socio(nombre, direccion, telefono, rfc, tipoMembresia);
                
                // Obtener y establecer columnas adicionales si existen
                int indiceUsuario1 = findColumnIndex(tableModel, "UsuarioAdicional1");
                int indiceUsuario2 = findColumnIndex(tableModel, "UsuarioAdicional2");
                
                if (indiceUsuario1 != -1) {
                    String usuarioAdicional1 = (String) tableModel.getValueAt(fila, indiceUsuario1);
                    socio.setUsuarioAdicional1(usuarioAdicional1);
                }
                
                if (indiceUsuario2 != -1) {
                    String usuarioAdicional2 = (String) tableModel.getValueAt(fila, indiceUsuario2);
                    socio.setUsuarioAdicional2(usuarioAdicional2);
                }
                
                socios.add(socio);
                
            } catch (Exception e) {
                System.err.println("Error procesando fila " + (fila + 1) + ": " + e.getMessage());
            }
        }
        
        return socios;
    }
    
    /**
     * Convierte una lista de Socios a un DefaultTableModel
     * 
     * @param socios Lista de objetos Socio
     * @return DefaultTableModel con los datos de los socios
     */
    public static DefaultTableModel convertirSociosATableModel(List<Socio> socios) {
        // Definir columnas
        String[] columnas = {
            "Nombre", "Direccion", "Telefono", "RFC", 
            "Usuario Adicional 1", "Usuario Adicional 2", 
            "Tipo Membresía", "Fecha Inicio", "Fecha Renovación", 
            "Activa", "Cashback"
        };
        
        // Crear modelo de tabla
        DefaultTableModel tableModel = new DefaultTableModel(columnas, 0);
        
        // Llenar el modelo con datos de los socios
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
    
    /**
     * Lee socios desde un archivo CSV
     * 
     * @param rutaArchivo Ruta del archivo CSV
     * @return DefaultTableModel con los datos de los socios
     * @throws IOException Si hay un error al leer el archivo
     */
    public static DefaultTableModel leerSociosDesdeArchivo(String rutaArchivo) throws IOException {
        List<Socio> socios = new ArrayList<>();
        
        try (BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo))) {
            // Saltar la línea de encabezados
            String linea = lector.readLine();
            
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                
                // Validar que la línea tenga suficientes campos
                if (campos.length >= 7) {
                    try {
                        String nombre = campos[0];
                        String direccion = campos[1];
                        String telefono = campos[2];
                        String rfc = campos[3];
                        
                        // Convertir tipo de membresía
                        Socio.TipoMembresia tipoMembresia = 
                            Socio.TipoMembresia.valueOf(campos[4].toUpperCase());
                        
                        // Crear socio
                        Socio socio = new Socio(nombre, direccion, telefono, rfc, tipoMembresia);
                        
                        // Establecer campos adicionales si están presentes
                        if (campos.length > 7) {
                            // Ejemplo de cómo manejar campos adicionales como fechas o cashback
                            socio.setCashback(Double.parseDouble(campos[7]));
                        }
                        
                        socios.add(socio);
                    } catch (Exception e) {
                        System.err.println("Error procesando línea: " + linea);
                    }
                }
            }
        }
        
        // Convertir lista de socios a DefaultTableModel
        return convertirSociosATableModel(socios);
    }
    
    /**
     * Busca el índice de una columna en el DefaultTableModel por su nombre
     * 
     * @param tableModel Modelo de tabla
     * @param nombreColumna Nombre de la columna a buscar
     * @return Índice de la columna o -1 si no se encuentra
     */
    private static int findColumnIndex(DefaultTableModel tableModel, String nombreColumna) {
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            if (tableModel.getColumnName(i).equalsIgnoreCase(nombreColumna)) {
                return i;
            }
        }
        return -1;
    }
}
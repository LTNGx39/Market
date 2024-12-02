package Item;

import javax.swing.table.TableModel;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Miembros {
    
    /**
     * Lee datos de un TableModel y convierte cada fila en un objeto Socio
     * 
     * @param tableModel El modelo de tabla que contiene los datos de los socios
     * @return Lista de objetos Socio creados a partir de los datos de la tabla
     */
    public static List<Socio> convertirTableModelASocios(TableModel tableModel) {
        List<Socio> socios = new ArrayList<>();
        
        // Validar que el TableModel tenga los datos necesarios
        if (tableModel == null || tableModel.getRowCount() == 0) {
            return socios;
        }
        
        // Indices de columnas esperados (ajustar según la estructura real de tu tabla)
        int indiceNombre = findColumnIndex(tableModel, "nombre");
        int indiceDireccion = findColumnIndex(tableModel, "direccion");
        int indiceTelefono = findColumnIndex(tableModel, "telefono");
        int indiceRFC = findColumnIndex(tableModel, "rfc");
        int indiceTipoMembresia = findColumnIndex(tableModel, "tipoMembresia");
        
        // Validar que se encuentren todas las columnas necesarias
        if (indiceNombre == -1 || indiceDireccion == -1 || 
            indiceTelefono == -1 || indiceRFC == -1 || indiceTipoMembresia == -1) {
            throw new IllegalArgumentException("No se encontraron todas las columnas necesarias");
        }
        
        // Recorrer cada fila del TableModel
        for (int fila = 0; fila < tableModel.getRowCount(); fila++) {
            try {
                // Obtener datos de la fila
                String nombre = (String) tableModel.getValueAt(fila, indiceNombre);
                String direccion = (String) tableModel.getValueAt(fila, indiceDireccion);
                String telefono = (String) tableModel.getValueAt(fila, indiceTelefono);
                String rfc = (String) tableModel.getValueAt(fila, indiceRFC);
                
                // Convertir tipo de membresía (asumiendo que es un String que coincide con el enum)
                Socio.TipoMembresia tipoMembresia = 
                    Socio.TipoMembresia.valueOf(
                        ((String) tableModel.getValueAt(fila, indiceTipoMembresia)).toUpperCase()
                    );
                
                // Crear objeto Socio
                Socio socio = new Socio(nombre, direccion, telefono, rfc, tipoMembresia);
                socios.add(socio);
                
            } catch (Exception e) {
                System.err.println("Error procesando fila " + (fila + 1) + ": " + e.getMessage());
                // Opcional: continuar procesando otras filas o lanzar excepción
            }
        }
        
        return socios;
    }
    
    /**
     * Guarda los socios en un archivo de texto
     * 
     * @param socios Lista de socios a guardar
     * @param rutaArchivo Ruta del archivo donde se guardarán los socios
     * @throws IOException Si hay un error al escribir en el archivo
     */
    public static void guardarSociosEnArchivo(List<Socio> socios, String rutaArchivo) throws IOException {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(rutaArchivo))) {
            // Escribir encabezado
            escritor.write("Nombre,Direccion,Telefono,RFC,Tipo Membresía,Fecha Inicio,Fecha Renovación,Activa,Cashback");
            escritor.newLine();
            
            // Escribir cada socio
            for (Socio socio : socios) {
                String lineaSocio = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%.2f",
                    socio.getNombre(), 
                    socio.getDireccion(), 
                    socio.getTelefono(), 
                    socio.getRfc(), 
                    socio.getTipoMembresia(), 
                    socio.getFechaInicio(), 
                    socio.getFechaRenovacion(), 
                    socio.isActiva(), 
                    socio.getCashback()
                );
                escritor.write(lineaSocio);
                escritor.newLine();
            }
        }
    }
    
    /**
     * Busca el índice de una columna en el TableModel por su nombre
     * 
     * @param tableModel Modelo de tabla
     * @param nombreColumna Nombre de la columna a buscar
     * @return Índice de la columna o -1 si no se encuentra
     */
    private static int findColumnIndex(TableModel tableModel, String nombreColumna) {
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            if (tableModel.getColumnName(i).equalsIgnoreCase(nombreColumna)) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Método de ejemplo para usar los métodos anteriores
     */
    public static void ejemploUso(TableModel tableModel) {
        try {
            // Convertir TableModel a lista de Socios
            List<Socio> socios = convertirTableModelASocios(tableModel);
            
            // Guardar socios en un archivo
            guardarSociosEnArchivo(socios, "socios.csv");
            
            System.out.println("Se han guardado " + socios.size() + " socios en el archivo.");
        } catch (IOException e) {
            System.err.println("Error al guardar los socios: " + e.getMessage());
        }
    }
}
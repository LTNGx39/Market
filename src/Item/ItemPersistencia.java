package Item;

import java.io.*;
import java.nio.file.*;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class ItemPersistencia {
    // Ruta de la carpeta y archivo
    private static final String CARPETA = "src/data";
    private static final String RUTA_ARCHIVO = CARPETA + "/items.csv";
    
    /**
     * Método para guardar Items desde un DefaultTableModel a un archivo
     * @param tableModel DefaultTableModel que contiene los datos de los Items
     */
    public static void guardarItems(DefaultTableModel tableModel) {
        try {
            // Verificar si la carpeta existe, si no, crearla
            Path carpetaPath = Paths.get(CARPETA);
            if (!Files.exists(carpetaPath)) {
                Files.createDirectories(carpetaPath);
                System.out.println("Carpeta creada en: " + carpetaPath.toAbsolutePath());
            }
            // Escribir datos en el archivo
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA_ARCHIVO))) {
                for (int fila = 0; fila < tableModel.getRowCount(); fila++) {
                    String nombre = tableModel.getValueAt(fila, 0).toString();
                    String id = tableModel.getValueAt(fila, 1).toString();
                    String descripcion = tableModel.getValueAt(fila, 2).toString();
                    double precioCompra = Double.parseDouble(tableModel.getValueAt(fila, 3).toString().replace("$", ""));
                    double precioVenta = Double.parseDouble(tableModel.getValueAt(fila, 4).toString().replace("$", ""));
                    double descuento = Double.parseDouble(tableModel.getValueAt(fila, 5).toString().replace("%", ""));
                    int stock = Integer.parseInt(tableModel.getValueAt(fila, 6).toString());
                    writer.write(String.format("%s;%s;%s;%.2f;%.2f;%.2f;%d\n",
                            nombre, id, descripcion,
                            precioCompra, precioVenta,
                            descuento, stock));
                }
                System.out.println("Datos guardados exitosamente en: " + RUTA_ARCHIVO);
            }
        } catch (IOException e) {
            System.err.println("Error al guardar los datos: " + e.getMessage());
        }
    }
    
    /**
     * Método para leer Items desde un archivo y devolver un DefaultTableModel
     * @return DefaultTableModel con los Items leídos del archivo
     */
    public static DefaultTableModel leerItemsDesdeArchivo() {
        // Definir los nombres de las columnas
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Nombre");
        columnNames.add("ID");
        columnNames.add("Descripción");
        columnNames.add("Precio Compra");
        columnNames.add("Precio Venta");
        columnNames.add("Descuento");
        columnNames.add("Stock");
    
        // Vector para almacenar los datos
        Vector<Vector<Object>> data = new Vector<>();
    
        try {
            // Verificar si el archivo existe
            Path archivoPath = Paths.get(RUTA_ARCHIVO);
            
            // Verificación explícita de existencia del archivo
            if (!Files.exists(archivoPath)) {
                try {
                    // Crear directorios si no existen
                    Files.createDirectories(archivoPath.getParent());
                    
                    // Usar FileWriter para crear el archivo
                    try (FileWriter writer = new FileWriter(archivoPath.toFile())) {
                        // Archivo creado, pero vacío
                        System.out.println("Archivo creado en: " + archivoPath);
                    }
                    
                    // Devolver un DefaultTableModel vacío
                    return new DefaultTableModel(columnNames, 0);
                } catch (IOException e) {
                    System.err.println("Error al crear el archivo: " + e.getMessage());
                    return new DefaultTableModel(columnNames, 0);
                }
            }
    
            // Leer datos desde el archivo
            try (BufferedReader reader = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    // Verificar que la línea no esté vacía
                    if (!linea.trim().isEmpty()) {
                        String[] datos = linea.split(";");
                        Vector<Object> row = new Vector<>();
                        row.add(datos[0]);       // Nombre
                        row.add(datos[1]);       // ID
                        row.add(datos[2]);       // Descripción
                        row.add(Double.parseDouble(datos[3]));  // Precio Compra
                        row.add(Double.parseDouble(datos[4]));  // Precio Venta
                        row.add(Double.parseDouble(datos[5]));  // Descuento
                        row.add(Integer.parseInt(datos[6]));    // Stock
    
                        data.add(row);
                    }
                }
                System.out.println("Datos leídos exitosamente de: " + RUTA_ARCHIVO);
            }
        } catch (IOException e) {
            System.err.println("Error al leer los datos: " + e.getMessage());
        }
    
        // Crear y devolver DefaultTableModel
        return new DefaultTableModel(data, columnNames);
    }
}

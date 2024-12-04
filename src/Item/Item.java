package Item;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class Item {

    // Atributos privados para encapsulación
    private String nombre;
    private String id;
    private String descripcion;
    private double precioCompra;
    private double precioVenta;
    private double descuento;
    private int stock;

    // Variables estaticas para lectura y escritura
    private static final String CARPETA = "src/data";
    private static final String RUTA_ARCHIVO = CARPETA + "/items.csv";
   
    // Constante para el stock mínimo que genera alerta
    private static final int STOCK_MINIMO = 5;

    // Constructor
    public Item(String nombre, String id, String descripcion, double precioCompra,
                double precioVenta, double descuento, int stock) {
        this.nombre = nombre;
        this.id = id;
        this.descripcion = descripcion;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.descuento = descuento;
        this.stock = stock;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getPrecioCompra() {
        return String.format("$%.2f", precioCompra); // Precio formateado con $
    }

    public String getPrecioVenta() {
        return String.format("$%.2f", precioVenta); // Precio formateado con $
    }

    public String getDescuento() {
        return String.format("%.0f%%", descuento * 100); // Descuento formateado con %
    }

    public int getStock() {
        return stock;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    // Método para comprar artículo
    public boolean compraArticulo(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
       
        if (stock >= cantidad) {
            stock -= cantidad;
            verificarStockMinimo();
            return true;
        }
        return false;
    }

    // Método privado para verificar si el stock está bajo el mínimo
    private void verificarStockMinimo() {
        if (stock < STOCK_MINIMO) {
            System.out.println("¡ALERTA! Stock bajo para el artículo: " + nombre +
                               " (ID: " + id + "). Stock actual: " + stock);
        }
    }

    // Método para obtener el precio final con descuento
    public String getPrecioConDescuento() {
        double precioConDescuento = precioVenta * (1 - descuento);
        return String.format("$%.2f", precioConDescuento);
    }

    // Método toString para imprimir información del artículo
    @Override
    public String toString() {
        return "Item{" +
               "nombre='" + nombre + '\'' +
               ", id='" + id + '\'' +
               ", descripcion='" + descripcion + '\'' +
               ", precioCompra=" + getPrecioCompra() +
               ", precioVenta=" + getPrecioVenta() +
               ", descuento=" + getDescuento() +
               ", stock=" + stock +
               '}';
    }

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
                        row.add("$" + Double.parseDouble(datos[3]));  // Precio Compra
                        row.add("$" + Double.parseDouble(datos[4]));  // Precio Venta
                        row.add(Double.parseDouble(datos[5]) + "%");  // Descuento
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
    
    /**
     * Método para verificar si un ID ya existe en el archivo.
     * 
     * @param id El ID del producto a verificar.
     * @return true si el ID existe, false en caso contrario.
     */
    public static boolean existeID(String id) {
        try {
            // Verificar si el archivo existe
            Path archivoPath = Paths.get(RUTA_ARCHIVO);
            if (!Files.exists(archivoPath)) {
                System.out.println("El archivo no existe. No se encontraron productos.");
                return false;
            }

            // Leer el archivo y buscar el ID
            try (BufferedReader reader = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    // Saltar encabezados o líneas vacías
                    if (linea.trim().isEmpty() || linea.startsWith("Nombre")) {
                        continue;
                    }

                    String[] datos = linea.split(";");
                    if (datos.length > 1 && datos[1].equals(id)) {
                        return true; // ID encontrado
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }

        return false; // ID no encontrado
    }
}

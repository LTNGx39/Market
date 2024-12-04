package Item;

import java.io.*;
import java.nio.file.*;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class ItemManager {
    // Constantes para rutas de archivo y carpeta
    private static final String CARPETA = "src/data";
    private static final String RUTA_ARCHIVO = CARPETA + "/items.csv";
    private static final int STOCK_MINIMO = 5; // Stock mínimo que genera alerta

    // Clase interna Item
    public static class Item {
        private String nombre;
        private String id;
        private String descripcion;
        private double precioCompra;
        private double precioVenta;
        private double descuento;
        private int stock;

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

        // Getters y Setters
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

        public double getPrecioCompra() { return precioCompra; }
        public void setPrecioCompra(double precioCompra) { this.precioCompra = precioCompra; }

        public double getPrecioVenta() { return precioVenta; }
        public void setPrecioVenta(double precioVenta) { this.precioVenta = precioVenta; }

        public double getDescuento() { return descuento; }
        public void setDescuento(double descuento) { this.descuento = descuento; }

        public int getStock() { return stock; }
        public void setStock(int stock) { this.stock = stock; }

        // Métodos adicionales
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

        private void verificarStockMinimo() {
            if (stock < STOCK_MINIMO) {
                System.out.println("¡ALERTA! Stock bajo para el artículo: " + nombre +
                                   " (ID: " + id + "). Stock actual: " + stock);
            }
        }

        public double getPrecioConDescuento() {
            return precioVenta * (1 - descuento);
        }

        @Override
        public String toString() {
            return "Item{" +
                   "nombre='" + nombre + '\'' +
                   ", id='" + id + '\'' +
                   ", descripcion='" + descripcion + '\'' +
                   ", precioCompra=" + precioCompra +
                   ", precioVenta=" + precioVenta +
                   ", descuento=" + descuento +
                   ", stock=" + stock +
                   '}';
        }
    }

    // Métodos para persistencia

    /**
     * Guardar Items en un archivo CSV desde un DefaultTableModel.
     */
    public static void guardarItems(DefaultTableModel tableModel) {
        try {
            Path carpetaPath = Paths.get(CARPETA);
            if (!Files.exists(carpetaPath)) {
                Files.createDirectories(carpetaPath);
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA_ARCHIVO))) {
                writer.write("Nombre;ID;Descripción;Precio Compra;Precio Venta;Descuento;Stock");
                writer.newLine();

                for (int fila = 0; fila < tableModel.getRowCount(); fila++) {
                    String nombre = tableModel.getValueAt(fila, 0).toString();
                    String id = tableModel.getValueAt(fila, 1).toString();
                    String descripcion = tableModel.getValueAt(fila, 2).toString();
                    double precioCompra = Double.parseDouble(tableModel.getValueAt(fila, 3).toString().replace("$", ""));
                    double precioVenta = Double.parseDouble(tableModel.getValueAt(fila, 4).toString().replace("$", ""));
                    double descuento = Double.parseDouble(tableModel.getValueAt(fila, 5).toString().replace("%", ""));
                    int stock = Integer.parseInt(tableModel.getValueAt(fila, 6).toString());

                    writer.write(String.format("%s;%s;%s;%.2f;%.2f;%.2f;%d",
                            nombre, id, descripcion, precioCompra, precioVenta, descuento, stock));
                    writer.newLine();
                }
            }
            System.out.println("Datos guardados exitosamente en: " + RUTA_ARCHIVO);
        } catch (IOException e) {
            System.err.println("Error al guardar los datos: " + e.getMessage());
        }
    }

    /**
     * Leer Items desde un archivo CSV a un DefaultTableModel.
     */
    public static DefaultTableModel leerItemsDesdeArchivo() {
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Nombre");
        columnNames.add("ID");
        columnNames.add("Descripción");
        columnNames.add("Precio Compra");
        columnNames.add("Precio Venta");
        columnNames.add("Descuento");
        columnNames.add("Stock");

        Vector<Vector<Object>> data = new Vector<>();

        try {
            Path archivoPath = Paths.get(RUTA_ARCHIVO);
            if (!Files.exists(archivoPath)) {
                Files.createDirectories(archivoPath.getParent());
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA_ARCHIVO))) {
                    writer.write("Nombre;ID;Descripción;Precio Compra;Precio Venta;Descuento;Stock");
                    writer.newLine();
                }
                return new DefaultTableModel(columnNames, 0);
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    if (!linea.trim().isEmpty() && !linea.startsWith("Nombre")) {
                        String[] datos = linea.split(";");
                        Vector<Object> row = new Vector<>();
                        row.add(datos[0]);       // Nombre
                        row.add(datos[1]);       // ID
                        row.add(datos[2]);       // Descripción
                        row.add("$" + Double.parseDouble(datos[3]));  // Precio Compra
                        row.add("$" + Double.parseDouble(datos[4]));  // Precio Venta
                        row.add(Double.parseDouble(datos[5]) + "%");  // Descuento
                        row.add(Integer.parseInt(datos[6]));          // Stock
                        data.add(row);
                    }
                }
            }
            System.out.println("Datos leídos exitosamente de: " + RUTA_ARCHIVO);
        } catch (IOException e) {
            System.err.println("Error al leer los datos: " + e.getMessage());
        }

        return new DefaultTableModel(data, columnNames);
    }
}

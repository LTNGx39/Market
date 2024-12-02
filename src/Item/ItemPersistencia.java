package Item;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import javax.swing.table.TableModel;

public class ItemPersistencia {
    // Ruta de la carpeta y archivo
    private static final String CARPETA = "src/data";
    private static final String RUTA_ARCHIVO = CARPETA + "/items.csv";

    /**
     * Método para guardar Items desde un TableModel a un archivo
     * @param tableModel TableModel que contiene los datos de los Items
     */
    public static void guardarItems(TableModel tableModel) {
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
                    double precioCompra = Double.parseDouble(tableModel.getValueAt(fila, 3).toString());
                    double precioVenta = Double.parseDouble(tableModel.getValueAt(fila, 4).toString());
                    double descuento = Double.parseDouble(tableModel.getValueAt(fila, 5).toString());
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
     * Método para leer Items desde un archivo y devolver un arreglo
     * @return Arreglo de Items leídos del archivo
     */
    public static Item[] leerItemsDesdeArchivo() {
        ArrayList<Item> items = new ArrayList<>();

        try {
            // Verificar si el archivo existe
            Path archivoPath = Paths.get(RUTA_ARCHIVO);
            if (!Files.exists(archivoPath)) {
                System.err.println("El archivo no existe en: " + RUTA_ARCHIVO);
                return new Item[0];
            }

            // Leer datos desde el archivo
            try (BufferedReader reader = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    String[] datos = linea.split(";");
                    Item item = new Item(
                            datos[0],
                            datos[1],
                            datos[2],
                            Double.parseDouble(datos[3]),
                            Double.parseDouble(datos[4]),
                            Double.parseDouble(datos[5]),
                            Integer.parseInt(datos[6]));
                    items.add(item);
                }
                System.out.println("Datos leídos exitosamente de: " + RUTA_ARCHIVO);
            }
        } catch (IOException e) {
            System.err.println("Error al leer los datos: " + e.getMessage());
        }

        return items.toArray(new Item[0]);
    }
}

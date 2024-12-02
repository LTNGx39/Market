package Item;

import java.io.*;
import java.util.ArrayList;
import javax.swing.table.TableModel;

public class ItemPersistencia {
    // Ruta predefinida para el archivo
    private static final String RUTA_ARCHIVO = "items.csv";

    /**
     * Método para guardar Items desde un TableModel a un archivo
     * @param tableModel TableModel que contiene los datos de los Items
     */
    public static void guardarItems(TableModel tableModel) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA_ARCHIVO))) {
            for (int fila = 0; fila < tableModel.getRowCount(); fila++) {
                // Recuperar datos de cada columna
                String nombre = tableModel.getValueAt(fila, 0).toString();
                String id = tableModel.getValueAt(fila, 1).toString();
                String descripcion = tableModel.getValueAt(fila, 2).toString();
                double precioCompra = Double.parseDouble(tableModel.getValueAt(fila, 3).toString());
                double precioVenta = Double.parseDouble(tableModel.getValueAt(fila, 4).toString());
                double descuento = Double.parseDouble(tableModel.getValueAt(fila, 5).toString());
                int stock = Integer.parseInt(tableModel.getValueAt(fila, 6).toString());

                // Escribir en archivo (formato CSV para simplicidad)
                writer.write(String.format("%s;%s;%s;%.2f;%.2f;%.2f;%d\n", 
                    nombre, id, descripcion, 
                    precioCompra, precioVenta, 
                    descuento, stock));
            }
            System.out.println("Datos guardados exitosamente en " + RUTA_ARCHIVO);
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
        
        try (BufferedReader reader = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                // Dividir línea por separador
                String[] datos = linea.split(";");
                
                // Crear Item con los datos
                Item item = new Item(
                    datos[0],      // nombre
                    datos[1],      // id
                    datos[2],      // descripcion
                    Double.parseDouble(datos[3]),  // precioCompra
                    Double.parseDouble(datos[4]),  // precioVenta
                    Double.parseDouble(datos[5]),  // descuento
                    Integer.parseInt(datos[6])     // stock
                );
                
                items.add(item);
            }
            System.out.println("Datos leídos exitosamente de " + RUTA_ARCHIVO);
        } catch (IOException e) {
            System.err.println("Error al leer los datos: " + e.getMessage());
        }
        
        return items.toArray(new Item[0]);
    }
}

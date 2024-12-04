package Item;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Item {
    // Atributos privados para encapsulación
    private String nombre;
    private String id;
    private String descripcion;
    private double precioCompra;
    private double precioVenta;
    private double descuento;
    private int stock;

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
    public double getPrecioCompra() {
        return precioCompra;
    }
    public double getPrecioVenta() {
        return precioVenta;
    }
    public double getDescuento() {
        return descuento;
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
    public double getPrecioConDescuento() {
        return precioVenta * (1 - descuento);
    }

    // Método toString para imprimir información del artículo
    @Override
    public String toString() {
        return "Item{" +
               "nombre='" + nombre + '\'' +
               ", id='" + id + '\'' +
               ", descripcion='" + descripcion + '\'' +
               ", precioCompra=$" + precioCompra +
               ", precioVenta=$" + precioVenta +
               ", descuento=" + descuento * 100 + "%" +
               ", stock=" + stock +
               '}';
    }

    // Método para leer artículos desde un archivo
    public static List<Item> leerDesdeArchivo(String rutaArchivo) throws IOException {
        List<Item> items = new ArrayList<>();
        try (BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea = lector.readLine(); // Leer encabezados

            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");

                // Validar que la línea tenga suficientes campos
                if (campos.length >= 7) {
                    try {
                        String nombre = campos[0];
                        String id = campos[1];
                        String descripcion = campos[2];
                        double precioCompra = Double.parseDouble(campos[3]);
                        double precioVenta = Double.parseDouble(campos[4]);
                        double descuento = Double.parseDouble(campos[5]) / 100;
                        int stock = Integer.parseInt(campos[6]);

                        // Crear objeto Item y agregar a la lista
                        Item item = new Item(nombre, id, descripcion, precioCompra, precioVenta, descuento, stock);
                        items.add(item);
                    } catch (Exception e) {
                        System.err.println("Error procesando línea: " + linea);
                    }
                }
            }
        }
        return items;
    }
}
package Item;

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
               ", precioCompra=" + precioCompra +
               ", precioVenta=" + precioVenta +
               ", descuento=" + descuento +
               ", stock=" + stock +
               '}';
    }
}
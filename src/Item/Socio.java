package Item;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Socio {
    // Enumeración para tipos de membresía
    public enum TipoMembresia {
        NORMAL(150.0),
        PREMIUM(450.0);

        private final double costo;

        TipoMembresia(double costo) {
            this.costo = costo;
        }

        public double getCosto() {
            return costo;
        }
    }

    // Atributos privados
    private String nombre;
    private String direccion;
    private String telefono;
    private String rfc;
    private String usuarioAdicional1;
    private String usuarioAdicional2;
    private LocalDate fechaInicio;
    private LocalDate fechaRenovacion;
    private boolean activa;
    private TipoMembresia tipoMembresia;
    private double cashback;

    // Constructor con todos los parámetros
    public Socio(String nombre, String direccion, String telefono, String rfc, 
                 TipoMembresia tipoMembresia, String usuarioAdicional1, 
                 String usuarioAdicional2, LocalDate fechaInicio) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.rfc = rfc;
        this.tipoMembresia = tipoMembresia;
        this.usuarioAdicional1 = usuarioAdicional1;
        this.usuarioAdicional2 = usuarioAdicional2;
        this.fechaInicio = fechaInicio != null ? fechaInicio : LocalDate.now();
        this.fechaRenovacion = this.fechaInicio.plusYears(1);
        this.activa = true;
        this.cashback = 0.0;
    }

    // Constructor sin los parámetros adicionales (para casos cuando no se tengan)
    public Socio(String nombre, String direccion, String telefono, String rfc, TipoMembresia tipoMembresia) {
        this(nombre, direccion, telefono, rfc, tipoMembresia, null, null, LocalDate.now());
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getRfc() {
        return rfc;
    }

    public String getUsuarioAdicional1() {
        return usuarioAdicional1;
    }

    public String getUsuarioAdicional2() {
        return usuarioAdicional2;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaRenovacion() {
        return fechaRenovacion;
    }

    public boolean isActiva() {
        return activa;
    }

    public TipoMembresia getTipoMembresia() {
        return tipoMembresia;
    }

    public double getCashback() {
        return cashback;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public boolean setUsuarioAdicional1(String usuarioAdicional1) {
        if (this.usuarioAdicional1 == null) {
            this.usuarioAdicional1 = usuarioAdicional1;
            return true;
        }
        return false;
    }

    public boolean setUsuarioAdicional2(String usuarioAdicional2) {
        if (this.usuarioAdicional2 == null && this.usuarioAdicional1 != null) {
            this.usuarioAdicional2 = usuarioAdicional2;
            return true;
        }
        return false;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
        this.fechaRenovacion = fechaInicio.plusYears(1);
    }

    public void setFechaRenovacion(LocalDate fechaRenovacion) {
        this.fechaRenovacion = fechaRenovacion;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public void setTipoMembresia(TipoMembresia tipoMembresia) {
        this.tipoMembresia = tipoMembresia;
    }

    public void setCashback(double cashback) {
        this.cashback = cashback;
    }


    public static TipoMembresia convertirAMembresia(String entrada) {
        if (entrada == null) return null;
        switch (entrada.trim().toUpperCase()) {
            case "NORMAL":
            case "NORM":
            case "NRM":
                return TipoMembresia.NORMAL;
            case "PREMIUM":
            case "PREM":
            case "PRM":
                return TipoMembresia.PREMIUM;
            default:
                throw new IllegalArgumentException("Tipo de membresía inválido:  " + entrada);
        }
    }
    
    // Método para verificar si la membresía está activa
    public boolean isMembresiaActiva() {
        LocalDate hoy = LocalDate.now();
        activa = !hoy.isAfter(fechaRenovacion);
        return activa;
    }

    // Métodos adicionales útiles
    public void agregarCashback(double montoCompra) {
        if (tipoMembresia == TipoMembresia.PREMIUM) {
            cashback += montoCompra * 0.05; // 5% de cashback
        }
    }

    public boolean usarCashback(double monto) {
        if (monto <= cashback) {
            cashback -= monto;
            return true;
        }
        return false;
    }

    public void renovarMembresia() {
        fechaRenovacion = fechaRenovacion.plusYears(1);
        activa = true;
    }

    public long getDiasHastaRenovacion() {
        return ChronoUnit.DAYS.between(LocalDate.now(), fechaRenovacion);
    }

    @Override
    public String toString() {
        return "Socio{" +
               "nombre='" + nombre + '\'' +
               ", rfc='" + rfc + '\'' +
               ", tipoMembresia=" + tipoMembresia +
               ", fechaRenovacion=" + fechaRenovacion +
               ", activa=" + activa +
               ", cashback=" + cashback +
               '}';
    }
}

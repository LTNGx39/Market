package Item;

import java.io.*;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Files {
    private String itemsFilePath;
    private String sociosFilePath;
    private static final String DELIMITER = ",";

    // Constructor
    public Files(String itemsFilePath, String sociosFilePath) {
        this.itemsFilePath = itemsFilePath;
        this.sociosFilePath = sociosFilePath;
    }

    // Método para leer archivo de Items
    public List<Item> readFileItems() {
        List<Item> items = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(itemsFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(DELIMITER);
                if (data.length == 7) {
                    Item item = new Item(
                        data[0].trim(),                    // nombre
                        data[1].trim(),                    // id
                        data[2].trim(),                    // descripcion
                        Double.parseDouble(data[3].trim()), // precioCompra
                        Double.parseDouble(data[4].trim()), // precioVenta
                        Double.parseDouble(data[5].trim()), // descuento
                        Integer.parseInt(data[6].trim())    // stock
                    );
                    items.add(item);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al leer el archivo de items: " + e.getMessage());
        }
        
        return items;
    }

    // Método para leer archivo de Socios
    public List<Socio> readFileSocios() {
        List<Socio> socios = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(sociosFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(DELIMITER);
                if (data.length == 11) {
                    Socio socio = new Socio(
                        data[0].trim(),                           // nombre
                        data[1].trim(),                           // direccion
                        data[2].trim(),                           // telefono
                        data[3].trim(),                           // rfc
                        Socio.TipoMembresia.valueOf(data[8].trim()) // tipoMembresia
                    );
                    
                    // Establecer usuarios adicionales si existen
                    if (!data[4].trim().isEmpty()) {
                        socio.setUsuarioAdicional1(data[4].trim());
                    }
                    if (!data[5].trim().isEmpty()) {
                        socio.setUsuarioAdicional2(data[5].trim());
                    }
                    
                    // Establecer fechas
                    socio.setFechaInicio(LocalDate.parse(data[6].trim()));
                    socio.setFechaRenovacion(LocalDate.parse(data[7].trim()));
                    
                    // Establecer estado y cashback
                    socio.setActiva(Boolean.parseBoolean(data[9].trim()));
                    socio.setCashback(Double.parseDouble(data[10].trim()));
                    
                    socios.add(socio);
                }
            }
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error al leer el archivo de socios: " + e.getMessage());
        }
        
        return socios;
    }

    // Método para guardar Items en archivo
    public void saveFileItems(List<Item> items) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(itemsFilePath))) {
            for (Item item : items) {
                String line = String.format("%s%s%s%s%s%s%s%s%s%s%s%s%s%s%d",
                    item.getNombre(), DELIMITER,
                    item.getId(), DELIMITER,
                    item.getDescripcion(), DELIMITER,
                    item.getPrecioConDescuento(), DELIMITER,
                    item.getPrecioVenta(), DELIMITER,
                    item.getDescuento(), DELIMITER,
                    item.getStock()
                );
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo de items: " + e.getMessage());
        }
    }

    // Método para guardar Socios en archivo
    public void saveFileSocios(List<Socio> socios) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(sociosFilePath))) {
            for (Socio socio : socios) {
                String line = String.format("%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s",
                    socio.getNombre(), DELIMITER,
                    socio.getDireccion(), DELIMITER,
                    socio.getTelefono(), DELIMITER,
                    socio.getRfc(), DELIMITER,
                    socio.getUsuarioAdicional1() != null ? socio.getUsuarioAdicional1() : "", DELIMITER,
                    socio.getUsuarioAdicional2() != null ? socio.getUsuarioAdicional2() : "", DELIMITER,
                    socio.getFechaInicio(), DELIMITER,
                    socio.getFechaRenovacion(), DELIMITER,
                    socio.getTipoMembresia(), DELIMITER,
                    socio.isActiva(), DELIMITER,
                    socio.getCashback()
                );
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo de socios: " + e.getMessage());
        }
    }

    // Métodos para cambiar las rutas de los archivos si es necesario
    public void setItemsFilePath(String itemsFilePath) {
        this.itemsFilePath = itemsFilePath;
    }

    public void setSociosFilePath(String sociosFilePath) {
        this.sociosFilePath = sociosFilePath;
    }

    // Métodos para obtener las rutas actuales
    public String getItemsFilePath() {
        return itemsFilePath;
    }

    public String getSociosFilePath() {
        return sociosFilePath;
    }

    // Método para verificar si los archivos existen
    public boolean filesExist() {
        File itemsFile = new File(itemsFilePath);
        File sociosFile = new File(sociosFilePath);
        return itemsFile.exists() && sociosFile.exists();
    }

    // Método para crear archivos si no existen
    public void createFiles() throws IOException {
        File itemsFile = new File(itemsFilePath);
        File sociosFile = new File(sociosFilePath);
        
        if (!itemsFile.exists()) {
            itemsFile.createNewFile();
        }
        if (!sociosFile.exists()) {
            sociosFile.createNewFile();
        }
    }

    public static void createDirectories(Path carpetaPath) {
        try {
            // Verifica si el directorio no existe
            if (!exists(carpetaPath)) {
                // Intenta crear el directorio, incluyendo directorios padre si no existen
                carpetaPath.toFile().mkdirs();
                
                // Verificación adicional
                if (!exists(carpetaPath)) {
                    throw new IOException("No se pudo crear el directorio: " + carpetaPath);
                }
                
                System.out.println("Directorio creado: " + carpetaPath);
            }
        } catch (Exception e) {
            System.err.println("Error al crear directorios: " + e.getMessage());
        }
    }

    public static boolean exists(Path carpetaPath) {
        // Verificación básica de existencia de carpeta o archivo
        return carpetaPath != null && carpetaPath.toFile().exists();
    }
}
# Informe Técnico del Archivo Java: Files.java
# Clase Principal: Files

La clase Files se utiliza para gestionar archivos relacionados con los objetos Item y Socio. Permite leer, guardar, y verificar la existencia de estos archivos, además de crear directorios si es necesario.


Variables Globales

    itemsFilePath (String):
        Ruta del archivo que almacena la información de los objetos Item.
        Se inicializa mediante el constructor y puede modificarse con el método setItemsFilePath.

    sociosFilePath (String):
        Ruta del archivo que contiene la información de los objetos Socio.
        Se inicializa mediante el constructor y puede modificarse con el método setSociosFilePath.

    DELIMITER (String - static final):
        Separador de campos utilizado para procesar los datos en los archivos (",").

Métodos Principales
1. readFileItems()

    Función: Lee datos de un archivo específico y los convierte en una lista de objetos Item.
    Detalles:
        Usa un bloque try-with-resources para manejar un BufferedReader, asegurando el cierre del recurso automáticamente.
        Cada línea se divide utilizando el delimitador, y los datos se asignan a un objeto Item si cumplen con el formato esperado.
        Maneja excepciones como IOException y NumberFormatException, informando cualquier error.
    Retorno: Una lista de objetos Item.

2. readFileSocios()

    Función: Lee datos de un archivo específico y los convierte en una lista de objetos Socio.
    Detalles:
        Similar a readFileItems, utiliza un BufferedReader y procesa cada línea.
        Configura atributos adicionales (usuarioAdicional1, usuarioAdicional2, fechas, estado y cashback`) si los datos están presentes.
        Maneja excepciones como IOException e IllegalArgumentException.
    Retorno: Una lista de objetos Socio.

3. saveFileItems(List<Item> items)

    Función: Escribe una lista de objetos Item en un archivo.
    Detalles:
        Utiliza un bloque try-with-resources para manejar un BufferedWriter.
        Cada objeto Item se serializa en una línea utilizando el delimitador definido.
        Captura y maneja excepciones de tipo IOException.

4. saveFileSocios(List<Socio> socios)

    Función: Escribe una lista de objetos Socio en un archivo.
    Detalles:
        Similar a saveFileItems, serializa cada objeto Socio en una línea del archivo.
        Maneja valores opcionales y garantiza que las líneas estén formateadas correctamente.
        Captura excepciones de tipo IOException.

5. filesExist()

    Función: Verifica la existencia de los archivos de Item y Socio.
    Detalles:
        Utiliza las rutas itemsFilePath y sociosFilePath.
        Retorna un valor booleano que indica si ambos archivos existen.

6. createFiles()

    Función: Crea los archivos de Item y Socio si no existen.
    Detalles:
        Usa la clase File para comprobar y crear los archivos.
        Lanza una excepción de tipo IOException si ocurre un error al crear los archivos.

7. createDirectories(Path carpetaPath)

    Función: Crea un directorio y sus padres si no existen.
    Detalles:
        Comprueba la existencia del directorio usando el método estático exists.
        Si no existe, intenta crearlo con mkdirs.
        Lanza una excepción de tipo IOException si falla la creación.

8. exists(Path carpetaPath)

    Función: Verifica si un directorio o archivo existe.
    Detalles:
        Retorna true si la ruta es válida y el archivo/directorio existe.


Bloques de Código Destacados

    Estructuras Try-With-Resources:
        Se utilizan en los métodos de lectura y escritura (readFileItems, readFileSocios, saveFileItems, saveFileSocios) para asegurar que los recursos como BufferedReader y BufferedWriter se cierren automáticamente, evitando fugas de memoria.

    Manejo de Excepciones:
        Las excepciones comunes (IOException, NumberFormatException, IllegalArgumentException) son capturadas e informadas al usuario a través de mensajes de error.

    Uso de Métodos Estáticos:
        Métodos como createDirectories y exists facilitan tareas comunes relacionadas con archivos y directorios.



Notas Finales

    La clase Files está diseñada para gestionar de manera robusta y modular los archivos de datos, asegurando la persistencia y la integridad de los objetos Item y Socio.
    Hace un uso eficiente de las herramientas estándar de Java para manejo de archivos y excepciones, lo que refuerza su fiabilidad y legibilidad.




# Reporte Técnico
# Nombre de la Clase: Item

Resumen General:

La clase Item representa un artículo en un sistema de gestión de inventarios. Define atributos relacionados con la descripción del artículo, métodos para manipular datos como compra, cálculo de precios, gestión de stock, y funciones estáticas para interactuar con un archivo CSV. Además, proporciona utilidades para integrarse con interfaces gráficas mediante el uso de DefaultTableModel.

Atributos Globales Importantes:

    Atributos de instancia:
        nombre (String): Nombre del artículo.
        id (String): Identificador único del artículo.
        descripcion (String): Descripción del artículo.
        precioCompra (double): Precio de compra del artículo.
        precioVenta (double): Precio de venta del artículo.
        descuento (int): Porcentaje de descuento aplicado al artículo.
        stock (int): Cantidad disponible en inventario.

    Constantes:
        CARPETA (String): Ruta de la carpeta que contiene los archivos de datos.
        RUTA_ARCHIVO (String): Ruta completa al archivo CSV donde se almacenan los datos de los artículos.
        STOCK_MINIMO (int): Umbral de stock mínimo para generar una alerta.



Métodos Principales:

    Constructor:
        Item(String nombre, String id, String descripcion, double precioCompra, double precioVenta, int descuento, int stock)
            Inicializa una instancia con los valores especificados.

    Gestión de stock:
        boolean compraArticulo(int cantidad)
            Reduce el stock si hay suficiente inventario. Genera una alerta si el stock cae por debajo de STOCK_MINIMO.
            Excepciones manejadas:
                IllegalArgumentException si la cantidad es menor o igual a cero.
        static boolean reducirStock(String id, int cantidad)
            Busca un artículo por su ID en el archivo CSV y reduce el stock en la cantidad especificada.
            Bloques clave:
                Lee el archivo línea por línea para actualizar el stock.
                Escribe nuevamente el archivo tras realizar cambios.

    Gestión de precios:
        String getPrecioConDescuento()
            Calcula y retorna el precio de venta tras aplicar el descuento.
        String[] obtenerDetallesItem(String id)
            Devuelve información detallada del artículo, incluyendo precio con descuento.

    Interacción con archivos:
        static void guardarItems(DefaultTableModel tableModel)
            Escribe los datos de un modelo de tabla en el archivo CSV.
            Verifica y crea la carpeta necesaria si no existe.
        static DefaultTableModel leerItemsDesdeArchivo()
            Lee el archivo CSV y retorna un modelo de tabla (DefaultTableModel) con los datos cargados.
        static boolean existeID(String id)
            Busca un ID específico en el archivo para determinar si existe.

    Gestión de alertas:
        private void verificarStockMinimo()
            Imprime un mensaje de alerta en consola si el stock del artículo cae por debajo de STOCK_MINIMO.

    Métodos auxiliares:
        @Override String toString()
            Retorna una representación en formato texto de los datos del artículo.



Explicación de Bloques Importantes:

    Bloque de manejo de archivos:
        Los métodos guardarItems y leerItemsDesdeArchivo usan estructuras try-with-resources para garantizar el cierre de los recursos de escritura y lectura.
        Verifican la existencia de directorios y crean nuevos si es necesario, utilizando utilidades como Paths y Files.

    Bloque de verificación de stock:
        El método verificarStockMinimo se ejecuta tras cada compra para asegurar que el stock no baje del nivel mínimo definido. Este diseño garantiza la detección y notificación inmediata de problemas de inventario.

    Estructuras de control:
        Ciclo en reducirStock:
            Recorre todas las líneas del archivo CSV, identifica el artículo correspondiente y actualiza su stock, asegurando consistencia en los datos.
        Bloque condicional en existeID:
            Implementa un flujo para detectar la existencia del ID con comprobaciones de línea vacía o encabezados.



# Reporte Técnico
# Nombre de la Clase: Miembros


Esta clase pertenece al paquete Item y se encarga de la gestión de socios, incluyendo lectura y escritura de datos desde/para un archivo CSV, manejo de fechas de membresía, y representación de los datos en una tabla (DefaultTableModel).


Variables Globales Más Importantes

    RUTA_ARCHIVO:
        Tipo: String
        Descripción: Define la ruta del archivo CSV donde se almacenan los datos de los socios.
        Uso: Se utiliza como valor predeterminado para la ruta de los archivos en operaciones de entrada y salida.


Métodos Más Importantes
a) calcularFechaVencimiento

    Propósito:
        Calcula la fecha de vencimiento de una membresía, sumando un año a una fecha de inicio dada.
    Parámetros:
        LocalDate fechaInicio: Fecha de inicio de la membresía.
    Retorno:
        Una cadena formateada (dd/MM/yyyy) que representa la fecha de vencimiento.
        Retorna una cadena vacía si la fecha de inicio es null.

b) leerSociosDesdeArchivo

    Propósito:
        Lee un archivo CSV que contiene información de los socios y construye un modelo de tabla (DefaultTableModel) para representar los datos.
    Parámetros:
        String rutaArchivo: Ruta del archivo CSV a leer.
    Retorno:
        Un objeto DefaultTableModel que contiene los datos de los socios.
    Detalles Importantes:
        Control de errores:
            Maneja excepciones relacionadas con la lectura del archivo usando un bloque try-catch.
            Registra errores en líneas específicas del archivo con mensajes detallados.
        Lógica de lectura:
            Se asegura de que cada línea tenga al menos 9 campos (para incluir fechas y cashback).
            Se implementa soporte para múltiples formatos de fecha (dd/MM/yyyy, yyyy-MM-dd, yyyy/MM/dd) mediante un ciclo de prueba.
        Cálculo de fecha de fin:
            Si no se proporciona una fecha de fin válida, se calcula como un año después de la fecha de inicio.

c) convertirSociosATableModel

    Propósito:
        Convierte una lista de objetos Socio en un modelo de tabla (DefaultTableModel).
    Parámetros:
        List<Socio> socios: Lista de objetos Socio.
    Retorno:
        Un objeto DefaultTableModel con las columnas {"Nombre", "Tipo", "Dirección", "Teléfono", "RFC", "Add1", "Add2", "Fecha I.", "Fecha F.", "Cashback"}.
    Detalles Importantes:
        Cada fila de la tabla se genera usando los datos del objeto Socio y su formato.
        El método calcularFechaVencimiento se invoca para calcular dinámicamente la fecha de vencimiento.

d) guardarTableModelEnArchivo

    Propósito:
        Guarda los datos de un modelo de tabla (DefaultTableModel) en un archivo CSV.
    Parámetros:
        String rutaArchivo: Ruta del archivo donde se guardará la información.
        DefaultTableModel tableModel: Modelo de tabla con los datos a guardar.
    Detalles Importantes:
        Escritura:
            Los nombres de las columnas se escriben como la primera línea del archivo.
            Cada fila del modelo se recorre y escribe en formato CSV.
        Control de errores:
            Maneja posibles errores de escritura con un bloque try-catch.



Estructuras de Control Relevantes
a) Bloques try-catch

    Se utilizan extensivamente para manejar excepciones en:
        Lectura de archivos (FileReader):
            Captura excepciones de tipo IOException y registra un mensaje de error detallado.
        Parsing de fechas (LocalDate.parse):
            Implementados en un ciclo, intentan diferentes formatos de fecha y continúan en caso de error.

b) Ciclo para Formatos de Fecha

    Propósito:
        Probar múltiples formatos de fecha para asegurar compatibilidad.



Resumen del Flujo de Trabajo

    Lectura:
        El método leerSociosDesdeArchivo lee un archivo CSV y procesa cada línea para extraer datos, manejando errores en el formato o contenido.
    Procesamiento:
        Los datos se convierten a objetos Socio y se almacenan en una lista.
    Conversión:
        La lista de objetos Socio se transforma en un DefaultTableModel con columnas predefinidas mediante convertirSociosATableModel.
    Escritura:
        Los datos del DefaultTableModel se guardan nuevamente en un archivo CSV mediante guardarTableModelEnArchivo.



# Reporte Técnico: Clase Socio
# Nombre de la Clase: Socio

Esta clase pertenece al paquete Item y modela la información y comportamientos asociados a un socio, incluyendo atributos personales, estado de la membresía, fechas importantes, y funcionalidades adicionales como manejo de cashback y renovación.


Variables Globales Más Importantes

    nombre, direccion, telefono, rfc:
        Tipo: String
        Descripción: Datos básicos del socio.

    usuarioAdicional1, usuarioAdicional2:
        Tipo: String
        Descripción: Usuarios adicionales permitidos bajo la misma membresía.

    fechaInicio, fechaRenovacion:
        Tipo: LocalDate
        Descripción: Fecha de inicio de la membresía y la próxima fecha de renovación.

    activa:
        Tipo: boolean
        Descripción: Indica si la membresía del socio está activa.

    tipoMembresia:
        Tipo: TipoMembresia (enumeración)
        Descripción: Tipo de membresía del socio, que puede ser NORMAL o PREMIUM.

    cashback:
        Tipo: double
        Descripción: Monto acumulado en cashback para socios con membresía PREMIUM.



Enumeración TipoMembresia

    Propósito:
        Representa los tipos de membresía disponibles: NORMAL y PREMIUM.
    Atributo Asociado:
        costo: Representa el costo anual de la membresía.
    Método Importante:
        getCosto:
            Retorna el costo asociado al tipo de membresía.



Métodos Más Importantes
a) Constructores

    Constructor Principal:
        Recibe todos los atributos necesarios para crear un objeto Socio.
        Inicializa fechas (fechaInicio y fechaRenovacion) y activa la membresía por defecto.
    Constructor Simplificado:
        Excluye los campos usuarioAdicional1, usuarioAdicional2 y permite usar la fecha actual como fecha de inicio.

b) convertirAMembresia

    Propósito:
        Convierte una cadena de texto a un valor de la enumeración TipoMembresia.
    Parámetros:
        String entrada: Texto que representa el tipo de membresía.
    Retorno:
        Un valor de TipoMembresia correspondiente a la entrada.
    Detalles:
        Reconoce variantes comunes del texto (NORMAL, NORM, NRM, PREMIUM, PREM, PRM).
        Lanza una excepción IllegalArgumentException si la entrada es inválida.

c) isMembresiaActiva

    Propósito:
        Verifica si la membresía sigue activa, comparando la fecha de renovación con la fecha actual.
    Retorno:
        Un valor booleano indicando si la membresía está activa.

d) agregarCashback

    Propósito:
        Calcula y agrega cashback al socio si su membresía es PREMIUM.
    Parámetros:
        double montoCompra: Monto de la compra realizada.
    Detalles:
        El cashback se calcula como el 5% del monto de la compra.

e) usarCashback

    Propósito:
        Permite al socio usar parte de su cashback acumulado.
    Parámetros:
        double monto: Monto a descontar del cashback.
    Retorno:
        Un valor booleano que indica si la operación fue exitosa (el cashback disponible es suficiente).

f) renovarMembresia

    Propósito:
        Extiende la fecha de renovación de la membresía por un año y la activa nuevamente.

g) getDiasHastaRenovacion

    Propósito:
        Calcula la cantidad de días restantes hasta la fecha de renovación de la membresía.
    Retorno:
        Un valor long que representa los días hasta la renovación.

h) toString

    Propósito:
        Retorna una representación en texto del objeto Socio, incluyendo datos clave como el nombre, tipo de membresía, fecha de renovación y estado.





Estructuras de Control Relevantes
a) Control de Inicialización en Constructores

    Propósito:
        Garantizar valores predeterminados válidos.

Bloque switch en convertirAMembresia

    Propósito:
        Validar y transformar las cadenas de texto en valores de la enumeración TipoMembresia.


Resumen del Flujo de Trabajo

    Creación del Objeto:
        Un objeto Socio se inicializa con datos básicos y, opcionalmente, datos adicionales.
    Estado y Renovación:
        La clase permite verificar el estado de la membresía, calcular días restantes hasta la renovación y extender la membresía.
    Manejo de Cashback:
        Incluye funcionalidades para acumular y utilizar cashback exclusivamente para socios con membresía PREMIUM.
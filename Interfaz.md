# Main

### Informe Técnico del Archivo Java: `Main.java`

#### **Clase Principal: `Main`**
La clase `Main` actúa como el punto de entrada para la ejecución del programa. Contiene un único método principal, `main`, que es el encargado de inicializar la interfaz gráfica del usuario (GUI) a través de la creación de una instancia de `MainFrame`.

---

#### **Métodos Principales**

##### 1. **Método `main`**
- **Descripción**: Método estático que se ejecuta al iniciar el programa.
- **Parámetros**:
  - `String[] args`: Argumentos de línea de comandos (sin uso en este caso).
- **Funcionalidad**:
  1. **Instancia la clase `MainFrame`**: Se crea un objeto de la clase `MainFrame`, con dos argumentos que indican las dimensiones iniciales de la ventana (`800` de ancho y `600` de alto).
  2. **Hace visible la ventana**: Llama al método `setVisible(true)` en el objeto `MainFrame`, lo que asegura que la ventana gráfica sea mostrada al usuario.

---

#### **Variables Globales**
Esta clase no define variables globales propias. La única instancia relevante (`MainFrame`) se maneja de forma local dentro del método `main`.

---

#### **Dependencias Externas**
- **Paquete Importado: `UI`**:
  - La clase depende de un paquete externo llamado `UI`. Este paquete contiene la clase `MainFrame`, que se utiliza para gestionar la interfaz gráfica.
  - No se proporciona más información sobre la implementación de `MainFrame` en este archivo.

---

#### **Notas Finales**
- **Finalidad del Archivo**: Este archivo tiene como objetivo iniciar la aplicación y lanzar la ventana principal de la interfaz gráfica.
- **Bloques de Código Relevantes**:
  - No existen bloques complejos, como bucles o estructuras `try-catch`, ya que el archivo se centra únicamente en la inicialización básica de la aplicación.

Este diseño sugiere una arquitectura modular, donde la responsabilidad de configurar y manejar la interfaz gráfica recae en la clase `MainFrame`, que debe estar definida en el paquete `UI`.
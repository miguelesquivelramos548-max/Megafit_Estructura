# 📘 Guía Práctica: Cómo Conectar tus JFrame Forms en NetBeans con MegaFit

¡Hola! Esta guía te muestra exactamente cómo crear tus **JFrame Forms** en NetBeans y conectarlos en una sola línea de código con todas las estructuras de datos que ya están implementadas y listas en el proyecto.

---

## 🛠️ Paso 1: Crear tu primer JFrame Form en NetBeans

1. Abre el proyecto **Megait_estructura** en NetBeans.
2. En la ventana de proyectos (izquierda), expande `Source Packages`.
3. Haz clic derecho sobre el paquete `com.megafit` (o crea un paquete nuevo, por ejemplo `com.megafit.vistas`):
   - Selecciona **New** -> **JFrame Form...**
   - Dale un nombre, por ejemplo: `FrmPrincipal` o `FrmSocios`.
   - Haz clic en **Finish**.
4. ¡Listo! NetBeans te abrirá el diseñador visual drag-and-drop (*Design* y *Source*).

---

## 🔌 Paso 2: Importar el Servicio Central en tu JFrame

En la pestaña **Source** (Código) de tu JFrame, en los imports superiores agrega:

```java
import com.megafit.controller.MegaFitService;
import com.megafit.model.Membresia;
import com.megafit.model.Socio;
import javax.swing.JOptionPane;
```

Y dentro de tu clase JFrame, puedes tener una referencia rápida:

```java
public class FrmPrincipal extends javax.swing.JFrame {

    // Instancia única compartida del servicio (Singleton)
    private MegaFitService service = MegaFitService.getInstancia();

    public FrmPrincipal() {
        initComponents();
        this.setLocationRelativeTo(null); // Centrar en pantalla
        cargarTablaSocios(); // Llena tu JTable con los datos ya precargados
    }
```

---

## 💡 Paso 3: Ejemplos de Código para los Botones de tu Formulario

### 1. Llenar tu JTable de Socios (en 1 sola línea)
Crea este método en tu JFrame y llámalo cada vez que registres, elimines o abras la ventana:
```java
private void cargarTablaSocios() {
    // Reemplaza 'tblSocios' por el nombre de tu variable JTable
    tblSocios.setModel(service.obtenerModeloTablaSocios());
}
```

---

### 2. Botón "Registrar Socio"
Haz doble clic sobre tu botón en el Diseñador de NetBeans y pega esto:
```java
private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {
    String dni = txtDni.getText().trim();
    String nombre = txtNombre.getText().trim();
    String telefono = txtTelefono.getText().trim();
    int edad = Integer.parseInt(spnEdad.getValue().toString());
    String genero = cmbGenero.getSelectedItem().toString();
    
    // Obtener membresía
    Membresia plan = Membresia.desdeTexto(cmbMembresia.getSelectedItem().toString());
    String estado = cmbEstado.getSelectedItem().toString();

    // Guardar en la Lista Doblemente Enlazada, Árbol BST y Pila LIFO:
    boolean exito = service.registrarSocio(dni, nombre, edad, genero, telefono, plan, estado);

    if (exito) {
        JOptionPane.showMessageDialog(this, "¡Socio registrado exitosamente!");
        cargarTablaSocios();
        limpiarCampos();
    } else {
        JOptionPane.showMessageDialog(this, "Error: Verifique los datos o el DNI ya existe.");
    }
}
```

---

### 3. Botón "Buscar Socio por DNI" (Usa Árbol Binario BST O(log N))
```java
private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {
    String dni = txtBuscarDni.getText().trim();
    
    // Búsqueda eficiente en Árbol Binario de Búsqueda:
    Socio s = service.buscarPorDni(dni);

    if (s != null) {
        txtNombre.setText(s.getNombre());
        txtTelefono.setText(s.getTelefono());
        spnEdad.setValue(s.getEdad());
        JOptionPane.showMessageDialog(this, "Socio encontrado mediante Árbol Binario (BST):\n" + s.getNombre());
    } else {
        JOptionPane.showMessageDialog(this, "No se encontró ningún socio con el DNI: " + dni);
    }
}
```

---

### 4. Botón "Eliminar Socio Seleccionado"
```java
private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {
    int fila = tblSocios.getSelectedRow();
    if (fila == -1) {
        JOptionPane.showMessageDialog(this, "Seleccione un socio en la tabla.");
        return;
    }

    String dni = tblSocios.getValueAt(fila, 1).toString();
    
    int resp = JOptionPane.showConfirmDialog(this, "¿Seguro de eliminar al socio con DNI " + dni + "?");
    if (resp == JOptionPane.YES_OPTION) {
        service.eliminarSocioPorDni(dni);
        cargarTablaSocios();
        JOptionPane.showMessageDialog(this, "Socio eliminado.");
    }
}
```

---

### 5. Botón "Ordenar Alfabéticamente A-Z" (Algoritmo Burbuja)
```java
private void btnOrdenarAZActionPerformed(java.awt.event.ActionEvent evt) {
    service.ordenarSociosPorNombreAZ();
    cargarTablaSocios();
}
```

---

### 6. Control de Acceso con Cola FIFO (Recepción / Torniquete)

- **Para encolar un socio:**
```java
private void btnEncolarActionPerformed(java.awt.event.ActionEvent evt) {
    String dni = txtDniAcceso.getText().trim();
    boolean ok = service.encolarSocioPorDni(dni);
    if (ok) {
        tblCola.setModel(service.obtenerModeloTablaCola());
        JOptionPane.showMessageDialog(this, "Socio agregado a la cola de espera.");
    } else {
        JOptionPane.showMessageDialog(this, "No existe el socio o ya está en la cola.");
    }
}
```

- **Para atender al siguiente (Desencolar - FIFO):**
```java
private void btnDarAccesoActionPerformed(java.awt.event.ActionEvent evt) {
    Socio s = service.atenderSiguienteAcceso();
    if (s != null) {
        tblCola.setModel(service.obtenerModeloTablaCola());
        JOptionPane.showMessageDialog(this, "¡Pase autorizado por torniquete!\nBienvenido(a): " + s.getNombre());
    } else {
        JOptionPane.showMessageDialog(this, "La cola de espera está vacía.");
    }
}
```

---

### 7. Botón "Deshacer Última Operación" (Pila LIFO - Undo)
```java
private void btnDeshacerActionPerformed(java.awt.event.ActionEvent evt) {
    var accion = service.deshacerUltimaAccion();
    if (accion != null) {
        cargarTablaSocios();
        JOptionPane.showMessageDialog(this, "Acción revertida desde la Pila:\n" + accion.getDescripcion());
    } else {
        JOptionPane.showMessageDialog(this, "No hay acciones pendientes para deshacer.");
    }
}
```

---

## 🚀 Resumen de Estructuras Disponibles
Todas listas en `com.megafit.structures`:
1. `ListaEnlazadaSocios`: Lista Doblemente Enlazada con Nodos bidireccionales.
2. `ArbolBinarioSocios`: Árbol Binario de Búsqueda (BST) con recorridos InOrden, PreOrden y PostOrden.
3. `ColaAcceso`: Cola FIFO para recepción y acceso.
4. `PilaHistorial`: Pila LIFO para historial y motor Deshacer.
5. `MegaFitService`: Administrador Singleton que conecta todo.

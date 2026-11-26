# 🧭 Guía de pasos: crear los JFrame Form de MegaFit (paquete `com.megafit.vistas`)

Los diseños (`.java` + `.form`) los genera NetBeans cuando arrastras componentes, por eso esta guía te lleva
paso a paso. El código de los botones ya está listo en `GUIA_JFRAME_NETBEANS.md`: aquí solo lo conectas.

La carpeta del paquete ya existe: `src/com/megafit/vistas/`.

---

## Formularios que vas a crear

| Formulario    | Para qué sirve                          | Estructura que usa            |
|---------------|-----------------------------------------|-------------------------------|
| `FrmPrincipal`| Menú con botones que abren los demás    | —                             |
| `FrmSocios`   | Registrar, buscar, eliminar, ordenar    | Lista enlazada + Árbol BST    |
| `FrmAcceso`   | Recepción / torniquete                  | Cola FIFO                     |
| `FrmHistorial`| Auditoría y deshacer                    | Pila LIFO                     |

---

## Paso 1 — Abrir el proyecto
1. NetBeans → **File → Open Project…** (`Ctrl+Shift+O`) y elige la carpeta `Megait_estructura`.
2. En **Projects** expande `Source Packages`. Debes ver `com.megafit` y `com.megafit.vistas`.
   - Si `vistas` no aparece: clic derecho en `com.megafit` → **New → Java Package…** → nombre `com.megafit.vistas`.

## Paso 2 — Abrir el formulario (ya vienen creados)
Los 4 formularios (`FrmPrincipal`, `FrmSocios`, `FrmAcceso`, `FrmHistorial`) ya están en `com.megafit.vistas`, vacíos.
1. Doble clic sobre el formulario (por ejemplo `FrmSocios`).
2. Entra a la pestaña **Design**: ahí empiezas a arrastrar componentes.
   - Para crear uno extra: clic derecho en `com.megafit.vistas` → **New → JFrame Form…**

## Paso 3 — Diseñar (arrastrar desde la Palette)
Arrastra los componentes desde la **Palette** (derecha) al formulario. Para `FrmSocios`:

| Componente (Palette)        | Cantidad | Nombre de variable      |
|-----------------------------|----------|-------------------------|
| Label                       | varios   | (no importa)            |
| Text Field                  | 3        | `txtDni`, `txtNombre`, `txtTelefono` |
| Spinner                     | 1        | `spnEdad`               |
| Combo Box                   | 3        | `cmbGenero`, `cmbMembresia`, `cmbEstado` |
| Text Field (búsqueda)       | 1        | `txtBuscarDni`          |
| Table (viene dentro de un Scroll Pane) | 1 | `tblSocios`      |
| Button                      | 4        | `btnRegistrar`, `btnBuscar`, `btnEliminar`, `btnOrdenarAZ` |

Para `FrmAcceso`: `txtDniAcceso`, `btnEncolar`, `btnDarAcceso`, `tblCola`.
Para `FrmHistorial`: `btnDeshacer` y una tabla o `Text Area` para mostrar el historial.
Para `FrmPrincipal`: un botón por cada pantalla (`btnSocios`, `btnAcceso`, `btnHistorial`).

## Paso 4 — Poner los nombres de variable (¡importante!)
El código de la guía usa estos nombres exactos. Para cambiar uno:
1. Clic derecho sobre el componente → **Change Variable Name…** (o selecciónalo y pulsa **F2**).
2. Escribe el nombre de la tabla de arriba → **OK**.

Para el texto visible del botón: clic derecho → **Edit Text**.

## Paso 5 — Llenar los combos
1. Selecciona el combo → panel **Properties** → propiedad **model** → botón **…**
2. Escribe un elemento por línea. Ejemplos:
   - `cmbGenero`: Masculino, Femenino
   - `cmbEstado`: Activo, Inactivo
   - `cmbMembresia`: los textos que acepte `Membresia.desdeTexto(...)` (revisa `Membresia.java`).
3. En `spnEdad`, propiedad **model** → valor inicial 18, mínimo 12.

## Paso 6 — Conectar con el servicio (pestaña Source)
1. Arriba, debajo de `package com.megafit.vistas;`, agrega:
```java
import com.megafit.controller.MegaFitService;
import com.megafit.model.Membresia;
import com.megafit.model.Socio;
import javax.swing.JOptionPane;
```
2. Dentro de la clase y el constructor:
```java
private MegaFitService service = MegaFitService.getInstancia();

public FrmSocios() {
    initComponents();
    setLocationRelativeTo(null);   // centra la ventana
    cargarTablaSocios();
}

private void cargarTablaSocios() {
    tblSocios.setModel(service.obtenerModeloTablaSocios());
}

private void limpiarCampos() {
    txtDni.setText("");
    txtNombre.setText("");
    txtTelefono.setText("");
    spnEdad.setValue(18);
    cmbGenero.setSelectedIndex(0);
    cmbMembresia.setSelectedIndex(0);
    cmbEstado.setSelectedIndex(0);
}
```
   (`limpiarCampos()` es el método que el botón Registrar de la guía llama y que debes escribir tú.)

## Paso 7 — Programar cada botón
1. En **Design**, **doble clic** sobre el botón: NetBeans te lleva a `btnXxxActionPerformed`.
2. Pega dentro el código del botón correspondiente de `GUIA_JFRAME_NETBEANS.md`
   (Registrar, Buscar, Eliminar, Ordenar A-Z → `FrmSocios`; Encolar y Dar acceso → `FrmAcceso`; Deshacer → `FrmHistorial`).
3. **No edites** el bloque gris `initComponents()`: lo regenera NetBeans.

## Paso 8 — Abrir las ventanas desde el menú
En `FrmPrincipal`, doble clic en cada botón y escribe:
```java
// btnSocios
new FrmSocios().setVisible(true);
// btnAcceso
new FrmAcceso().setVisible(true);
// btnHistorial
new FrmHistorial().setVisible(true);
```
Para que cerrar una ventana secundaria no cierre todo el programa:
selecciona el formulario en **Design** → Properties → **defaultCloseOperation** → `DISPOSE`
(en `FrmPrincipal` déjalo en `EXIT`).

## Paso 9 — Que el programa inicie con la ventana
En `Main.java`, dentro de `main`, reemplaza lo que imprima en consola por:
```java
java.awt.EventQueue.invokeLater(() -> new com.megafit.vistas.FrmPrincipal().setVisible(true));
```
Ejecuta con **F6** (proyecto) o **Shift+F6** (solo el formulario abierto).

---

## ✅ Si algo falla
- **"cannot find symbol: txtDni"** → el nombre de variable no coincide (Paso 4).
- **"cannot find symbol: limpiarCampos"** → falta pegar ese método (Paso 6).
- **Error en `var accion = …`** → `var` pide Java 10+; cambia a `Project → Properties → Sources → Source/Binary Format` 11 o superior, o cambia `var` por el tipo real.
- **La tabla sale vacía** → revisa que llamas a `cargarTablaSocios()` en el constructor.
- **Índice de la columna DNI** (`getValueAt(fila, 1)`) → comprueba que la columna 1 de tu tabla sea el DNI.

---

## 🛟 Si NetBeans no abre la carpeta como proyecto
1. **File → New Project… → Java with Ant → Java Application** (nómbralo `Megait_estructura`, desmarca "Create Main Class").
2. Copia tus carpetas de código (`controller`, `model`, `structures`, `Main.java`) y la carpeta `vistas` dentro de
   `Megait_estructura/src/com/megafit/` (cópialas con el Explorador de archivos, no desde NetBeans).
3. En NetBeans, clic derecho sobre el proyecto → **Clean and Build** y listo.

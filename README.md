# ⚡ MegaFit Gym - Sistema de Gestión en Memoria (Estructura de Datos)

Proyecto desarrollado para el curso de **Estructura de Datos** en **Java**, configurado como proyecto nativo de **NetBeans IDE**.

> [!IMPORTANT]
> **Sin Formularios JFrame Preconstruidos**: Este proyecto contiene toda la lógica de negocio, modelos y las estructuras de datos puras en memoria. La capa de interfaz gráfica (JFrame Forms) la diseñarás directamente en NetBeans usando el editor visual a tu gusto.
> 
> Revisa el archivo **`GUIA_JFRAME_NETBEANS.md`** incluido en este proyecto para ver ejemplos de código de una sola línea para conectar tus botones y tablas.
>
> El servicio `MegaFitService` ya precarga **300 socios** en memoria. Los formularios siguen vacíos para que los diseñes en NetBeans.

---

## 🎯 Estructuras de Datos Implementadas en Memoria RAM

Todas implementadas desde cero mediante nodos y punteros (sin colecciones de Java para la lógica interna):

1. **Lista Doblemente Enlazada (`ListaEnlazadaSocios`)**:
   - Punteros `siguiente` y `anterior`.
   - Inserción al inicio, final y posición \(O(1)\).
   - Búsqueda secuencial y filtrado dinámico.
   - Eliminación de nodos con reconexión de punteros.
   - Algoritmo de **Ordenamiento Burbuja** alfabético (A-Z) y por ID/cuota.

2. **Árbol Binario de Búsqueda BST (`ArbolBinarioSocios`)**:
   - Punteros `izquierdo` y `derecho`.
   - Indexación por DNI para búsqueda binaria de alta velocidad \(O(\log N)\).
   - Recorridos clásicos: **InOrden** (lista naturalmente ordenada por DNI), **PreOrden** y **PostOrden**.
   - Cálculo de altura del árbol.

3. **Cola FIFO (`ColaAcceso`)**:
   - Control de turnos de recepción y torniquetes (First In, First Out).
   - Operaciones `encolar(Socio)` y `desencolar()` para dar acceso.

4. **Pila LIFO (`PilaHistorial`)**:
   - Historial de auditoría cronológica inversa (Last In, First Out).
   - Motor de **Deshacer (Undo / Rollback)**: al desapilar, revierte registros o eliminaciones.

5. **Servicio Central (`MegaFitService`)**:
   - Patrón Singleton que conecta todas las estructuras.
   - Genera `DefaultTableModel` listos para enlazar con cualquier `JTable` de tus JFrame Forms.

---

## 🚀 Cómo Abrir y Ejecutar en NetBeans

1. Abre **NetBeans IDE**.
2. Ve a **File** -> **Open Project...** (o pulsa `Ctrl + Shift + O`).
3. Selecciona la carpeta **`Megait_estructura`** (NetBeans la reconocerá automáticamente).
4. Para probar la consola y verificar que todo compila, presiona **F6** o clic derecho en el proyecto -> **Run**.
5. Para crear tus formularios: Clic derecho en el paquete `com.megafit` -> **New** -> **JFrame Form...** y sigue la **`GUIA_JFRAME_NETBEANS.md`**.

---

## 🪟 Paquete de formularios
La carpeta `src/com/megafit/vistas/` está lista para tus JFrame Forms. Sigue **`GUIA_PASOS_FORMULARIOS.md`** para crearlos y conectarlos.

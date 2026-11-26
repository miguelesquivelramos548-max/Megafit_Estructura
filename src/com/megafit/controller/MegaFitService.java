package com.megafit.controller;

import com.megafit.model.AccionHistorial;
import com.megafit.model.Membresia;
import com.megafit.model.Socio;
import com.megafit.structures.ArbolBinarioSocios;
import com.megafit.structures.ColaAcceso;
import com.megafit.structures.ListaEnlazadaSocios;
import com.megafit.structures.PilaHistorial;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.table.DefaultTableModel;

/**
 * Servicio central (Singleton). Conecta la lista doblemente enlazada, el árbol
 * BST, la cola FIFO y la pila LIFO. Precarga exactamente 300 socios.
 */
public final class MegaFitService {

    private static final MegaFitService INSTANCIA = new MegaFitService();

    private final ListaEnlazadaSocios listaSocios = new ListaEnlazadaSocios();
    private final ArbolBinarioSocios arbolDni = new ArbolBinarioSocios();
    private final ColaAcceso colaAcceso = new ColaAcceso();
    private final PilaHistorial historial = new PilaHistorial();
    private int siguienteId = 1;

    private MegaFitService() {
        generarDatosIniciales();
    }

    public static MegaFitService getInstancia() {
        return INSTANCIA;
    }

    /** Alias corto por si se usa desde otra vista. */
    public static MegaFitService instancia() {
        return INSTANCIA;
    }

    private void generarDatosIniciales() {
        String[] nombres = {"Ana", "Carlos", "María", "José", "Laura", "Miguel", "Sofía", "Javier",
            "Valeria", "Andrés", "Camila", "Diego", "Fernanda", "Luis", "Mariana", "Pedro",
            "Gabriela", "Ricardo", "Daniela", "Alejandro", "Paola", "Sergio", "Natalia", "Emilio"};
        String[] apellidos = {"García", "Martínez", "López", "Hernández", "Pérez", "Gómez",
            "Díaz", "Álvarez", "Romero", "Torres", "Ruiz", "Ramírez", "Castro", "Ortega",
            "Vargas", "Rojas", "Mendoza", "Herrera", "Medina", "Aguilar"};
        String[] generos = {"Masculino", "Femenino"};
        String[] estados = {"Activo", "Activo", "Activo", "Activo", "Activo", "Inactivo"};
        Membresia[] planes = Membresia.values();
        Random azar = new Random(20261126);
        List<Socio> generados = new ArrayList<>();

        for (int i = 0; i < 300; i++) {
            String dni = String.format("%08d", 10000001 + i);
            Socio socio = new Socio(
                    siguienteId++,
                    dni,
                    nombres[azar.nextInt(nombres.length)] + " " + apellidos[azar.nextInt(apellidos.length)],
                    16 + azar.nextInt(50),
                    generos[azar.nextInt(generos.length)],
                    "6001" + String.format("%04d", azar.nextInt(10000)),
                    planes[azar.nextInt(planes.length)],
                    estados[azar.nextInt(estados.length)],
                    LocalDate.now().minusDays(azar.nextInt(1200))
            );
            listaSocios.insertarAlFinal(socio);
            generados.add(socio);
        }
        // Los DNI son correlativos: se insertan en orden aleatorio para que el
        // BST quede equilibrado y la búsqueda sea realmente O(log n).
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < generados.size(); i++) {
            indices.add(i);
        }
        Collections.shuffle(indices, new Random(77));
        for (int indice : indices) {
            arbolDni.insertar(generados.get(indice));
        }
    }

    // ------------------------------------------------------------------
    // Operaciones usadas por los JFrame Form (GUIA_JFRAME_NETBEANS.md)
    // ------------------------------------------------------------------

    public boolean registrarSocio(String dni, String nombre, int edad, String genero,
                                  String telefono, Membresia plan, String estado) {
        if (dni == null || dni.trim().isEmpty() || nombre == null || nombre.trim().isEmpty()) {
            return false;
        }
        String dniLimpio = dni.trim();
        if (arbolDni.buscar(dniLimpio) != null) {
            return false;
        }
        Socio socio = new Socio(siguienteId++, dniLimpio, nombre.trim(), edad,
                genero, telefono, plan, estado, LocalDate.now());
        listaSocios.insertarAlFinal(socio);
        arbolDni.insertar(socio);
        apilar("ALTA", "Registrado " + socio.getNombre() + " (DNI " + dniLimpio + ")",
                () -> {
                    listaSocios.eliminarPorDni(dniLimpio);
                    arbolDni.eliminar(dniLimpio);
                });
        return true;
    }

    public Socio buscarPorDni(String dni) {
        return arbolDni.buscar(dni == null ? null : dni.trim());
    }

    public boolean eliminarSocioPorDni(String dni) {
        if (dni == null) {
            return false;
        }
        String dniLimpio = dni.trim();
        Socio socio = arbolDni.buscar(dniLimpio);
        if (socio == null) {
            return false;
        }
        listaSocios.eliminarPorDni(dniLimpio);
        arbolDni.eliminar(dniLimpio);
        apilar("BAJA", "Eliminado " + socio.getNombre() + " (DNI " + dniLimpio + ")",
                () -> {
                    if (arbolDni.buscar(dniLimpio) == null) {
                        listaSocios.insertarAlFinal(socio);
                        arbolDni.insertar(socio);
                    }
                });
        return true;
    }

    public void ordenarSociosPorNombreAZ() {
        listaSocios.ordenarPorNombreAZ();
        apilar("ORDEN", "Socios ordenados A-Z por nombre", null);
    }

    public boolean encolarSocioPorDni(String dni) {
        Socio socio = buscarPorDni(dni);
        if (socio == null || colaAcceso.contiene(socio)) {
            return false;
        }
        colaAcceso.encolar(socio);
        apilar("COLA", "Encolado " + socio.getNombre() + " para acceso",
                () -> colaAcceso.eliminar(socio));
        return true;
    }

    public Socio atenderSiguienteAcceso() {
        Socio socio = colaAcceso.desencolar();
        if (socio != null) {
            apilar("ACCESO", "Acceso concedido a " + socio.getNombre(),
                    () -> colaAcceso.encolarAlFrente(socio));
        }
        return socio;
    }

    /** Desapila la última acción, ejecuta su rollback y la devuelve. */
    public AccionHistorial deshacerUltimaAccion() {
        AccionHistorial accion = historial.desapilar();
        if (accion == null) {
            return null;
        }
        accion.ejecutarReversion();
        return accion;
    }

    private void apilar(String tipo, String descripcion, Runnable revertir) {
        historial.apilar(new AccionHistorial(tipo, descripcion, revertir));
    }

    // ------------------------------------------------------------------
    // Modelos de tabla listos para setModel(...) en los formularios
    // ------------------------------------------------------------------

    public DefaultTableModel obtenerModeloTablaSocios() {
        String[] columnas = {"ID", "DNI", "Nombre", "Edad", "Género", "Teléfono",
            "Membresía", "Estado", "Ingreso"};
        List<Socio> socios = listaSocios.aLista();
        Object[][] filas = new Object[socios.size()][columnas.length];
        for (int i = 0; i < socios.size(); i++) {
            Socio s = socios.get(i);
            filas[i] = new Object[]{s.getId(), s.getDni(), s.getNombre(), s.getEdad(),
                s.getGenero(), s.getTelefono(), s.getMembresia().toString(), s.getEstado(),
                s.getFechaIngreso()};
        }
        return new DefaultTableModel(filas, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    public DefaultTableModel obtenerModeloTablaCola() {
        String[] columnas = {"Turno", "DNI", "Nombre", "Membresía"};
        List<Socio> socios = colaAcceso.aLista();
        Object[][] filas = new Object[socios.size()][columnas.length];
        for (int i = 0; i < socios.size(); i++) {
            Socio s = socios.get(i);
            filas[i] = new Object[]{i + 1, s.getDni(), s.getNombre(), s.getMembresia().toString()};
        }
        return new DefaultTableModel(filas, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    public DefaultTableModel obtenerModeloTablaHistorial() {
        String[] columnas = {"Fecha / hora", "Tipo", "Descripción"};
        List<AccionHistorial> acciones = historial.aLista();
        Object[][] filas = new Object[acciones.size()][columnas.length];
        for (int i = 0; i < acciones.size(); i++) {
            AccionHistorial a = acciones.get(i);
            filas[i] = new Object[]{a.getFecha(), a.getTipo(), a.getDescripcion()};
        }
        return new DefaultTableModel(filas, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    // ------------------------------------------------------------------
    // Consultas auxiliares
    // ------------------------------------------------------------------

    public int totalSocios() {
        return listaSocios.tamano();
    }

    public int nodosArbol() {
        return arbolDni.contarNodos();
    }

    public int alturaArbol() {
        return arbolDni.altura();
    }

    public int personasEnCola() {
        return colaAcceso.tamano();
    }

    public int accionesEnPila() {
        return historial.tamano();
    }

    public List<Socio> listarSocios() {
        return listaSocios.aLista();
    }

    public List<Socio> sociosPorArbol() {
        return arbolDni.inOrden();
    }

    public List<Socio> recorridoPreOrden() {
        return arbolDni.preOrden();
    }

    public List<Socio> recorridoPostOrden() {
        return arbolDni.postOrden();
    }
}

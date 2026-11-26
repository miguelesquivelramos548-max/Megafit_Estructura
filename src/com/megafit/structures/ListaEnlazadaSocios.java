package com.megafit.structures;

import com.megafit.model.Socio;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Lista doblemente enlazada de socios, con búsqueda, filtrado y ordenamiento burbuja. */
public class ListaEnlazadaSocios {

    private static class Nodo {
        Socio socio;
        Nodo siguiente;
        Nodo anterior;

        Nodo(Socio socio) {
            this.socio = socio;
        }
    }

    private Nodo cabeza;
    private Nodo cola;
    private int tamano;

    public void insertarAlInicio(Socio socio) {
        Nodo nuevo = new Nodo(socio);
        nuevo.siguiente = cabeza;
        if (cabeza != null) {
            cabeza.anterior = nuevo;
        } else {
            cola = nuevo;
        }
        cabeza = nuevo;
        tamano++;
    }

    public void insertarAlFinal(Socio socio) {
        Nodo nuevo = new Nodo(socio);
        if (cola == null) {
            cabeza = nuevo;
        } else {
            cola.siguiente = nuevo;
            nuevo.anterior = cola;
        }
        cola = nuevo;
        tamano++;
    }

    public void insertarEnPosicion(int posicion, Socio socio) {
        if (posicion <= 0) {
            insertarAlInicio(socio);
            return;
        }
        if (posicion >= tamano) {
            insertarAlFinal(socio);
            return;
        }
        Nodo actual = nodoEn(posicion);
        Nodo nuevo = new Nodo(socio);
        nuevo.anterior = actual.anterior;
        nuevo.siguiente = actual;
        actual.anterior.siguiente = nuevo;
        actual.anterior = nuevo;
        tamano++;
    }

    public Socio buscarPorDni(String dni) {
        Nodo actual = cabeza;
        while (actual != null) {
            if (Objects.equals(actual.socio.getDni(), dni)) {
                return actual.socio;
            }
            actual = actual.siguiente;
        }
        return null;
    }

    public boolean eliminarPorDni(String dni) {
        Nodo actual = cabeza;
        while (actual != null) {
            if (Objects.equals(actual.socio.getDni(), dni)) {
                if (actual.anterior == null) {
                    cabeza = actual.siguiente;
                } else {
                    actual.anterior.siguiente = actual.siguiente;
                }
                if (actual.siguiente == null) {
                    cola = actual.anterior;
                } else {
                    actual.siguiente.anterior = actual.anterior;
                }
                tamano--;
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    public List<Socio> filtrar(String texto) {
        String filtro = texto == null ? "" : texto.trim().toLowerCase();
        List<Socio> resultado = new ArrayList<>();
        Nodo actual = cabeza;
        while (actual != null) {
            Socio socio = actual.socio;
            if (filtro.isEmpty()
                    || socio.getNombre().toLowerCase().contains(filtro)
                    || socio.getDni().toLowerCase().contains(filtro)
                    || socio.getTelefono().toLowerCase().contains(filtro)) {
                resultado.add(socio);
            }
            actual = actual.siguiente;
        }
        return resultado;
    }

    public void ordenarPorNombreAZ() {
        ordenar((a, b) -> a.getNombre().compareToIgnoreCase(b.getNombre()));
    }

    public void ordenarPorId() {
        ordenar((a, b) -> Integer.compare(a.getId(), b.getId()));
    }

    public void ordenarPorCuota() {
        ordenar((a, b) -> Double.compare(a.getMembresia().getCuota(), b.getMembresia().getCuota()));
    }

    private void ordenar(java.util.Comparator<Socio> comparador) {
        for (Nodo i = cabeza; i != null; i = i.siguiente) {
            for (Nodo j = i.siguiente; j != null; j = j.siguiente) {
                if (comparador.compare(i.socio, j.socio) > 0) {
                    Socio temporal = i.socio;
                    i.socio = j.socio;
                    j.socio = temporal;
                }
            }
        }
    }

    public List<Socio> aLista() {
        List<Socio> resultado = new ArrayList<>(tamano);
        Nodo actual = cabeza;
        while (actual != null) {
            resultado.add(actual.socio);
            actual = actual.siguiente;
        }
        return resultado;
    }

    public int tamano() { return tamano; }
    public boolean estaVacia() { return tamano == 0; }

    public void vaciar() {
        cabeza = null;
        cola = null;
        tamano = 0;
    }

    private Nodo nodoEn(int indice) {
        Nodo actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.siguiente;
        }
        return actual;
    }
}

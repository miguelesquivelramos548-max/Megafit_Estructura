package com.megafit.structures;

import com.megafit.model.AccionHistorial;
import java.util.ArrayList;
import java.util.List;

/** Pila LIFO de auditoría. La cima es la última acción realizada. */
public class PilaHistorial {

    private static class Nodo {
        AccionHistorial accion;
        Nodo siguiente;

        Nodo(AccionHistorial accion) {
            this.accion = accion;
        }
    }

    private Nodo cima;
    private int tamano;

    public void apilar(AccionHistorial accion) {
        if (accion == null) {
            return;
        }
        Nodo nuevo = new Nodo(accion);
        nuevo.siguiente = cima;
        cima = nuevo;
        tamano++;
    }

    public AccionHistorial desapilar() {
        if (cima == null) {
            return null;
        }
        AccionHistorial accion = cima.accion;
        cima = cima.siguiente;
        tamano--;
        return accion;
    }

    public AccionHistorial verCima() {
        return cima == null ? null : cima.accion;
    }

    /** Devuelve las acciones desde la más reciente a la más antigua. */
    public List<AccionHistorial> aLista() {
        List<AccionHistorial> lista = new ArrayList<>(tamano);
        Nodo actual = cima;
        while (actual != null) {
            lista.add(actual.accion);
            actual = actual.siguiente;
        }
        return lista;
    }

    public int tamano() {
        return tamano;
    }

    public boolean estaVacia() {
        return tamano == 0;
    }

    public void vaciar() {
        cima = null;
        tamano = 0;
    }
}

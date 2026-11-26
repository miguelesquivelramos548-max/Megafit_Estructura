package com.megafit.structures;

import com.megafit.model.Socio;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Cola FIFO para el control de turnos de recepción y torniquete. */
public class ColaAcceso {

    private static class Nodo {
        Socio socio;
        Nodo siguiente;

        Nodo(Socio socio) {
            this.socio = socio;
        }
    }

    private Nodo frente;
    private Nodo fin;
    private int tamano;

    public void encolar(Socio socio) {
        Nodo nuevo = new Nodo(socio);
        if (fin == null) {
            frente = nuevo;
        } else {
            fin.siguiente = nuevo;
        }
        fin = nuevo;
        tamano++;
    }

    /** Inserta al frente; se usa para revertir un "dar acceso". */
    public void encolarAlFrente(Socio socio) {
        Nodo nuevo = new Nodo(socio);
        nuevo.siguiente = frente;
        frente = nuevo;
        if (fin == null) {
            fin = nuevo;
        }
        tamano++;
    }

    public Socio desencolar() {
        if (frente == null) {
            return null;
        }
        Socio socio = frente.socio;
        frente = frente.siguiente;
        if (frente == null) {
            fin = null;
        }
        tamano--;
        return socio;
    }

    public Socio verFrente() {
        return frente == null ? null : frente.socio;
    }

    public boolean contiene(Socio socio) {
        if (socio == null) {
            return false;
        }
        Nodo actual = frente;
        while (actual != null) {
            if (Objects.equals(actual.socio.getDni(), socio.getDni())) {
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    public boolean eliminar(Socio socio) {
        if (socio == null || frente == null) {
            return false;
        }
        List<Socio> restantes = new ArrayList<>();
        Nodo actual = frente;
        boolean removido = false;
        while (actual != null) {
            if (!removido && Objects.equals(actual.socio.getDni(), socio.getDni())) {
                removido = true;
            } else {
                restantes.add(actual.socio);
            }
            actual = actual.siguiente;
        }
        if (!removido) {
            return false;
        }
        vaciar();
        for (Socio restante : restantes) {
            encolar(restante);
        }
        return true;
    }

    public List<Socio> aLista() {
        List<Socio> lista = new ArrayList<>(tamano);
        Nodo actual = frente;
        while (actual != null) {
            lista.add(actual.socio);
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
        frente = null;
        fin = null;
        tamano = 0;
    }
}

package com.megafit.structures;

import com.megafit.model.Socio;
import java.util.ArrayList;
import java.util.List;

/** Árbol binario de búsqueda indexado por DNI (búsqueda promedio O(log n)). */
public class ArbolBinarioSocios {

    private static class Nodo {
        Socio socio;
        Nodo izquierdo;
        Nodo derecho;

        Nodo(Socio socio) {
            this.socio = socio;
        }
    }

    private Nodo raiz;

    public void insertar(Socio socio) {
        if (socio == null || socio.getDni() == null) {
            return;
        }
        raiz = insertar(raiz, socio);
    }

    private Nodo insertar(Nodo nodo, Socio socio) {
        if (nodo == null) {
            return new Nodo(socio);
        }
        int cmp = socio.getDni().compareTo(nodo.socio.getDni());
        if (cmp < 0) {
            nodo.izquierdo = insertar(nodo.izquierdo, socio);
        } else if (cmp > 0) {
            nodo.derecho = insertar(nodo.derecho, socio);
        }
        return nodo;
    }

    public Socio buscar(String dni) {
        if (dni == null) {
            return null;
        }
        Nodo actual = raiz;
        while (actual != null) {
            int cmp = dni.compareTo(actual.socio.getDni());
            if (cmp == 0) {
                return actual.socio;
            }
            actual = cmp < 0 ? actual.izquierdo : actual.derecho;
        }
        return null;
    }

    public boolean eliminar(String dni) {
        int antes = contarNodos();
        raiz = eliminar(raiz, dni);
        return contarNodos() < antes;
    }

    private Nodo eliminar(Nodo nodo, String dni) {
        if (nodo == null) {
            return null;
        }
        int cmp = dni.compareTo(nodo.socio.getDni());
        if (cmp < 0) {
            nodo.izquierdo = eliminar(nodo.izquierdo, dni);
        } else if (cmp > 0) {
            nodo.derecho = eliminar(nodo.derecho, dni);
        } else {
            if (nodo.izquierdo == null) {
                return nodo.derecho;
            }
            if (nodo.derecho == null) {
                return nodo.izquierdo;
            }
            Nodo sucesor = minimo(nodo.derecho);
            nodo.socio = sucesor.socio;
            nodo.derecho = eliminar(nodo.derecho, sucesor.socio.getDni());
        }
        return nodo;
    }

    private Nodo minimo(Nodo nodo) {
        while (nodo.izquierdo != null) {
            nodo = nodo.izquierdo;
        }
        return nodo;
    }

    public List<Socio> inOrden() {
        List<Socio> lista = new ArrayList<>();
        inOrden(raiz, lista);
        return lista;
    }

    private void inOrden(Nodo nodo, List<Socio> lista) {
        if (nodo == null) {
            return;
        }
        inOrden(nodo.izquierdo, lista);
        lista.add(nodo.socio);
        inOrden(nodo.derecho, lista);
    }

    public List<Socio> preOrden() {
        List<Socio> lista = new ArrayList<>();
        preOrden(raiz, lista);
        return lista;
    }

    private void preOrden(Nodo nodo, List<Socio> lista) {
        if (nodo == null) {
            return;
        }
        lista.add(nodo.socio);
        preOrden(nodo.izquierdo, lista);
        preOrden(nodo.derecho, lista);
    }

    public List<Socio> postOrden() {
        List<Socio> lista = new ArrayList<>();
        postOrden(raiz, lista);
        return lista;
    }

    private void postOrden(Nodo nodo, List<Socio> lista) {
        if (nodo == null) {
            return;
        }
        postOrden(nodo.izquierdo, lista);
        postOrden(nodo.derecho, lista);
        lista.add(nodo.socio);
    }

    public int altura() {
        return altura(raiz);
    }

    private int altura(Nodo nodo) {
        return nodo == null ? 0 : 1 + Math.max(altura(nodo.izquierdo), altura(nodo.derecho));
    }

    public int contarNodos() {
        return contarNodos(raiz);
    }

    private int contarNodos(Nodo nodo) {
        return nodo == null ? 0 : 1 + contarNodos(nodo.izquierdo) + contarNodos(nodo.derecho);
    }

    public void vaciar() {
        raiz = null;
    }
}

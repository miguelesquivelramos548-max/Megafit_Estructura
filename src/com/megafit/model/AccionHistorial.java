package com.megafit.model;

import java.time.LocalDateTime;

/** Registro apilado en la Pila LIFO. Puede llevar asociada una acción de rollback. */
public class AccionHistorial {

    private final String tipo;
    private final String descripcion;
    private final LocalDateTime fecha;
    private final Runnable revertir;

    public AccionHistorial(String tipo, String descripcion, Runnable revertir) {
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fecha = LocalDateTime.now();
        this.revertir = revertir;
    }

    public String getTipo() { return tipo; }
    public String getDescripcion() { return descripcion; }
    public LocalDateTime getFecha() { return fecha; }

    /** Ejecuta la reversión de la operación, si tiene una asociada. */
    public void ejecutarReversion() {
        if (revertir != null) {
            revertir.run();
        }
    }

    @Override
    public String toString() {
        return descripcion;
    }
}

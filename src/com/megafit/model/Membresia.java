package com.megafit.model;

/** Planes de MegaFit usados por los combos de los formularios. */
public enum Membresia {

    BASICA("Básica", 499.0, "Sala de pesas y cardio"),
    PREMIUM("Premium", 1299.0, "Pesas, cardio y clases grupales"),
    VIP("VIP", 3999.0, "Acceso total y entrenador personal");

    private final String texto;
    private final double cuota;
    private final String descripcion;

    Membresia(String texto, double cuota, String descripcion) {
        this.texto = texto;
        this.cuota = cuota;
        this.descripcion = descripcion;
    }

    public String getTexto() {
        return texto;
    }

    public double getCuota() {
        return cuota;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static Membresia desdeTexto(String texto) {
        if (texto == null) {
            return BASICA;
        }
        String buscado = texto.trim().toLowerCase();
        for (Membresia membresia : values()) {
            if (membresia.texto.toLowerCase().equals(buscado)
                    || membresia.name().toLowerCase().equals(buscado)) {
                return membresia;
            }
        }
        return BASICA;
    }

    @Override
    public String toString() {
        return texto;
    }
}

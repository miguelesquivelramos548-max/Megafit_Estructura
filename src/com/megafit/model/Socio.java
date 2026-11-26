package com.megafit.model;

import java.time.LocalDate;

/** Socio del gimnasio. La clave de indexación en el BST es el DNI. */
public class Socio {

    private int id;
    private String dni;
    private String nombre;
    private int edad;
    private String genero;
    private String telefono;
    private Membresia membresia;
    private String estado;
    private LocalDate fechaIngreso;

    public Socio(int id, String dni, String nombre, int edad, String genero, String telefono,
                 Membresia membresia, String estado, LocalDate fechaIngreso) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.edad = edad;
        this.genero = genero;
        this.telefono = telefono;
        this.membresia = membresia;
        this.estado = estado;
        this.fechaIngreso = fechaIngreso;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public Membresia getMembresia() { return membresia; }
    public void setMembresia(Membresia membresia) { this.membresia = membresia; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }

    @Override
    public String toString() {
        return "#" + id + " " + nombre + " (" + dni + ") - " + membresia;
    }
}

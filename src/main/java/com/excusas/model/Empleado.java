package com.excusas.model;

public class Empleado {
    private final String nombre;
    private final String email;
    private final int legajo;

    public Empleado(String nombre, String email, int legajo) {
        this.nombre = nombre;
        this.email = email;
        this.legajo = legajo;
    }

    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public int getLegajo() { return legajo; }
}

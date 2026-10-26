package com.excusas.model;

import com.excusas.state.ModoDesempeno;
import com.excusas.state.ModoNormal;

public class Empleado {
    private String nombre;
    private String email;
    private int legajo;
    private ModoDesempeno modo;

    public Empleado(String nombre, String email, int legajo) {
        this.nombre = nombre;
        this.email = email;
        this.legajo = legajo;
        this.modo = new ModoNormal();
    }

    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public int getLegajo() { return legajo; }

    public ModoDesempeno getModo() { return modo; }
    public void setModo(ModoDesempeno modo) { this.modo = modo; }

    public void tomarCafe() {
        this.modo.tomarCafe(this);
    }
    public void tomarDescanso() {
        this.modo.tomarDescanso(this);
    }
}
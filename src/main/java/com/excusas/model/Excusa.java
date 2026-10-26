package com.excusas.model;

public class Excusa {
    private Empleado empleado;
    private Motivo motivo;
    private boolean aceptada = false;

    public Excusa(Empleado empleado, Motivo motivo) {
        this.empleado = empleado;
        this.motivo = motivo;
    }

    public Motivo getMotivo() { return motivo; }
    public Empleado getEmpleado() { return empleado; }
    public boolean isAceptada() { return aceptada; }
    public void setAceptada(boolean aceptada) { this.aceptada = aceptada; }
}
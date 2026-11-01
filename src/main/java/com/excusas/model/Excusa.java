package com.excusas.model;

import java.util.Set;

public abstract class Excusa {
    public static final String MENSAJE_RECHAZO = "Excusa rechazada: necesitamos pruebas contundentes";

    private final Empleado empleado;
    private final Motivo motivo;
    private boolean aceptada;
    private String respuestaFinal;

    protected Excusa(Empleado empleado, Motivo motivo) {
        if (!motivosValidos().contains(motivo)) {
            throw new IllegalArgumentException(motivo + " no es un motivo de " + getClass().getSimpleName());
        }
        this.empleado = empleado;
        this.motivo = motivo;
    }

    protected abstract Set<Motivo> motivosValidos();

    public void aceptar() { this.aceptada = true; }
    public void rechazarPorFaltaDePruebas() { this.aceptada = false; this.respuestaFinal = MENSAJE_RECHAZO; }

    public Empleado getEmpleado() { return empleado; }
    public Motivo getMotivo() { return motivo; }
    public boolean isAceptada() { return aceptada; }
    public String getRespuestaFinal() { return respuestaFinal; }
}

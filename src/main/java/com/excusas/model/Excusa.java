package com.excusas.model;

public class Excusa {
    private Empleado empleado;
    private NivelComplejidad nivel;
    private String descripcion;
    private boolean aceptada;
    private String respuestaFinal;

    public Excusa(Empleado empleado, NivelComplejidad nivel, String descripcion) {
        this.empleado = empleado;
        this.nivel = nivel;
        this.descripcion = descripcion;
        this.aceptada = false;
    }

    public Empleado getEmpleado() { return empleado; }
    public NivelComplejidad getNivel() { return nivel; }
    public String getDescripcion() { return descripcion; }

    public boolean isAceptada() { return aceptada; }
    public void setAceptada(boolean aceptada) { this.aceptada = aceptada; }

    public String getRespuestaFinal() { return respuestaFinal; }
    public void setRespuestaFinal(String respuestaFinal) { this.respuestaFinal = respuestaFinal; }
}
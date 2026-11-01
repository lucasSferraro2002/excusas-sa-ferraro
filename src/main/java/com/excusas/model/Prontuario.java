package com.excusas.model;

public class Prontuario {
    private final Empleado empleado;
    private final Excusa excusa;
    private final int legajo;

    public Prontuario(Excusa excusa) {
        this.excusa = excusa;
        this.empleado = excusa.getEmpleado();
        this.legajo = empleado.getLegajo();
    }

    public Empleado getEmpleado() { return empleado; }
    public Excusa getExcusa() { return excusa; }
    public int getLegajo() { return legajo; }
}

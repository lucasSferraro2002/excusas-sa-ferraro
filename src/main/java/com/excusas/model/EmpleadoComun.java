package com.excusas.model;

public class EmpleadoComun extends Empleado {
    public EmpleadoComun(String nombre, String email, int legajo) {
        super(nombre, email, legajo, null);
    }

    @Override
    protected NivelComplejidad getNivelAsignado() { return null; }

    @Override
    protected void procesarExcusaEspecifica(Excusa excusa) {
    }
}
package com.excusas.chain;

public class CadenaBuilder {
    private Responsable primero;
    private EslabonDeCadena ultimo;

    public CadenaBuilder agregarResponsable(EslabonDeCadena responsable) {
        if (primero == null) {
            primero = responsable;
        } else {
            ultimo.setSiguiente(responsable);
        }
        ultimo = responsable;
        return this;
    }

    public Responsable construir() {
        return primero;
    }
}

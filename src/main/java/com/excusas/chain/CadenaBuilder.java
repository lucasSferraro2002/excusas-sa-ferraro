package com.excusas.chain;

import com.excusas.model.Empleado;

public class CadenaBuilder {

    private Empleado primerEslabon;
    private Empleado ultimoEslabon;

    public CadenaBuilder agregarResponsable(Empleado responsable) {
        if (primerEslabon == null) {
            primerEslabon = responsable;
            ultimoEslabon = responsable;
        } else {
            ultimoEslabon.setSiguiente(responsable);
            ultimoEslabon = responsable;
        }
        return this;
    }

    public Empleado construir() {
        return primerEslabon;
    }
}
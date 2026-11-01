package com.excusas.model;

import java.util.EnumSet;
import java.util.Set;

public class ExcusaCompleja extends Excusa {
    public ExcusaCompleja(Empleado empleado, Motivo motivo) { super(empleado, motivo); }

    @Override
    protected Set<Motivo> motivosValidos() { return EnumSet.of(Motivo.IRRELEVANTE); }
}

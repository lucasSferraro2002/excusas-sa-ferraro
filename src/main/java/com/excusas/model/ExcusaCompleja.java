package com.excusas.model;

import java.util.EnumSet;
import java.util.Set;

public class ExcusaCompleja extends Excusa {
    public ExcusaCompleja(Empleado empleado, Motivo motivo) { this(empleado, motivo, ""); }

    public ExcusaCompleja(Empleado empleado, Motivo motivo, String descripcion) { super(empleado, motivo, descripcion); }

    @Override
    protected Set<Motivo> motivosValidos() { return EnumSet.of(Motivo.IRRELEVANTE); }

    @Override
    public NivelExcusa getNivel() { return NivelExcusa.COMPLEJA; }
}

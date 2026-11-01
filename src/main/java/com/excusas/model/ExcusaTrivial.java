package com.excusas.model;

import java.util.EnumSet;
import java.util.Set;

public class ExcusaTrivial extends Excusa {
    public ExcusaTrivial(Empleado empleado, Motivo motivo) { super(empleado, motivo); }

    @Override
    protected Set<Motivo> motivosValidos() { return EnumSet.of(Motivo.QUEDARSE_DORMIDO, Motivo.PERDER_TRANSPORTE); }

    @Override
    public NivelExcusa getNivel() { return NivelExcusa.TRIVIAL; }
}

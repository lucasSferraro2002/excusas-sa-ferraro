package com.excusas.model;

import java.util.EnumSet;
import java.util.Set;

public class ExcusaInverosimil extends Excusa {
    public ExcusaInverosimil(Empleado empleado, Motivo motivo) { super(empleado, motivo); }

    @Override
    protected Set<Motivo> motivosValidos() { return EnumSet.of(Motivo.INVEROSIMIL); }

    @Override
    public NivelExcusa getNivel() { return NivelExcusa.INVEROSIMIL; }
}

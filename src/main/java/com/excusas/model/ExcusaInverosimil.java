package com.excusas.model;

import java.util.EnumSet;
import java.util.Set;

public class ExcusaInverosimil extends Excusa {
    public ExcusaInverosimil(Empleado empleado, Motivo motivo) { this(empleado, motivo, ""); }

    public ExcusaInverosimil(Empleado empleado, Motivo motivo, String descripcion) { super(empleado, motivo, descripcion); }

    @Override
    protected Set<Motivo> motivosValidos() { return EnumSet.of(Motivo.INVEROSIMIL); }

    @Override
    public NivelExcusa getNivel() { return NivelExcusa.INVEROSIMIL; }
}

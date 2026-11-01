package com.excusas.model;

import java.util.EnumSet;
import java.util.Set;

public class ExcusaModerada extends Excusa {
    public ExcusaModerada(Empleado empleado, Motivo motivo) { this(empleado, motivo, ""); }

    public ExcusaModerada(Empleado empleado, Motivo motivo, String descripcion) { super(empleado, motivo, descripcion); }

    @Override
    protected Set<Motivo> motivosValidos() { return EnumSet.of(Motivo.PERDIDA_SUMINISTRO, Motivo.CUIDADO_FAMILIAR); }

    @Override
    public NivelExcusa getNivel() { return NivelExcusa.MODERADA; }
}

package com.excusas.model;

import java.util.function.BiFunction;

public enum Motivo {
    QUEDARSE_DORMIDO(ExcusaTrivial::new),
    PERDER_TRANSPORTE(ExcusaTrivial::new),
    PERDIDA_SUMINISTRO(ExcusaModerada::new),
    CUIDADO_FAMILIAR(ExcusaModerada::new),
    IRRELEVANTE(ExcusaCompleja::new),
    INVEROSIMIL(ExcusaInverosimil::new);

    private final BiFunction<Empleado, Motivo, Excusa> constructor;

    Motivo(BiFunction<Empleado, Motivo, Excusa> constructor) {
        this.constructor = constructor;
    }

    public Excusa nuevaExcusa(Empleado empleado) {
        return constructor.apply(empleado, this);
    }
}

package com.excusas.model;

public enum Motivo {
    QUEDARSE_DORMIDO(ExcusaTrivial::new, false),
    PERDER_TRANSPORTE(ExcusaTrivial::new, false),
    PERDIDA_SUMINISTRO(ExcusaModerada::new, false),
    CUIDADO_FAMILIAR(ExcusaModerada::new, false),
    IRRELEVANTE(ExcusaCompleja::new, true),
    INVEROSIMIL(ExcusaInverosimil::new, true);

    @FunctionalInterface
    private interface FabricaDeExcusa {
        Excusa crear(Empleado empleado, Motivo motivo, String descripcion);
    }

    private final FabricaDeExcusa fabrica;
    private final boolean admiteDescripcion;

    Motivo(FabricaDeExcusa fabrica, boolean admiteDescripcion) {
        this.fabrica = fabrica;
        this.admiteDescripcion = admiteDescripcion;
    }

    public boolean admiteDescripcion() { return admiteDescripcion; }

    public Excusa nuevaExcusa(Empleado empleado) {
        return nuevaExcusa(empleado, "");
    }

    public Excusa nuevaExcusa(Empleado empleado, String descripcion) {
        return fabrica.crear(empleado, this, descripcion);
    }
}

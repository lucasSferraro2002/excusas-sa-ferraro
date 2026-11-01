package com.excusas.state;

import java.util.OptionalInt;

@FunctionalInterface
public interface PoliticaDeModo {
    Estado modoPara(int excusasProcesadas);

    default int cantidadTrasCafe() { return 0; }

    default OptionalInt cantidadTrasDescanso(int minutos) { return OptionalInt.empty(); }

    default int cantidadTrasUnaNoche(int cantidadActual) { return cantidadActual; }
}

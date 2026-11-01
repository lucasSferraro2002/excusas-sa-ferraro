package com.excusas.state;

import java.util.OptionalInt;

/**
 * Reglas de transición entre modos. El modo siempre sale de la cantidad de excusas procesadas;
 * café, descanso y noche se expresan como la cantidad a la que vuelve el encargado.
 * Para sumar modos nuevos basta una política que los devuelva en modoPara.
 */
@FunctionalInterface
public interface PoliticaDeModo {
    Estado modoPara(int excusasProcesadas);

    default int cantidadTrasCafe() { return 0; }

    default OptionalInt cantidadTrasDescanso(int minutos) { return OptionalInt.empty(); }

    default int cantidadTrasUnaNoche(int cantidadActual) { return cantidadActual; }
}

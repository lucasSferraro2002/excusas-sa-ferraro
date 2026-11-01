package com.excusas.state;

import com.excusas.chain.Encargado;
import com.excusas.model.Excusa;

public interface Estado {
    void manejarExcusa(Encargado encargado, Excusa excusa);

    /** Por defecto un nuevo día no cambia el modo; ModoVago lo redefine. */
    default void alNuevoDia(Encargado encargado) { }
}

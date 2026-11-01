package com.excusas.state;

import com.excusas.chain.Encargado;
import com.excusas.model.Excusa;

public interface Estado {
    void manejarExcusa(Encargado encargado, Excusa excusa);
    default void alNuevoDia(Encargado encargado) { }
}



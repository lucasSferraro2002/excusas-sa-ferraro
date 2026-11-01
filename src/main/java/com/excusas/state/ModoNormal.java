package com.excusas.state;

import com.excusas.model.Empleado;
import com.excusas.model.Excusa;

public class ModoNormal implements Estado {
    @Override
    public void manejarExcusa(Empleado empleado, Excusa excusa) {
        if (!empleado.intentarProcesar(excusa)) {
            if (empleado.getSiguiente() != null) {
                empleado.getSiguiente().revisarExcusa(excusa);
            } else {
                excusa.setRespuestaFinal("Excusa rechazada: necesitamos pruebas contundentes");
            }
        }
    }

}
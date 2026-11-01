package com.excusas.state;

import com.excusas.model.Empleado;
import com.excusas.model.Excusa;

public interface Estado {
    void manejarExcusa(Empleado empleado, Excusa excusa);

    default void tomarCafe(Empleado empleado) {
        empleado.setModo(new ModoProductivo());
        empleado.reiniciarContador();
    }

    default void tomarDescanso(Empleado empleado, int minutos) {
        if (minutos >= 10 && minutos <= 15) {
            empleado.setModo(new ModoNormal());
            empleado.setCantidadExcusas(6);
        }
    }
}
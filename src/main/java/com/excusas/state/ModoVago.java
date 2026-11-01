package com.excusas.state;

import com.excusas.model.Empleado;
import com.excusas.model.Excusa;
import java.util.Random;

public class ModoVago implements Estado {

    @Override
    public void manejarExcusa(Empleado empleado, Excusa excusa) {
        if (empleado.getSiguiente() != null) {
            empleado.getSiguiente().revisarExcusa(excusa);
        }
    }

    public void alDiaSiguiente(Empleado empleado) {
        Random random = new Random();

        if (random.nextBoolean()) {
            empleado.setModo(new ModoProductivo());
            empleado.setCantidadExcusas(0);
        } else {
            empleado.setModo(new ModoNormal());
            empleado.setCantidadExcusas(6);
        }
    }
}
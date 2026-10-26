package com.excusas.state;
import com.excusas.model.Empleado;

public class ModoVago implements ModoDesempeno {
    @Override
    public void tomarCafe(Empleado empleado) {
        empleado.setModo(new ModoNormal());
    }

    @Override
    public void tomarDescanso(Empleado empleado) {
    }
}
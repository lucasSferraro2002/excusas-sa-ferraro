package com.excusas.state;
import com.excusas.model.Empleado;

public class ModoProductivo implements ModoDesempeno {
    @Override
    public void tomarCafe(Empleado empleado) {
    }
    @Override
    public void tomarDescanso(Empleado empleado) {
        empleado.setModo(new ModoNormal());
    }
}
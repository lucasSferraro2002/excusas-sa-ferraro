package com.excusas.state;
import com.excusas.model.Empleado;

public class ModoNormal implements ModoDesempeno {
    @Override
    public void tomarCafe(Empleado empleado) {
        empleado.setModo(new ModoProductivo());
    }
    @Override
    public void tomarDescanso(Empleado empleado) {
        empleado.setModo(new ModoVago());
    }
}
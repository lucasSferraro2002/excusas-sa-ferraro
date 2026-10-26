package com.excusas.state;
import com.excusas.model.Empleado;

public class ModoNormal implements ModoDesempeno {
    @Override
    public void tomarCafe(Empleado empleado) {
        // Al tomar café estando normal, pasa a productivo
        empleado.setModo(new ModoProductivo());
    }
    @Override
    public void tomarDescanso(Empleado empleado) {
        empleado.setModo(new ModoVago());
    }
}
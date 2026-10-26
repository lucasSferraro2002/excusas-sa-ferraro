package com.excusas.state;
import com.excusas.model.Empleado;

public interface ModoDesempeno {
    void tomarCafe(Empleado empleado);
    void tomarDescanso(Empleado empleado);
}
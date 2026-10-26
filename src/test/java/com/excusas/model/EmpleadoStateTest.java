package com.excusas.model;

import com.excusas.state.ModoProductivo;
import com.excusas.state.ModoVago;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class EmpleadoStateTest {

    @Test
    void empleadoPasaAModoProductivoAlTomarCafe() {

        Empleado empleado = new Empleado("Ana", "ana@excusas.com", 101);

        empleado.tomarCafe();

        assertInstanceOf(ModoProductivo.class, empleado.getModo());
    }
    @Test
    void empleadoPasaAModoVagoAlTomarDescanso() {
        Empleado empleado = new Empleado("Ana", "ana@excusas.com", 101);

        empleado.tomarDescanso();

        assertInstanceOf(ModoVago.class, empleado.getModo());
    }
}
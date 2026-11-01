package com.excusas.chain;

import com.excusas.model.Empleado;
import com.excusas.model.EmpleadoComun;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CadenaBuilderTest {

    @Test
    void construirCadenaAsignaElSiguienteCorrectamente() {
        Empleado e1 = new EmpleadoComun("E1", "e1@test.com", 1);
        Empleado e2 = new EmpleadoComun("E2", "e2@test.com", 2);
        Empleado e3 = new EmpleadoComun("E3", "e3@test.com", 3);

        Empleado cadena = new CadenaBuilder()
                .agregarResponsable(e1)
                .agregarResponsable(e2)
                .agregarResponsable(e3)
                .construir();

        assertEquals(e1, cadena);
        assertEquals(e2, e1.getSiguiente());
        assertEquals(e3, e2.getSiguiente());
        assertNull(e3.getSiguiente());
    }
}
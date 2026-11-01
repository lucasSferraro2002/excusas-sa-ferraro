package com.excusas.chain;

import com.excusas.model.*;
import com.excusas.service.EmailSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReglasDeNegocioTest {

    private Empleado empleado;
    private EmailSender emailSenderMock;

    @BeforeEach
    void setUp() {
        empleado = new EmpleadoComun("Juan", "juan@test.com", 1);
        emailSenderMock = mock(EmailSender.class);
    }

    @Test
    void rechazaExcusaAlLlegarAlFinalDeLaCadena() {
        Empleado cadena = new CadenaBuilder()
                .agregarResponsable(new Recepcionista("Ana", "ana@excusas.com", 101, emailSenderMock))
                .construir();

        Excusa excusa = new Excusa(empleado, NivelComplejidad.COMPLEJA, "Problema grave");
        cadena.revisarExcusa(excusa);

        assertFalse(excusa.isAceptada());
        assertEquals("Excusa rechazada: necesitamos pruebas contundentes", excusa.getRespuestaFinal());
    }

    @Test
    void aceptaExcusaSiResponsableCoincide() {
        Empleado cadena = new CadenaBuilder()
                .agregarResponsable(new GerenteHR("Carlos", "carlos@excusas.com", 103, emailSenderMock))
                .construir();

        Excusa excusa = new Excusa(empleado, NivelComplejidad.COMPLEJA, "Problema grave");
        cadena.revisarExcusa(excusa);

        assertTrue(excusa.isAceptada());
    }
}
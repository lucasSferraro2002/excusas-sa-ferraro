package com.excusas.chain;

import com.excusas.model.Empleado;
import com.excusas.model.Excusa;
import com.excusas.model.NivelComplejidad;
import com.excusas.service.EmailSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SupervisoraAreaTest {

    private EmailSender emailSenderMock;
    private SupervisoraArea supervisora;
    private Empleado empleado;

    @BeforeEach
    void setUp() {
        emailSenderMock = mock(EmailSender.class);
        supervisora = new SupervisoraArea("Laura", "laura@excusas.com", 102, emailSenderMock);
        empleado = new EmpleadoComun("Juan", "juan@test.com", 1);
    }

    @Test
    void procesaExcusaModeradaPorLuzYEnviaEmailAEdesur() {
        Excusa excusa = new Excusa(empleado, NivelComplejidad.MODERADA, "Hubo un corte de luz en el barrio");

        boolean resultado = supervisora.intentarProcesar(excusa);

        assertTrue(resultado);
        assertTrue(excusa.isAceptada());
        verify(emailSenderMock).enviarEmail("EDESUR@mailfake.com.ar", "laura@excusas.com", "Consulta", "¿Es verdad?");
    }

    @Test
    void procesaExcusaModeradaPersonalYEnviaEmailAlEmpleado() {
        Excusa excusa = new Excusa(empleado, NivelComplejidad.MODERADA, "Cuidado familiar a cargo");

        boolean resultado = supervisora.intentarProcesar(excusa);

        assertTrue(resultado);
        assertTrue(excusa.isAceptada());
        verify(emailSenderMock).enviarEmail("juan@test.com", "laura@excusas.com", "Consulta personal", "¿Todo esta bien?");
    }
}
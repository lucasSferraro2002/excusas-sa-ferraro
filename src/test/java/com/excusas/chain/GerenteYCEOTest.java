package com.excusas.chain;

import com.excusas.model.Empleado;
import com.excusas.model.EmpleadoComun;
import com.excusas.model.Excusa;
import com.excusas.model.NivelComplejidad;
import com.excusas.service.EmailSender;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GerenteYCEOTest {

    @Test
    void gerenteProcesaExcusaComplejaSinEnviarEmail() {
        EmailSender emailSenderMock = mock(EmailSender.class);
        GerenteHR gerente = new GerenteHR("Carlos", "carlos@excusas.com", 103, emailSenderMock);
        Empleado empleado = new EmpleadoComun("Juan", "juan@test.com", 1);

        Excusa excusa = new Excusa(empleado, NivelComplejidad.COMPLEJA, "Problema grave");

        boolean resultado = gerente.intentarProcesar(excusa);

        assertTrue(resultado);
        assertTrue(excusa.isAceptada());
        verify(emailSenderMock, never()).enviarEmail(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void ceoProcesaExcusaInverosimilYEnviaEmail() {
        EmailSender emailSenderMock = mock(EmailSender.class);
        CEO ceo = new CEO("Roberto", "ceo@excusas.com", 1, emailSenderMock);
        Empleado empleado = new EmpleadoComun("Juan", "juan@test.com", 1);

        Excusa excusa = new Excusa(empleado, NivelComplejidad.INVEROSIMIL, "Abducido por aliens");

        boolean resultado = ceo.intentarProcesar(excusa);

        assertTrue(resultado);
        assertTrue(excusa.isAceptada());
        verify(emailSenderMock).enviarEmail("juan@test.com", "ceo@excusas.com", "Resolución final", "Aprobado por creatividad");
    }
}
package com.excusas.chain;

import com.excusas.model.Empleado;
import com.excusas.model.Excusa;
import com.excusas.model.Motivo;
import com.excusas.service.EmailSender;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class CEOTest {

    @Test
    void ceoAceptaExcusaInverosimilYEnviaEmail() {
        EmailSender emailSenderMock = mock(EmailSender.class);
        CEO ceo = new CEO("Roberto", "ceo@excusas.com", 1, emailSenderMock);
        Empleado empleado = new Empleado("Lucas", "lucas@excusas.com", 504);
        Excusa excusa = new Excusa(empleado, Motivo.INCREIBLE_INVEROSIMIL);

        ceo.revisarExcusa(excusa);

        assertTrue(excusa.isAceptada());
        verify(emailSenderMock).enviarEmail(
                eq("lucas@excusas.com"),
                anyString(),
                anyString(),
                eq("Aprobado por creatividad")
        );
    }
}
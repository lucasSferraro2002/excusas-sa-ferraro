package com.excusas.chain;

import com.excusas.model.Empleado;
import com.excusas.model.Excusa;
import com.excusas.model.Motivo;
import com.excusas.service.EmailSender;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class SupervisoraAreaTest {

    @Test
    void supervisoraAceptaCorteDeLuzYEnviaEmailAEdesur() {
        EmailSender emailSenderMock = mock(EmailSender.class);
        SupervisoraArea supervisora = new SupervisoraArea("Laura", "laura@excusas.com", 102, emailSenderMock);
        Empleado empleado = new Empleado("Pedro", "pedro@excusas.com", 502);
        Excusa excusa = new Excusa(empleado, Motivo.PERDIDA_SUMINISTRO);

        supervisora.revisarExcusa(excusa);

        assertTrue(excusa.isAceptada());
        verify(emailSenderMock).enviarEmail(
                eq("EDESUR@mailfake.com.ar"),
                anyString(),
                anyString(),
                anyString()
        );
    }
}
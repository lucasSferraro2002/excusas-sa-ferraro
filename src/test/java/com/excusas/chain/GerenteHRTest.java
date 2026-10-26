package com.excusas.chain;

import com.excusas.model.Empleado;
import com.excusas.model.Excusa;
import com.excusas.model.Motivo;
import com.excusas.service.EmailSender;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class GerenteHRTest {

    @Test
    void gerenteAceptaExcusaIrrelevanteOCompleja() {
        EmailSender emailSenderMock = mock(EmailSender.class);
        GerenteHR gerente = new GerenteHR("Carlos", "carlos@excusas.com", 103, emailSenderMock);
        Empleado empleado = new Empleado("Maria", "maria@excusas.com", 503);
        Excusa excusa = new Excusa(empleado, Motivo.IRRELEVANTE);

        gerente.revisarExcusa(excusa);

        assertTrue(excusa.isAceptada());
    }
}
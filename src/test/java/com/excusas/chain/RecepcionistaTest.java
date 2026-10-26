package com.excusas.chain;
import static org.junit.jupiter.api.Assertions.assertFalse;
import com.excusas.model.Empleado;
import com.excusas.model.Excusa;
import com.excusas.model.Motivo;
import com.excusas.service.EmailSender;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class RecepcionistaTest {

    @Test
    void recepcionistaAceptaExcusaQuedarseDormidoYEnviaEmail() {
        EmailSender emailSenderMock = mock(EmailSender.class);
        Recepcionista recepcionista = new Recepcionista("Ana", "ana@excusas.com", 101, emailSenderMock);
        Empleado empleado = new Empleado("Juan", "juan@excusas.com", 501);
        Excusa excusa = new Excusa(empleado, Motivo.QUEDARSE_DORMIDO);

        recepcionista.revisarExcusa(excusa);

        assertTrue(excusa.isAceptada());
        verify(emailSenderMock).enviarEmail(
                eq("juan@excusas.com"),
                anyString(),
                eq("motivo demora"),
                eq("la licencia fue aceptada")
        );
    }
    @Test
    void recepcionistaDerivaExcusaASiguienteResponsable() {

        EmailSender emailSenderMock = mock(EmailSender.class);
        Recepcionista recepcionista = new Recepcionista("Ana", "ana@excusas.com", 101, emailSenderMock);


        Responsable siguienteMock = mock(Responsable.class);
        recepcionista.setSiguiente(siguienteMock);

        Empleado empleado = new Empleado("Juan", "juan@excusas.com", 501);

        Excusa excusa = new Excusa(empleado, Motivo.PERDIDA_SUMINISTRO);

        recepcionista.revisarExcusa(excusa);

        assertFalse(excusa.isAceptada());

        verify(siguienteMock).revisarExcusa(excusa);
    }
}
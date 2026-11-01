package com.excusas.chain;

import com.excusas.model.Empleado;
import com.excusas.model.Excusa;
import com.excusas.model.NivelComplejidad;
import com.excusas.service.EmailSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecepcionistaTest {

    private EmailSender emailSenderMock;
    private Recepcionista recepcionista;
    private Empleado empleado;

    @BeforeEach
    void setUp() {
        emailSenderMock = mock(EmailSender.class);
        recepcionista = new Recepcionista("Ana", "ana@excusas.com", 101, emailSenderMock);
        empleado = new EmpleadoComun("Juan", "juan@test.com", 1);
    }

    @Test
    void procesaExcusaTrivialYEnviaEmail() {
        Excusa excusa = new Excusa(empleado, NivelComplejidad.TRIVIAL, "Me quede dormido");

        boolean resultado = recepcionista.intentarProcesar(excusa);

        assertTrue(resultado);
        assertTrue(excusa.isAceptada());
        verify(emailSenderMock, times(1)).enviarEmail("juan@test.com", "ana@excusas.com", "motivo demora", "la licencia fue aceptada");
    }

    @Test
    void rechazaExcusaNoTrivial() {
        Excusa excusa = new Excusa(empleado, NivelComplejidad.MODERADA, "Problema familiar");

        boolean resultado = recepcionista.intentarProcesar(excusa);

        assertFalse(resultado);
        verify(emailSenderMock, never()).enviarEmail(anyString(), anyString(), anyString(), anyString());
    }
}
package com.excusas.state;

import com.excusas.model.Empleado;
import com.excusas.model.Excusa;
import com.excusas.model.NivelComplejidad;
import com.excusas.service.EmailSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EstadoEmpleadoTest {

    private Empleado empleado;
    private Empleado siguiente;
    private EmailSender emailSenderMock;
    private Excusa excusa;

    @BeforeEach
    void setUp() {
        emailSenderMock = mock(EmailSender.class);
        empleado = new EmpleadoComun("Jefe", "jefe@excusas.com", 100);
        empleado.setEmailSender(emailSenderMock);

        siguiente = mock(Empleado.class);
        empleado.setSiguiente(siguiente);

        Empleado solicitante = new EmpleadoComun("Juan", "juan@test.com", 1);
        excusa = new Excusa(solicitante, NivelComplejidad.COMPLEJA, "Texto");
    }

    @Test
    void modoProductivoDerivaYAvisaAlCTO() {
        empleado.setModo(new ModoProductivo());

        empleado.revisarExcusa(excusa);

        // Textos actualizados para coincidir con tu clase ModoProductivo
        verify(emailSenderMock).enviarEmail(
                "CTO@excusas.com",
                "jefe@excusas.com",
                "Aviso por derivación",
                "Derivo excusa"
        );
        verify(siguiente).revisarExcusa(excusa);
    }

    @Test
    void modoNormalDerivaSinAvisarAlCTO() {
        empleado.setModo(new ModoNormal());

        empleado.revisarExcusa(excusa);

        verify(emailSenderMock, never()).enviarEmail(anyString(), anyString(), anyString(), anyString());
        verify(siguiente).revisarExcusa(excusa);
    }

    @Test
    void modoVagoAlDiaSiguienteTransicionaAProductivo() {
        ModoVago modoVagoProductivo = new ModoVago(() -> true);
        empleado.setModo(modoVagoProductivo);

        modoVagoProductivo.alDiaSiguiente(empleado);

        assertTrue(empleado.getModo() instanceof ModoProductivo);
    }

    @Test
    void modoVagoAlDiaSiguienteTransicionaANormal() {
        ModoVago modoVagoNormal = new ModoVago(() -> false);
        empleado.setModo(modoVagoNormal);

        modoVagoNormal.alDiaSiguiente(empleado);

        assertTrue(empleado.getModo() instanceof ModoNormal);
    }
}
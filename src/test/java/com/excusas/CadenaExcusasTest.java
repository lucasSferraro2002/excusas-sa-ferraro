package com.excusas;

import com.excusas.chain.*;
import com.excusas.model.*;
import com.excusas.service.Contactos;
import com.excusas.service.EmailSender;
import com.excusas.service.RegistroDeProntuarios;
import com.excusas.state.ModoNormal;
import com.excusas.state.ModoProductivo;
import com.excusas.state.ModoVago;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CadenaExcusasTest {
    @Mock
    private EmailSender sender;
    @Mock
    private RegistroDeProntuarios registro;

    private final Empleado empleado = new Empleado("Juan", "juan@excusas.com", 7);

    private Responsable cadenaEstandar() {
        return new CadenaBuilder()
                .agregarResponsable(new Recepcionista("Ana", "ana@x.com", 1, sender))
                .agregarResponsable(new SupervisoraArea("Laura", "laura@x.com", 2, sender))
                .agregarResponsable(new GerenteHR("Carlos", "carlos@x.com", 3, sender))
                .agregarResponsable(new CEO("Roberto", "ceo@x.com", 4, sender, registro))
                .construir();
    }

    @Test
    void cadaMotivoSeCreaConSuTipoDeExcusa() {
        assertInstanceOf(ExcusaTrivial.class, Motivo.QUEDARSE_DORMIDO.nuevaExcusa(empleado));
        assertInstanceOf(ExcusaModerada.class, Motivo.CUIDADO_FAMILIAR.nuevaExcusa(empleado));
        assertInstanceOf(ExcusaCompleja.class, Motivo.IRRELEVANTE.nuevaExcusa(empleado));
        assertInstanceOf(ExcusaInverosimil.class, Motivo.INVEROSIMIL.nuevaExcusa(empleado));
    }

    @Test
    void unTipoDeExcusaRechazaMotivosQueNoLeCorresponden() {
        assertThrows(IllegalArgumentException.class, () -> new ExcusaTrivial(empleado, Motivo.INVEROSIMIL));
    }

    @Test
    void recepcionistaAceptaExcusaTrivialYAvisaAlEmpleado() {
        Excusa excusa = Motivo.QUEDARSE_DORMIDO.nuevaExcusa(empleado);

        cadenaEstandar().revisarExcusa(excusa);

        assertTrue(excusa.isAceptada());
        verify(sender).enviarEmail("juan@excusas.com", "ana@x.com", "motivo demora", "la licencia fue aceptada");
        verifyNoMoreInteractions(sender);
    }

    @Test
    void supervisoraConsultaAEdesurPorPerdidaDeSuministro() {
        Excusa excusa = Motivo.PERDIDA_SUMINISTRO.nuevaExcusa(empleado);

        cadenaEstandar().revisarExcusa(excusa);

        assertTrue(excusa.isAceptada());
        verify(sender).enviarEmail(eq(Contactos.EMAIL_EDESUR), eq("laura@x.com"), anyString(), anyString());
    }

    @Test
    void supervisoraPreguntaAlEmpleadoPorCuidadoFamiliar() {
        Excusa excusa = Motivo.CUIDADO_FAMILIAR.nuevaExcusa(empleado);

        cadenaEstandar().revisarExcusa(excusa);

        assertTrue(excusa.isAceptada());
        verify(sender).enviarEmail(eq("juan@excusas.com"), eq("laura@x.com"), eq("Consulta personal"), anyString());
    }

    @Test
    void ceoApruebaInverosimilYRegistraProntuario() {
        Excusa excusa = Motivo.INVEROSIMIL.nuevaExcusa(empleado);

        cadenaEstandar().revisarExcusa(excusa);

        assertTrue(excusa.isAceptada());
        verify(sender).enviarEmail("juan@excusas.com", "ceo@x.com", "Resolución final", "Aprobado por creatividad");
        verify(registro).registrarProntuario(excusa);
    }

    @Test
    void ceoPrimeroNoAceptaUnaExcusaTrivial() {
        Responsable cadena = new CadenaBuilder()
                .agregarResponsable(new CEO("Roberto", "ceo@x.com", 4, sender, registro))
                .agregarResponsable(new Recepcionista("Ana", "ana@x.com", 1, sender))
                .construir();
        Excusa excusa = Motivo.PERDER_TRANSPORTE.nuevaExcusa(empleado);

        cadena.revisarExcusa(excusa);

        assertTrue(excusa.isAceptada());
        verify(registro, never()).registrarProntuario(any());
    }

    @Test
    void sinNingunResponsableCapazSeRechazaConElMensajeDeLaConsigna() {
        Responsable cadena = new Recepcionista("Ana", "ana@x.com", 1, sender);
        Excusa excusa = Motivo.INVEROSIMIL.nuevaExcusa(empleado);

        cadena.revisarExcusa(excusa);

        assertFalse(excusa.isAceptada());
        assertEquals(Excusa.MENSAJE_RECHAZO, excusa.getRespuestaFinal());
    }

    @Test
    void elRechazoSeInformaTambienSiElUltimoEslabonEstaVagoOProductivo() {
        for (var modo : List.of(new ModoVago(), new ModoProductivo(), new ModoNormal())) {
            Recepcionista ultimo = new Recepcionista("Ana", "ana@x.com", 1, sender);
            ultimo.setModo(modo);
            Excusa excusa = Motivo.INVEROSIMIL.nuevaExcusa(empleado);

            ultimo.revisarExcusa(excusa);

            assertEquals(Excusa.MENSAJE_RECHAZO, excusa.getRespuestaFinal(), "modo " + modo.getClass().getSimpleName());
        }
    }

    @Test
    void productivoAvisaAlCtoCadaVezQueDerivaALoSiguiente() {
        cadenaEstandar().revisarExcusa(Motivo.INVEROSIMIL.nuevaExcusa(empleado));

        verify(sender, times(3)).enviarEmail(eq(Contactos.EMAIL_CTO), anyString(), eq("Derivacion de excusa"), anyString());
    }

    @Test
    void vagoDerivaSinEvaluarNiAvisarAlCto() {
        Recepcionista vago = new Recepcionista("Ana", "ana@x.com", 1, sender);
        vago.setModo(new ModoVago());
        Responsable cadena = new CadenaBuilder()
                .agregarResponsable(vago)
                .agregarResponsable(new Recepcionista("Beto", "beto@x.com", 2, sender))
                .construir();

        cadena.revisarExcusa(Motivo.QUEDARSE_DORMIDO.nuevaExcusa(empleado));

        verify(sender).enviarEmail(eq("juan@excusas.com"), eq("beto@x.com"), eq("motivo demora"), anyString());
        verifyNoMoreInteractions(sender);
    }
}

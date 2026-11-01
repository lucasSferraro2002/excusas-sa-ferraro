package com.excusas.service;

import com.excusas.model.Empleado;
import com.excusas.model.EmpleadoComun;
import com.excusas.model.Excusa;
import com.excusas.model.NivelComplejidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class AdministradorProntuariosTest {

    @BeforeEach
    void setUp() {
        AdministradorProntuarios.getInstance().getObservadores().clear();
    }

    @Test
    void notificaObservadoresAlRegistrarProntuario() {
        AtomicBoolean notificado = new AtomicBoolean(false);
        AdministradorProntuarios.getInstance().agregarObservador(excusa -> notificado.set(true));

        Empleado empleado = new EmpleadoComun("Juan", "juan@test.com", 1);
        Excusa excusa = new Excusa(empleado, NivelComplejidad.INVEROSIMIL, "Abduccion");

        AdministradorProntuarios.getInstance().registrarProntuario(excusa);

        assertTrue(notificado.get());
    }
}
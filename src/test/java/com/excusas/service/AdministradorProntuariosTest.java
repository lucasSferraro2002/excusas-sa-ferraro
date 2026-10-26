package com.excusas.service;

import com.excusas.model.Empleado;
import com.excusas.model.Excusa;
import com.excusas.model.Motivo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class AdministradorProntuariosTest {

    @Test
    void administradorDebeSerSingleton() {
        AdministradorProntuarios admin1 = AdministradorProntuarios.getInstance();
        AdministradorProntuarios admin2 = AdministradorProntuarios.getInstance();

        assertSame(admin1, admin2);
    }

    @Test
    void notificaALaDireccionAlRegistrarProntuario() {
        AdministradorProntuarios admin = AdministradorProntuarios.getInstance();
        DirectivoObserver observerMock = mock(DirectivoObserver.class);
        admin.agregarObservador(observerMock);

        Empleado empleado = new Empleado("Lucas", "lucas@excusas.com", 504);
        Excusa excusa = new Excusa(empleado, Motivo.INCREIBLE_INVEROSIMIL);

        admin.registrarProntuario(excusa);

        verify(observerMock).actualizar(any(Excusa.class));
    }
}
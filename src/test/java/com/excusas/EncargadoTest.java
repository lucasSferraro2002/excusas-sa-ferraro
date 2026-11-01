package com.excusas;

import com.excusas.chain.Encargado;
import com.excusas.chain.Recepcionista;
import com.excusas.chain.Responsable;
import com.excusas.model.Excusa;
import com.excusas.service.EmailSender;
import com.excusas.state.Estado;
import com.excusas.state.PoliticaDeModo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EncargadoTest {
    @Mock
    private EmailSender sender;
    @Mock
    private Estado modo;
    @Mock
    private Excusa excusa;

    private Encargado encargado() {
        return new Recepcionista("Ana", "ana@x.com", 1, sender);
    }

    @Test
    void revisarExcusaDelegaEnElModoActual() {
        Encargado encargado = encargado();
        encargado.setModo(modo);

        encargado.revisarExcusa(excusa);

        verify(modo).manejarExcusa(encargado, excusa);
    }

    @Test
    void comenzarNuevoDiaDelegaEnElModoActual() {
        Encargado encargado = encargado();
        encargado.setModo(modo);

        encargado.comenzarNuevoDia();

        verify(modo).alNuevoDia(encargado);
    }

    @Test
    void derivarSinSiguienteRechazaLaExcusa() {
        encargado().derivar(excusa);

        verify(excusa).rechazarPorFaltaDePruebas();
    }

    @Test
    void derivarPasaLaExcusaAlSiguiente(@Mock Responsable siguiente) {
        Encargado encargado = encargado();
        encargado.setSiguiente(siguiente);

        encargado.derivar(excusa);

        verify(siguiente).revisarExcusa(excusa);
        verify(excusa, never()).rechazarPorFaltaDePruebas();
    }

    @Test
    void elCafeUsaLaPoliticaInyectada(@Mock PoliticaDeModo politica) {
        when(politica.cantidadTrasCafe()).thenReturn(3);
        when(politica.modoPara(3)).thenReturn(modo);
        Encargado encargado = encargado();
        encargado.setPolitica(politica);

        encargado.tomarCafe();

        assertSame(modo, encargado.getModo());
    }
}

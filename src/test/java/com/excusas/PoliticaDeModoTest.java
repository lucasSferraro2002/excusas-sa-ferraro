package com.excusas;

import com.excusas.chain.Recepcionista;
import com.excusas.model.Empleado;
import com.excusas.model.Motivo;
import com.excusas.service.EmailSender;
import com.excusas.state.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PoliticaDeModoTest {
    @Mock
    private EmailSender sender;
    @Mock
    private Estado modoNuevo;

    private final PoliticaDeModo politica = new PoliticaEstandar();
    private final Empleado empleado = new Empleado("Juan", "juan@excusas.com", 7);

    private Recepcionista nuevaRecepcionista() {
        return new Recepcionista("Ana", "ana@x.com", 1, sender);
    }

    private void procesar(Recepcionista r, int veces) {
        for (int i = 0; i < veces; i++) {
            r.revisarExcusa(Motivo.QUEDARSE_DORMIDO.nuevaExcusa(empleado));
        }
    }

    @Test
    void productivoConMenosDeCincoExcusas() {
        for (int n = 0; n < 5; n++) assertInstanceOf(ModoProductivo.class, politica.modoPara(n));
    }

    @Test
    void normalDeCincoADiezExcusas() {
        for (int n = 5; n <= 10; n++) assertInstanceOf(ModoNormal.class, politica.modoPara(n));
    }

    @Test
    void vagoDesdeOnceExcusas() {
        assertInstanceOf(ModoVago.class, politica.modoPara(11));
        assertInstanceOf(ModoVago.class, politica.modoPara(50));
    }

    @Test
    void elEncargadoCambiaDeModoAlProcesarExcusas() {
        Recepcionista r = nuevaRecepcionista();
        procesar(r, 4);
        assertInstanceOf(ModoProductivo.class, r.getModo());
        procesar(r, 1);
        assertInstanceOf(ModoNormal.class, r.getModo());
        procesar(r, 6);
        assertInstanceOf(ModoVago.class, r.getModo());
    }

    @Test
    void sePuedeIncorporarUnModoNuevoSinModificarEncargado() {
        Recepcionista r = nuevaRecepcionista();
        r.setPolitica(n -> modoNuevo);
        procesar(r, 1);
        assertSame(modoNuevo, r.getModo());
    }

    @Test
    void elCafeVuelveAProductivoYReiniciaLaCuenta() {
        Recepcionista r = nuevaRecepcionista();
        procesar(r, 7);
        assertInstanceOf(ModoNormal.class, r.getModo());
        r.tomarCafe();
        assertInstanceOf(ModoProductivo.class, r.getModo());
        procesar(r, 4);
        assertInstanceOf(ModoProductivo.class, r.getModo());
    }

    @Test
    void elDescansoDeDiezAQuinceMinutosPasaANormal() {
        for (int minutos : new int[]{10, 12, 15}) {
            Recepcionista r = nuevaRecepcionista();
            r.tomarDescanso(minutos);
            assertInstanceOf(ModoNormal.class, r.getModo(), minutos + " min");
        }
    }

    @Test
    void elDescansoFueraDeRangoNoTieneEfecto() {
        for (int minutos : new int[]{0, 9, 16, 60}) {
            Recepcionista r = nuevaRecepcionista();
            r.tomarDescanso(minutos);
            assertInstanceOf(ModoProductivo.class, r.getModo(), minutos + " min");
        }
    }

    @Test
    void elVagoAlDiaSiguienteVuelveAProductivoOANormalSegunElAzar() {
        Recepcionista conCara = nuevaRecepcionista();
        conCara.setPolitica(new PoliticaEstandar(() -> true));
        procesar(conCara, 11);
        assertInstanceOf(ModoVago.class, conCara.getModo());
        conCara.comenzarNuevoDia();
        assertInstanceOf(ModoProductivo.class, conCara.getModo());

        Recepcionista conCeca = nuevaRecepcionista();
        conCeca.setPolitica(new PoliticaEstandar(() -> false));
        procesar(conCeca, 11);
        conCeca.comenzarNuevoDia();
        assertInstanceOf(ModoNormal.class, conCeca.getModo());
    }

    @Test
    void unNuevoDiaNoCambiaElModoSiNoEstabaVago() {
        Recepcionista r = nuevaRecepcionista();
        procesar(r, 6);
        r.comenzarNuevoDia();
        assertInstanceOf(ModoNormal.class, r.getModo());
    }
}

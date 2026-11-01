package com.excusas;

import com.excusas.chain.CEO;
import com.excusas.model.Empleado;
import com.excusas.model.Excusa;
import com.excusas.model.Motivo;
import com.excusas.model.Prontuario;
import com.excusas.service.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProntuarioTest {
    @Mock
    private EmailSender sender;

    private final AdministradorProntuarios admin = AdministradorProntuarios.getInstance();
    private final Empleado empleado = new Empleado("Juan", "juan@excusas.com", 7);

    private CEO nuevoCeo() {
        return new CEO("Roberto", "ceo@x.com", 1, sender, admin);
    }

    @Test
    void elAdministradorEsUnSingleton() {
        assertSame(AdministradorProntuarios.getInstance(), AdministradorProntuarios.getInstance());
    }

    @Test
    void elCeoRegistraUnProntuarioConEmpleadoExcusaYLegajo() {
        Excusa excusa = Motivo.INVEROSIMIL.nuevaExcusa(empleado);
        int antes = admin.getProntuarios().size();

        nuevoCeo().revisarExcusa(excusa);

        List<Prontuario> prontuarios = admin.getProntuarios();
        assertEquals(antes + 1, prontuarios.size());
        Prontuario ultimo = prontuarios.get(prontuarios.size() - 1);
        assertSame(empleado, ultimo.getEmpleado());
        assertSame(excusa, ultimo.getExcusa());
        assertEquals(7, ultimo.getLegajo());
    }

    @Test
    void losObservadoresRecibenElProntuario(@Mock DirectivoObserver observador) {
        admin.agregarObservador(observador);

        nuevoCeo().revisarExcusa(Motivo.INVEROSIMIL.nuevaExcusa(empleado));

        ArgumentCaptor<Prontuario> captor = ArgumentCaptor.forClass(Prontuario.class);
        verify(observador).actualizar(captor.capture());
        assertEquals(7, captor.getValue().getLegajo());
    }

    @Test
    void laListaDeProntuariosNoSePuedeModificarDesdeAfuera() {
        assertThrows(UnsupportedOperationException.class, () -> admin.getProntuarios().add(null));
    }

    @Test
    void elEquipoDeDireccionAvisaPorEmailAlRegistrarseUnProntuario() {
        EquipoDeDireccion direccion = new EquipoDeDireccion(sender);

        direccion.actualizar(new Prontuario(Motivo.INVEROSIMIL.nuevaExcusa(empleado)));

        verify(sender).enviarEmail(Contactos.EMAIL_DIRECCION, Contactos.EMAIL_PRONTUARIOS,
                "Nuevo prontuario", "Se registró un prontuario para el legajo 7");
    }

    @Test
    void elProntuarioConservaLaExcusaEscritaPorElEmpleado() {
        Excusa excusa = Motivo.INVEROSIMIL.nuevaExcusa(empleado, "Una paloma robó mi bicicleta");

        nuevoCeo().revisarExcusa(excusa);

        List<Prontuario> prontuarios = admin.getProntuarios();
        Prontuario ultimo = prontuarios.get(prontuarios.size() - 1);
        assertEquals("Una paloma robó mi bicicleta", ultimo.getExcusa().getDescripcion());
    }
}

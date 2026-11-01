package com.excusas.chain;

import com.excusas.model.Excusa;
import com.excusas.model.Motivo;
import com.excusas.model.NivelExcusa;
import com.excusas.service.Contactos;
import com.excusas.service.EmailSender;

/** Acepta excusas triviales y moderadas; las triviales solo las acepta, sin acción propia. */
public class SupervisoraArea extends Encargado {
    private static final String ASUNTO_EDESUR = "Verificación de servicio";
    private static final String CUERPO_EDESUR = "¿Me confirman si hubo un corte de luz en el barrio del empleado?";
    private static final String ASUNTO_FAMILIAR = "Consulta personal";
    private static final String CUERPO_FAMILIAR = "¿Está todo bien con tu familiar?";

    public SupervisoraArea(String nombre, String email, int legajo, EmailSender emailSender) {
        super(nombre, email, legajo, emailSender);
    }

    @Override
    protected boolean puedeProcesar(Excusa excusa) { return esDeNivelHasta(excusa, NivelExcusa.MODERADA); }

    @Override
    protected void procesarExcusaEspecifica(Excusa excusa) {
        excusa.aceptar();
        if (excusa.getMotivo() == Motivo.PERDIDA_SUMINISTRO) {
            enviarEmail(Contactos.EMAIL_EDESUR, ASUNTO_EDESUR, CUERPO_EDESUR);
        } else if (excusa.getMotivo() == Motivo.CUIDADO_FAMILIAR) {
            enviarEmail(excusa.getEmpleado().getEmail(), ASUNTO_FAMILIAR, CUERPO_FAMILIAR);
        }
    }
}

package com.excusas.chain;

import com.excusas.model.Empleado;
import com.excusas.model.Excusa;
import com.excusas.model.Motivo;
import com.excusas.service.EmailSender;

public class SupervisoraArea extends Empleado implements Responsable {
    private EmailSender emailSender;
    private Responsable siguiente;

    public SupervisoraArea(String nombre, String email, int legajo, EmailSender emailSender) {
        super(nombre, email, legajo);
        this.emailSender = emailSender;
    }

    public void setSiguiente(Responsable siguiente) {
        this.siguiente = siguiente;
    }

    @Override
    public void revisarExcusa(Excusa excusa) {
        if (excusa.getMotivo() == Motivo.PERDIDA_SUMINISTRO) {
            excusa.setAceptada(true);
            emailSender.enviarEmail(
                    "EDESUR@mailfake.com.ar",
                    this.getEmail(),
                    "Consulta corte de suministro",
                    "¿Es verdad que hubo corte de luz?"
            );
        } else if (excusa.getMotivo() == Motivo.CUIDADO_FAMILIAR) {
            excusa.setAceptada(true);
            emailSender.enviarEmail(
                    excusa.getEmpleado().getEmail(),
                    this.getEmail(),
                    "Consulta personal",
                    "¿Está todo bien?"
            );
        } else if (siguiente != null) {
            siguiente.revisarExcusa(excusa);
        }
    }
}
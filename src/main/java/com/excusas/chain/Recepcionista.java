package com.excusas.chain;

import com.excusas.model.Empleado;
import com.excusas.model.Excusa;
import com.excusas.model.Motivo;
import com.excusas.service.EmailSender;

public class Recepcionista extends Empleado implements Responsable {
    private EmailSender emailSender;
    private Responsable siguiente;

    public Recepcionista(String nombre, String email, int legajo, EmailSender emailSender) {
        super(nombre, email, legajo);
        this.emailSender = emailSender;
    }

    public void setSiguiente(Responsable siguiente) {
        this.siguiente = siguiente;
    }

    @Override
    public void revisarExcusa(Excusa excusa) {
        if (excusa.getMotivo() == Motivo.QUEDARSE_DORMIDO) {
            excusa.setAceptada(true);
            emailSender.enviarEmail(
                    excusa.getEmpleado().getEmail(),
                    "recepcion@excusas.com",
                    "motivo demora",
                    "la licencia fue aceptada"
            );
        } else if (siguiente != null) {
            siguiente.revisarExcusa(excusa);
        }
    }
}
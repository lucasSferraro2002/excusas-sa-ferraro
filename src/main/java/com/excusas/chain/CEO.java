package com.excusas.chain;

import com.excusas.model.Empleado;
import com.excusas.model.Excusa;
import com.excusas.model.Motivo;
import com.excusas.service.AdministradorProntuarios;
import com.excusas.service.EmailSender;

public class CEO extends Empleado implements Responsable {
    private EmailSender emailSender;
    private Responsable siguiente;

    public CEO(String nombre, String email, int legajo, EmailSender emailSender) {
        super(nombre, email, legajo);
        this.emailSender = emailSender;
    }

    public void setSiguiente(Responsable siguiente) {
        this.siguiente = siguiente;
    }

    @Override
    public void revisarExcusa(Excusa excusa) {
        if (excusa.getMotivo() == Motivo.INCREIBLE_INVEROSIMIL) {
            excusa.setAceptada(true);
            emailSender.enviarEmail(
                    excusa.getEmpleado().getEmail(),
                    this.getEmail(),
                    "Resolución final",
                    "Aprobado por creatividad"
            );
            AdministradorProntuarios.getInstance().registrarProntuario(excusa);
        } else if (siguiente != null) {
            siguiente.revisarExcusa(excusa);
        }
    }
}
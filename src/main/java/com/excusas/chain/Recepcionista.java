package com.excusas.chain;

import com.excusas.model.Empleado;
import com.excusas.model.Excusa;
import com.excusas.model.NivelComplejidad;
import com.excusas.service.EmailSender;

public class Recepcionista extends Empleado {
    public Recepcionista(String nombre, String email, int legajo, EmailSender emailSender) {
        super(nombre, email, legajo, emailSender);
    }

    @Override
    protected NivelComplejidad getNivelAsignado() { return NivelComplejidad.TRIVIAL; }

    @Override
    protected void procesarExcusaEspecifica(Excusa excusa) {
        excusa.setAceptada(true);
        getEmailSender().enviarEmail(excusa.getEmpleado().getEmail(), getEmail(), "motivo demora", "la licencia fue aceptada");
    }
}
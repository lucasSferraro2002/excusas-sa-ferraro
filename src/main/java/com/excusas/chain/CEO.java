package com.excusas.chain;

import com.excusas.model.Empleado;
import com.excusas.model.Excusa;
import com.excusas.model.NivelComplejidad;
import com.excusas.service.AdministradorProntuarios;
import com.excusas.service.EmailSender;

public class CEO extends Empleado {
    public CEO(String nombre, String email, int legajo, EmailSender emailSender) {
        super(nombre, email, legajo, emailSender);
    }

    @Override
    protected NivelComplejidad getNivelAsignado() { return NivelComplejidad.INVEROSIMIL; }

    @Override
    protected void procesarExcusaEspecifica(Excusa excusa) {
        excusa.setAceptada(true);
        getEmailSender().enviarEmail(excusa.getEmpleado().getEmail(), getEmail(), "Resolución final", "Aprobado por creatividad");
        AdministradorProntuarios.getInstance().registrarProntuario(excusa);
    }
}
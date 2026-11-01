package com.excusas.chain;

import com.excusas.model.Empleado;
import com.excusas.model.Excusa;
import com.excusas.model.NivelComplejidad;
import com.excusas.service.EmailSender;

public class SupervisoraArea extends Empleado {
    public SupervisoraArea(String nombre, String email, int legajo, EmailSender emailSender) {
        super(nombre, email, legajo, emailSender);
    }

    @Override
    protected NivelComplejidad getNivelAsignado() { return NivelComplejidad.MODERADA; }

    @Override
    protected void procesarExcusaEspecifica(Excusa excusa) {
        excusa.setAceptada(true);
        String texto = excusa.getDescripcion().toLowerCase();

        if (texto.contains("corto la luz")) {
            getEmailSender().enviarEmail("EDESUR@mailfake.com.ar", getEmail(), "Verificación de servicio", "¿Me confirman si hubo un corte de luz en el barrio del empleado?");
        } else if (texto.contains("cuidar a un familiar")) {
            getEmailSender().enviarEmail(excusa.getEmpleado().getEmail(), getEmail(), "Consulta personal", "¿Está todo bien con tu familiar?");
        } else {
            getEmailSender().enviarEmail(excusa.getEmpleado().getEmail(), getEmail(), "Excusa moderada", "Tu excusa ha sido evaluada y aceptada.");
        }
    }
}
package com.excusas.chain;

import com.excusas.model.Empleado;
import com.excusas.model.Excusa;
import com.excusas.model.NivelComplejidad;
import com.excusas.service.EmailSender;

public class GerenteHR extends Empleado {
    public GerenteHR(String nombre, String email, int legajo, EmailSender emailSender) {
        super(nombre, email, legajo, emailSender);
    }

    @Override
    protected NivelComplejidad getNivelAsignado() { return NivelComplejidad.COMPLEJA; }

    @Override
    protected void procesarExcusaEspecifica(Excusa excusa) {
        excusa.setAceptada(true);
    }
}
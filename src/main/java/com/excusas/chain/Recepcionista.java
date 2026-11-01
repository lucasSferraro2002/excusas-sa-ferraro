package com.excusas.chain;

import com.excusas.model.Excusa;
import com.excusas.model.NivelExcusa;
import com.excusas.service.EmailSender;

public class Recepcionista extends Encargado {
    private static final String ASUNTO = "motivo demora";
    private static final String CUERPO = "la licencia fue aceptada";

    public Recepcionista(String nombre, String email, int legajo, EmailSender emailSender) {
        super(nombre, email, legajo, emailSender);
    }

    @Override
    protected boolean puedeProcesar(Excusa excusa) { return esDeNivelHasta(excusa, NivelExcusa.TRIVIAL); }

    @Override
    protected void procesarExcusaEspecifica(Excusa excusa) {
        excusa.aceptar();
        enviarEmail(excusa.getEmpleado().getEmail(), ASUNTO, CUERPO);
    }
}

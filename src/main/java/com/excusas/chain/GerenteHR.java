package com.excusas.chain;

import com.excusas.model.Excusa;
import com.excusas.model.ExcusaCompleja;
import com.excusas.service.EmailSender;

/** El enunciado no define acción al aceptar: solo marca la excusa como aceptada. */
public class GerenteHR extends Encargado {
    public GerenteHR(String nombre, String email, int legajo, EmailSender emailSender) {
        super(nombre, email, legajo, emailSender);
    }

    @Override
    protected boolean puedeProcesar(Excusa excusa) { return excusa instanceof ExcusaCompleja; }

    @Override
    protected void procesarExcusaEspecifica(Excusa excusa) { excusa.aceptar(); }
}

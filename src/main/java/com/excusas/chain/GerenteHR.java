package com.excusas.chain;

import com.excusas.model.Excusa;
import com.excusas.model.NivelExcusa;
import com.excusas.service.EmailSender;

/** Acepta hasta excusas complejas. El enunciado no define acción al aceptar: solo la marca como aceptada. */
public class GerenteHR extends Encargado {
    public GerenteHR(String nombre, String email, int legajo, EmailSender emailSender) {
        super(nombre, email, legajo, emailSender);
    }

    @Override
    protected boolean puedeProcesar(Excusa excusa) { return esDeNivelHasta(excusa, NivelExcusa.COMPLEJA); }

    @Override
    protected void procesarExcusaEspecifica(Excusa excusa) { excusa.aceptar(); }
}

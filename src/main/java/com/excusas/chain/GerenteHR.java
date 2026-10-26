package com.excusas.chain;

import com.excusas.model.Empleado;
import com.excusas.model.Excusa;
import com.excusas.model.Motivo;
import com.excusas.service.EmailSender;

public class GerenteHR extends Empleado implements Responsable {
    private Responsable siguiente;

    public GerenteHR(String nombre, String email, int legajo, EmailSender emailSender) {
        super(nombre, email, legajo);
    }

    public void setSiguiente(Responsable siguiente) {
        this.siguiente = siguiente;
    }

    @Override
    public void revisarExcusa(Excusa excusa) {
        if (excusa.getMotivo() == Motivo.IRRELEVANTE) {
            excusa.setAceptada(true);
        } else if (siguiente != null) {
            siguiente.revisarExcusa(excusa);
        }
    }
}